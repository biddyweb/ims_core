/** 
 * $Id$
 *  
 * Copyright (C) 2004-2006 FhG Fokus
 *
 * This file is part of Open IMS Core - an open source IMS CSCFs & HSS
 * implementation
 *
 * Open IMS Core is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * For a license to use the Open IMS Core software under conditions
 * other than those described here, or to purchase support for this
 * software, please contact Fraunhofer FOKUS by e-mail at the following
 * addresses:
 *     info@open-ims.org
 *
 * Open IMS Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * It has to be noted that this Open Source IMS Core System is not 
 * intended to become or act as a product in a commercial context! Its 
 * sole purpose is to provide an IMS core reference implementation for 
 * IMS technology testing and IMS application prototyping for research 
 * purposes, typically performed in IMS test-beds.
 * 
 * Users of the Open Source IMS Core System have to be aware that IMS
 * technology may be subject of patents and licence terms, as being 
 * specified within the various IMS-related IETF, ITU-T, ETSI, and 3GPP
 * standards. Thus all Open IMS Core users have to take notice of this 
 * fact and have to agree to check out carefully before installing, 
 * using and extending the Open Source IMS Core System, if related 
 * patents and licences may become applicable to the intended usage 
 * context.  
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */
 
/**
 * \file
 * 
 * CDiameterPeer Realm Routing
 * 
 *  \author Dragos Vingarzan vingarzan -at- fokus dot fraunhofer dot de
 * 
 */
#include "routing.h"
#include "config.h"
#include "peermanager.h"
#include "diameter_api.h"

extern dp_config *config;		/**< Configuration for this diameter peer 	*/

/**
 * Get the first peer that is connected from the list of routing entries.
 * @param r - the list of routing entries to look into
 * @returns - the peer or null if none connected
 */
peer* get_first_connected_route(routing_entry *r)
{
	routing_entry *i;
	peer *p;
	for(i=r;i;i=i->next){
		p = get_peer_by_fqdn(&(r->fqdn));
		if (p && (p->state==I_Open || p->state==R_Open)) return p;
	}
	return 0;
}

/**
 * Get the first connect peer that matches the routing mechanisms.
 * - First the Destination-Host AVP value is tried if connected (the peer does not have to
 * be in the routing table at all).
 * - Then we look for a connected peer in the specific realm for the Destination-Realm AVP
 * - Then we look for the first connected peer in the default routes
 * @param m - the Diameter message to find the destination peer for
 * @returns - the connected peer or null if none connected found
 */  
peer* get_routing_peer(AAAMessage *m)
{
	str destination_realm={0,0},destination_host={0,0};
	AAA_AVP *avp;
	peer *p;
	routing_realm *rr;
	
	avp = AAAFindMatchingAVP(m,0,AVP_Destination_Host,0,AAA_FORWARD_SEARCH);
	if (avp) destination_host = avp->data;
	
	if (destination_host.len){
		/* There is a destination host present in the message try and route directly there */
		p = get_peer_by_fqdn(&destination_host);
		if (p && (p->state==I_Open || p->state==R_Open)) return p;
		/* the destination host peer is not connected at the moment, try a normal route then */
	}
	
	avp = AAAFindMatchingAVP(m,0,AVP_Destination_Realm,0,AAA_FORWARD_SEARCH);
	if (avp) destination_realm = avp->data;
	
	if (!config->r_table) {
		LOG(L_ERR,"ERROR:get_routing_peer(): Empty routing table.\n");
		return 0;
	}
	
	if (destination_realm.len){
		/* first search for the destination realm */
		for(rr=config->r_table->realms;rr;rr=rr->next)
			if (rr->realm.len == destination_realm.len &&
				strncasecmp(rr->realm.s,destination_realm.s,destination_realm.len)==0)
					break;
		if (rr) {
			p = get_first_connected_route(rr->routes);
			if (p) return p;
			else LOG(L_ERR,"ERROR:get_routing_peer(): No connected Route peer found for Realm <%.*s>. Trying DefaultRoutes next...\n",
					destination_realm.len,destination_realm.s);
		}	 
	}
	/* if not found in the realms or no destination_realm, 
	 * get the first connected host in default routes */
	p = get_first_connected_route(config->r_table->routes);
	if (!p){
		LOG(L_ERR,"ERROR:get_routing_peer(): No connected DefaultRoute peer found.\n");
	}
	return p;
}
/*
  *  Copyright (C) 2004-2007 FhG Fokus
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
  * patents and licenses may become applicable to the intended usage
  * context. 
  *
  * You should have received a copy of the GNU General Public License
  * along with this program; if not, write to the Free Software
  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  
  * 
  */

package de.fhg.fokus.hss.web.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import de.fhg.fokus.hss.cx.CxConstants;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author adp dot fokus dot fraunhofer dot de 
 * Adrian Popescu / FOKUS Fraunhofer Institute
 */


public class IMPU_Form extends ActionForm implements Serializable{

	private int id;
	private String identity;
	private boolean barring;
	private int id_sp;
	private int id_impu_implicitset;
	private int id_charging_info;
	private boolean can_register;
	private int user_state;
	private int type;
	private String wildcard_psi;
	private String display_name;
	private boolean psi_activation;
	
	private int associated_ID;
	private String impi_identity;
	private String impu_implicitset_identity;
	private int vn_id;
	private int already_assigned_impi_id;
	private List select_sp;
	private List select_charging_info;
	private List select_identity_type;
	private List select_vn;
	private String nextAction;

	public void reset(ActionMapping actionMapping, HttpServletRequest request){
    	this.id = -1;
    	this.identity = null;
    	this.barring = false;    	
    	this.id_impu_implicitset = -1;
    	this.id_sp = -1;
    	this.id_charging_info = -1;
    	this.can_register = true;
    	this.type = CxConstants.Identity_Type.Public_User_Identity.code;    	
    	this.wildcard_psi = null;
    	this.psi_activation = false;
    	this.display_name = null;
    	this.user_state = 0;
    	
    	this.associated_ID = -1;
    	this.impi_identity = null;
    	this.impu_implicitset_identity = null;
    	this.vn_id = -1;
    	this.select_charging_info = null;
    	this.select_sp = null;
    	this.select_identity_type = null;
    	this.select_vn = null;
    }
	
    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest request){
        ActionErrors actionErrors = new ActionErrors();

        if (identity == null || ((!identity.startsWith("sip:") && !identity.startsWith("sips:")) && 
        		(!identity.startsWith("tel:") && !identity.startsWith("tels:")))){
        	actionErrors.add("identity", new ActionMessage("impu_form.error.identity"));
        }

        if (this.id_sp == -1){
        	actionErrors.add("id_sp", new ActionMessage("impu_form.error.id_sp"));
        }

        if (this.id_charging_info == -1){
        	actionErrors.add("id_charging_info", new ActionMessage("impu_form.error.id_charging_info"));
        }
        
        return actionErrors;
    }
	
	
	public boolean isBarring() {
		return barring;
	}
	public void setBarring(boolean barring) {
		this.barring = barring;
	}

	public int getId_charging_info() {
		return id_charging_info;
	}
	public void setId_charging_info(int id_charging_info) {
		this.id_charging_info = id_charging_info;
	}
	public void setId_sp(int id_sp) {
		this.id_sp = id_sp;
	}
	public int getId_impu_implicitset() {
		return id_impu_implicitset;
	}
	public void setId_impu_implicitset(int id_impu_implicitset) {
		this.id_impu_implicitset = id_impu_implicitset;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_sp() {
		return id_sp;
	}
	public void set_Id_sp(int id_sp) {
		this.id_sp = id_sp;
	}
	public List getSelect_charging_info() {
		return select_charging_info;
	}
	public void setSelect_charging_info(List select_charging_info) {
		this.select_charging_info = select_charging_info;
	}
	public List getSelect_sp() {
		return select_sp;
	}
	public void setSelect_sp(List select_sp) {
		this.select_sp = select_sp;
	}
		
	public int getUser_state() {
		return user_state;
	}
	public void setUser_state(int user_state) {
		this.user_state = user_state;
	}
	public String getNextAction() {
		return nextAction;
	}
	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}
    public boolean getCan_register() {
		return can_register;
	}
	public void setCan_register(boolean can_register) {
		this.can_register = can_register;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public boolean getPsi_activation() {
		return psi_activation;
	}
	public void setPsi_activation(boolean psi_activation) {
		this.psi_activation = psi_activation;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getWildcard_psi() {
		return wildcard_psi;
	}
	public void setWildcard_psi(String wildcard_psi) {
		this.wildcard_psi = wildcard_psi;
	}
	
	public List getSelect_identity_type() {
		return select_identity_type;
	}
	public void setSelect_identity_type(List select_identity_type) {
		this.select_identity_type = select_identity_type;
	}

	public String getImpi_identity() {
		return impi_identity;
	}

	public void setImpi_identity(String impi_identity) {
		this.impi_identity = impi_identity;
	}

	public int getAssociated_ID() {
		return associated_ID;
	}

	public void setAssociated_ID(int associated_ID) {
		this.associated_ID = associated_ID;
	}

	public String getImpu_implicitset_identity() {
		return impu_implicitset_identity;
	}

	public void setImpu_implicitset_identity(String impu_implicitset_identity) {
		this.impu_implicitset_identity = impu_implicitset_identity;
	}

	public int getVn_id() {
		return vn_id;
	}

	public void setVn_id(int vn_id) {
		this.vn_id = vn_id;
	}

	public List getSelect_vn() {
		return select_vn;
	}

	public void setSelect_vn(List select_vn) {
		this.select_vn = select_vn;
	}

	public int getAlready_assigned_impi_id() {
		return already_assigned_impi_id;
	}

	public void setAlready_assigned_impi_id(int already_assigned_impi_id) {
		this.already_assigned_impi_id = already_assigned_impi_id;
	}
	
	
}

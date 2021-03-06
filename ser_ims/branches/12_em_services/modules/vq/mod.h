 
/**
 * \file
 * 
 * 
 * Virtual queuing module
 * 
 *  \author Andreea dot Ancuta dot Onofrei -at- fokus dot fraunhofer dot de
 * 
 */

#ifndef VQ_MOD_H
#define VQ_MOD_H

#include "../../sr_module.h"

#define M_NAME "VQ"

/** Return and break the execution of routing script */
#define CSCF_RETURN_BREAK	0 
/** Return true in the routing script */
#define CSCF_RETURN_TRUE	1
/** Return false in the routing script */
#define CSCF_RETURN_FALSE -1
/** Return error in the routing script */
#define CSCF_RETURN_ERROR -2

#define STR_SHM_DUP(dest,src,txt)\
{\
	if ((src).len==0) {\
		(dest).s=0;\
		(dest).len=0;\
	}else {\
		(dest).s = shm_malloc((src).len);\
		if (!(dest).s){\
			LOG(L_ERR,"ERR:"M_NAME":"txt": Error allocating %d bytes\n",(src).len);\
			(dest).len = 0;\
			goto out_of_memory;\
		}else{\
			(dest).len = (src).len;\
			memcpy((dest).s,(src).s,(src).len);\
		}\
	}\
}

#define STR_PKG_DUP(dest,src,txt)\
{\
	if ((src).len==0) {\
		(dest).s=0;\
		(dest).len=0;\
	}else {\
		(dest).s = pkg_malloc((src).len);\
		if (!(dest).s){\
			LOG(L_ERR,"ERRL:"M_NAME":"txt": Error allocating %d bytes\n",(src).len);\
			(dest).len = 0;\
			goto out_of_memory;\
		}else{\
			(dest).len = (src).len;\
			memcpy((dest).s,(src).s,(src).len);\
		}\
	}\
}

#define STR_APPEND(dst,src)\
	{memcpy((dst).s+(dst).len,(src).s,(src).len);\
	(dst).len = (dst).len + (src).len;}

/* ANSI Terminal colors */
#define ANSI_GRAY		"\033[01;30m"
#define ANSI_BLINK_RED 	"\033[00;31m"
#define ANSI_RED 		"\033[01;31m"
#define ANSI_GREEN		"\033[01;32m"
#define ANSI_YELLOW 	"\033[01;33m"
#define ANSI_BLUE 		"\033[01;34m"
#define ANSI_MAGENTA	"\033[01;35m"
#define ANSI_CYAN		"\033[01;36m"
#define ANSI_WHITE		"\033[01;37m"



#endif



#include        "tclsysdef.h"

/***********************************************************************
* TCL_GENERAL.C --
*
* General-purpose routines ...
*
* TclMsg()
*
* History:
*  15-Jan-1998  TRG  Create.
*
************************************************************************/



	/****************************************************************
	 * TclMsg:
	 ****************************************************************/
int   TclMsg(			/* Returns: val of "sts"		*/
    int   sts			/* <r> sts to report			*/
   ,char  fmt[]		/* <r> fmt string for message		*/
   ,void  *p1			/* <r:opt> parameter			*/
   )
   {
    int   i,k;
    static DYNAMIC_DESCRIPTOR(dsc_format);

    str_copy_dx(&dsc_format,"%s: ");
    str_append(&dsc_format,fmt);
    str_append(&dsc_format,"\n");
    fprintf(stderr,dsc_format.dscA_pointer,pgmname(),p1);
    fprintf(stderr,"    --> sts = %08X\n",sts);

    str_free1_dx(&dsc_format);

    return(sts);
   }


	/***************************************************************
	 * TclShowDefault:
	 ***************************************************************/
int   TclShowDefault()		/* Returns: status			*/
   {
    char  *p;
    int nid;
    TreeGetDefaultNid(&nid);
    p = TreeGetPath(nid);
    if (p)
        TclTextOut(p);
    return(p ? 1 : 0);
   }

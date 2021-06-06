/* Generated by Together */

package com.dimata.common.session.contact;

import com.dimata.util.*;

import java.util.*;
import java.sql.*;

import com.dimata.common.entity.contact.*;
import com.dimata.common.db.DBResultSet;
import com.dimata.common.db.DBHandler;
import com.dimata.posbo.entity.warehouse.PstMatStockOpname;
import com.dimata.posbo.entity.warehouse.PstMatReturn;
import com.dimata.posbo.entity.warehouse.PstMatReceive;
import com.dimata.posbo.entity.purchasing.PstPurchaseOrder;
import com.dimata.posbo.entity.masterdata.PstMatVendorPrice;

public class SessContactList {
    /** this method used to get contact name specified by contact Id */
    public static String getContactName(long contactId) {
        DBResultSet dbrs = null;
        String result = "";
        try {
            String sql = " SELECT " + PstContactList.fieldNames[PstContactList.FLD_CONTACT_TYPE] + ", " +
                    PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + ", " +
                    PstContactList.fieldNames[PstContactList.FLD_PERSON_LASTNAME] + ", " +
                    PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " FROM " + PstContactList.TBL_CONTACT_LIST +
                    " WHERE " + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID] + " = " + contactId;
            //System.out.println("SessContactList.getContactName sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                int contactType = rs.getInt(PstContactList.fieldNames[PstContactList.FLD_CONTACT_TYPE]);
                if ((contactType == PstContactList.OWN_COMPANY) || (contactType == PstContactList.EXT_COMPANY)) {
                    result = rs.getString(PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]);
                } else {
                    result = rs.getString(PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]) + " " +
                            rs.getString(PstContactList.fieldNames[PstContactList.FLD_PERSON_LASTNAME]);
                }
            }
        } catch (Exception e) {
            System.out.println("SessContactList.getContactName err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /** this method used to list all vendor into which 'VENDOR_TYPE' */
    public static Vector getListVendor(String name, Vector vect, int start, int recordToGet) {
        String whereClause = "";
        Vector vectVendor = new Vector(1, 1);
        if ((name != null) && (name.length() > 0)) {
            vectVendor = LogicParser.textSentence(name);
            for (int i = 0; i < vectVendor.size(); i++) {
                String nameVendor = (String) vectVendor.get(i);
                if ((nameVendor.equals(LogicParser.SIGN)) || (nameVendor.equals(LogicParser.ENGLISH[LogicParser.WORD_OR])))
                    vectVendor.remove(i);
            }
        }
        //System.out.println("vectVendor : " + vectVendor);
        return PstContactList.getListContact(vectVendor, vect, start, recordToGet);
    }

    /** this method used to count all vendor into which 'VENDOR_TYPE' */
    public static int getCountListVendor(String name, Vector vect) {
        String whereClause = "";
        Vector vectVendor = new Vector(1, 1);
        if ((name != null) && (name.length() > 0)) {
            vectVendor = LogicParser.textSentence(name);
            for (int i = 0; i < vectVendor.size(); i++) {
                String nameVendor = (String) vectVendor.get(i);
                if ((nameVendor.equals(LogicParser.SIGN)) || (nameVendor.equals(LogicParser.ENGLISH[LogicParser.WORD_OR])))
                    vectVendor.remove(i);
            }
        }
        //System.out.println("vectVendor : " + vectVendor);
        return PstContactList.getCountListContact(vectVendor, vect);
    }

    public static boolean readyDataToDelete(long supplierId) {
        boolean status = true;
        try {
            // pengecekan di PO
            String where = PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + "=" + supplierId;
            Vector vlist = PstMatVendorPrice.list(0, 0, where, "");
            if (vlist != null && vlist.size() > 0) {
                status = false;
            } else {
                where = PstPurchaseOrder.fieldNames[PstPurchaseOrder.FLD_SUPPLIER_ID] + "=" + supplierId;
                vlist = PstPurchaseOrder.list(0, 0, where, "");
                if (vlist != null && vlist.size() > 0) {
                    status = false;
                } else {
                    // pengecekan di penerimaan
                    where = PstMatReceive.fieldNames[PstMatReceive.FLD_SUPPLIER_ID] + "=" + supplierId;
                    vlist = PstMatReceive.list(0, 0, where, "");
                    if (vlist != null && vlist.size() > 0) {
                        status = false;
                    } else {
                        // pengecekan di return
                        where = PstMatReturn.fieldNames[PstMatReturn.FLD_SUPPLIER_ID] + "=" + supplierId;
                        vlist = PstMatReturn.list(0, 0, where, "");
                        if (vlist != null && vlist.size() > 0) {
                            status = false;
                        } else {
                            // pengecekan di opname
                            where = PstMatStockOpname.fieldNames[PstMatStockOpname.FLD_SUPPLIER_ID] + "=" + supplierId;
                            vlist = PstMatStockOpname.list(0, 0, where, "");
                            if (vlist != null && vlist.size() > 0) {
                                status = false;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("SessContactList - readyDataToDelete : " + e.toString());
        }
        return status;
    }

}

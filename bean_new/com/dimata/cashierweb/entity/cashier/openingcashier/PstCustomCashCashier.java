/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.entity.cashier.openingcashier;

import com.dimata.common.entity.location.PstLocation;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.pos.entity.masterCashier.PstCashMaster;
import com.dimata.posbo.entity.masterdata.PstShift;
import com.dimata.common.db.DBHandler;
import com.dimata.common.db.DBResultSet;
import java.sql.ResultSet;

/**
 *
 * @author Ardiadi
 */
public class PstCustomCashCashier {
    public static int getCountPerCashOpening(String whereClause) {
        int count = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID]
                    + " , MSTR." + PstCashMaster.fieldNames[PstCashMaster.FLD_CASH_MASTER_ID]
                    + " , SHF." + PstShift.fieldNames[PstShift.FLD_SHIFT_ID]
                    + " , LOC." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]
                    + " , LOC." + PstLocation.fieldNames[PstLocation.FLD_NAME]
                    + " , CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE]
                    + " , SHF." + PstShift.fieldNames[PstShift.FLD_NAME]
                    + " , MSTR." + PstCashMaster.fieldNames[PstCashMaster.FLD_CASHIER_NUMBER]
                    + //+cash_bill_main
                    // " , CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+
                    // " , CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+
                    // " , CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+
                    " FROM " + PstCashCashier.TBL_CASH_CASHIER + " CSH"
                    + " INNER JOIN " + PstCashMaster.TBL_CASH_MASTER + " MSTR"
                    + " ON CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASHMASTER_ID]+"=MSTR."+PstCashMaster.fieldNames[PstCashMaster.FLD_CASH_MASTER_ID]
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " LOC"
                    + " ON MSTR." + PstCashMaster.fieldNames[PstCashMaster.FLD_LOCATION_ID]
                    + " = LOC." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]
                    + " INNER JOIN " + PstShift.TBL_SHIFT + " SHF"
                    + " ON CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_SHIFT_ID]
                    + " = SHF." + PstShift.fieldNames[PstShift.FLD_SHIFT_ID];
            //+inner join cash_bill_main
            // " INNER JOIN " + PstBillMain.TBL_CASH_BILL_MAIN + " CBM" +
            //" ON CBM." + PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID] +
            // " = CSH." + fieldNames[FLD_CASH_CASHIER_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return count;
    }
}

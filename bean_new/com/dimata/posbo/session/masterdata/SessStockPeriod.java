/* Generated by Together */

package com.dimata.posbo.session.masterdata;
import java.util.*;
import java.util.Date;
import java.sql.*;
import com.dimata.common.entity.periode.*;
import com.dimata.common.db.DBResultSet;
import com.dimata.common.db.DBHandler;

public class SessStockPeriod {

    /**
    * this method used to get Current Material Stock Periode
    * Current period ---> if period status is "not closed"
    */
    public static long getCurrPeriodId(){
 		 DBResultSet dbrs = null;
         long result = 0;
         try{
  			String sql = "SELECT " + PstPeriode.fieldNames[PstPeriode.FLD_STOCK_PERIODE_ID] +
                		 " FROM " + PstPeriode.TBL_STOCK_PERIODE +
              	 		 " WHERE " + PstPeriode.fieldNames[PstPeriode.FLD_STATUS] +
                         " = " + PstPeriode.FLD_STATUS_RUNNING;
  			//System.out.println("SessStockPeriod.getCurrPeriodId() sql : "+sql);
			dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                result = rs.getLong(PstPeriode.fieldNames[PstPeriode.FLD_STOCK_PERIODE_ID]);
            }
            rs.close();
		 }catch(Exception e){
           	System.out.println("Exception e : "+e.toString());
         }finally{
        	DBResultSet.close(dbrs);
            return result;
		 }
    }

    /**
    * this method used to check if currDate is valid for close period
    */
    public static boolean isValidDateToClosePeriod(Date currDate){
 		 DBResultSet dbrs = null;
         boolean result = false;
         try{
  			String sql = "SELECT " + PstPeriode.fieldNames[PstPeriode.FLD_END_DATE] +
                		 " FROM " + PstPeriode.TBL_STOCK_PERIODE +
              	 		 " WHERE " + PstPeriode.fieldNames[PstPeriode.FLD_STATUS] +
                         " = " + PstPeriode.FLD_STATUS_RUNNING;
  			//System.out.println("SessStockPeriod.isValidDateToClosePeriod() sql : "+sql);
			dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            Date endDate = new Date();
            while(rs.next()){
                endDate = rs.getDate(PstPeriode.fieldNames[PstPeriode.FLD_END_DATE]);
            }
            if(currDate.after(endDate)){
                result = true;
            }
		 }catch(Exception e){
           	System.out.println("SessStockPeriod.isValidDateToClosePeriod() err : "+e.toString());
         }finally{
        	DBResultSet.close(dbrs);
            return result;
		 }
    }

    /**
    * This method used to check if new entry periode is valid or not
    */
    public static int isValidPeriod(long idPeriod, Date newStartDate, Date newDueDate, int periodType){
        int monthInterval = PstPeriode.matPeriodTypeValues[periodType];
        int yearInterval = newDueDate.getYear()-newStartDate.getYear();

        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(newDueDate);

	    // add new matstockperiod
        if(idPeriod==0){
	        Periode stockPeriode = SessStockPeriod.getLastPeriod();
            // no period available
            if(stockPeriode.getOID()==0){
                 if(newStartDate.getDate()!=1){ return PstPeriode.ERR_START_DATE; }
                 else if(newDueDate.before(newStartDate)){ return PstPeriode.ERR_DUE_DATE; }
                 else if(newDueDate.getDate()!=newCalendar.getActualMaximum(newCalendar.DAY_OF_MONTH)){ return PstPeriode.ERR_DUE_DATE; }
                 else if((yearInterval*12)+newDueDate.getMonth()-newStartDate.getMonth()!=monthInterval-1){ return PstPeriode.ERR_DUE_DATE; }
                 else{ return PstPeriode.NO_ERR; }
            // period available
            }else{
                 Date lastDueDate = stockPeriode.getEndDate();
                 if(newStartDate.before(lastDueDate)){ return PstPeriode.ERR_START_DATE; }
                 else if(newStartDate.getDate()!=1){ return PstPeriode.ERR_START_DATE; }
                 else if(newDueDate.getDate()!=newCalendar.getActualMaximum(newCalendar.DAY_OF_MONTH)){ return PstPeriode.ERR_DUE_DATE; }
                 else if(newDueDate.before(newStartDate)){ return PstPeriode.ERR_DUE_DATE; }
                 else if((yearInterval*12)+newDueDate.getMonth()-newStartDate.getMonth()!=monthInterval-1){ return PstPeriode.ERR_DUE_DATE; }
                 else{ return PstPeriode.NO_ERR; }
            }
        }
        return PstPeriode.NO_ERR;
    }

    /**
    * this method used to get Last Material Stock Periode
    * Last period ---> if period's end date is maximum of all
    */
    public static Periode getLastPeriod(){
 		 DBResultSet dbrs = null;
         Periode result = new Periode();
         try{
  			String sql = "SELECT " + PstPeriode.fieldNames[PstPeriode.FLD_STOCK_PERIODE_ID] +
                         PstPeriode.fieldNames[PstPeriode.FLD_PERIODE_TYPE] +
                         PstPeriode.fieldNames[PstPeriode.FLD_PERIODE_NAME] +
                         PstPeriode.fieldNames[PstPeriode.ERR_START_DATE] +
                         PstPeriode.fieldNames[PstPeriode.FLD_END_DATE] +
						 PstPeriode.fieldNames[PstPeriode.FLD_STATUS] +
                		 " FROM " + PstPeriode.TBL_STOCK_PERIODE +
              	 		 " ORDER BY " + PstPeriode.fieldNames[PstPeriode.FLD_END_DATE] + " DESC";
  			//System.out.println("SessStockPeriod.getLastPeriod() sql : "+sql);
			dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                result.setOID(rs.getLong(PstPeriode.fieldNames[PstPeriode.FLD_STOCK_PERIODE_ID]));
                result.setPeriodeType(rs.getInt(PstPeriode.fieldNames[PstPeriode.FLD_PERIODE_TYPE]));
                result.setPeriodeName(rs.getString(PstPeriode.fieldNames[PstPeriode.FLD_PERIODE_NAME]));
                result.setStartDate(rs.getDate(PstPeriode.fieldNames[PstPeriode.FLD_START_DATE]));
                result.setEndDate(rs.getDate(PstPeriode.fieldNames[PstPeriode.FLD_END_DATE]));
                result.setStatus(rs.getInt(PstPeriode.fieldNames[PstPeriode.FLD_STATUS]));
                break;
            }
		 }catch(Exception e){
           	System.out.println("SessStockPeriod.getLastPeriod() exc : "+e.toString());
         }finally{
        	DBResultSet.close(dbrs);
            return result;
		 }
    }

}

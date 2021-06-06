

/* Created on 	:  [date] [time] AM/PM

 *

 * @author	 :

 * @version	 :

 */



/*******************************************************************

 * Class Description 	: [project description ... ]

 * Imput Parameters 	: [input parameter ...]

 * Output 		: [output ...]

 *******************************************************************/



package com.dimata.cashierweb.entity.cashier.transaction;



/* package java */

import java.io.*

;

import java.sql.*

;import java.util.*

;import java.util.Date;



/* package qdep */

import com.dimata.util.lang.I_Language;

import com.dimata.common.db.*;

import com.dimata.qdep.entity.*;



/* package hanoman */

//import com.dimata.qdep.db.DBHandler;

//import com.dimata.qdep.db.DBException;

//import com.dimata.qdep.db.DBLogger;



public class PstRsvGuest extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    

    //public static final  String TBL_RSV_GUEST = "RSV_GUEST";

    public static final  String TBL_RSV_GUEST = "rsv_guest";

    

    public static final  int FLD_RSV_GUEST_ID = 0;

    public static final  int FLD_CUSTOMER_ID = 1;

    public static final  int FLD_RESERVATION_ID = 2;

    public static final  int FLD_TYPE = 3;

    public static final int FLD_EXTENSION = 4;



    public static final  String[] fieldNames = {

        "RSV_GUEST_ID",

        "CONTACT_ID",

        "RESERVATION_ID",

        "TYPE",

        "EXTENSION"

    };

    

    public static final  int[] fieldTypes = {

        TYPE_LONG + TYPE_PK + TYPE_ID,

        TYPE_LONG,

        TYPE_LONG,

        TYPE_INT,

        TYPE_STRING

    };

    

    

    public static final int TYPE_PRIMARY = 0;

    public static final int TYPE_SECUNDARY = 1;

    

    public static final String[] strType = {"Primary", "Secundary"};

    

    public PstRsvGuest(){

    }

    

    public PstRsvGuest(int i) throws DBException {

        super(new PstRsvGuest());

    }

    

    public PstRsvGuest(String sOid) throws DBException {

        super(new PstRsvGuest(0));

        if(!locate(sOid))

            throw new DBException(this,DBException.RECORD_NOT_FOUND);

        else

            return;

    }

    

    public PstRsvGuest(long lOid) throws DBException {

        super(new PstRsvGuest(0));

        String sOid = "0";

        try {

            sOid = String.valueOf(lOid);

        }catch(Exception e) {

            throw new DBException(this,DBException.RECORD_NOT_FOUND);

        }

        if(!locate(sOid))

            throw new DBException(this,DBException.RECORD_NOT_FOUND);

        else

            return;

    }

    

    public int getFieldSize(){

        return fieldNames.length;

    }

    

    public String getTableName(){

        return TBL_RSV_GUEST;

    }

    

    public String[] getFieldNames(){

        return fieldNames;

    }

    

    public int[] getFieldTypes(){

        return fieldTypes;

    }

    

    public String getPersistentName(){

        return new PstRsvGuest().getClass().getName();

    }

    

    public long fetchExc(Entity ent) throws Exception{

        RsvGuest rsvguest = fetchExc(ent.getOID());

        ent = (Entity)rsvguest;

        return rsvguest.getOID();

    }

    

    public long insertExc(Entity ent) throws Exception{

        return insertExc((RsvGuest) ent);

    }

    

    public long updateExc(Entity ent) throws Exception{

        return updateExc((RsvGuest) ent);

    }

    

    public long deleteExc(Entity ent) throws Exception{

        if(ent==null){

            throw new DBException(this,DBException.RECORD_NOT_FOUND);

        }

        return deleteExc(ent.getOID());

    }

    

    public static RsvGuest fetchExc(long oid) throws DBException{

        try{

            RsvGuest rsvguest = new RsvGuest();

            PstRsvGuest pstRsvGuest = new PstRsvGuest(oid);

            rsvguest.setOID(oid);

            

            rsvguest.setCustomerId(pstRsvGuest.getlong(FLD_CUSTOMER_ID));

            rsvguest.setReservationId(pstRsvGuest.getlong(FLD_RESERVATION_ID));

            rsvguest.setType(pstRsvGuest.getInt(FLD_TYPE));

            rsvguest.setExtension(pstRsvGuest.getString(FLD_EXTENSION));



            return rsvguest;

        }catch(DBException dbe){

            throw dbe;

        }catch(Exception e){

            throw new DBException(new PstRsvGuest(0),DBException.UNKNOWN);

        }

    }

    

    public static long insertExc(RsvGuest rsvguest) throws DBException{

        try{

            PstRsvGuest pstRsvGuest = new PstRsvGuest(0);

            

            pstRsvGuest.setLong(FLD_CUSTOMER_ID, rsvguest.getCustomerId());

            pstRsvGuest.setLong(FLD_RESERVATION_ID, rsvguest.getReservationId());

            pstRsvGuest.setInt(FLD_TYPE, rsvguest.getType());

            pstRsvGuest.setString(FLD_EXTENSION, rsvguest.getExtension());



            pstRsvGuest.insert();

            rsvguest.setOID(pstRsvGuest.getlong(FLD_RSV_GUEST_ID));

        }catch(DBException dbe){

            throw dbe;

        }catch(Exception e){

            throw new DBException(new PstRsvGuest(0),DBException.UNKNOWN);

        }

        return rsvguest.getOID();

    }

    

    public static long updateExc(RsvGuest rsvguest) throws DBException{

        try{

            if(rsvguest.getOID() != 0){

                PstRsvGuest pstRsvGuest = new PstRsvGuest(rsvguest.getOID());

                

                pstRsvGuest.setLong(FLD_CUSTOMER_ID, rsvguest.getCustomerId());

                pstRsvGuest.setLong(FLD_RESERVATION_ID, rsvguest.getReservationId());

                pstRsvGuest.setInt(FLD_TYPE, rsvguest.getType());

                pstRsvGuest.setString(FLD_EXTENSION, rsvguest.getExtension());



                pstRsvGuest.update();

                return rsvguest.getOID();

                

            }

        }catch(DBException dbe){

            throw dbe;

        }catch(Exception e){

            throw new DBException(new PstRsvGuest(0),DBException.UNKNOWN);

        }

        return 0;

    }

    

    public static long deleteExc(long oid) throws DBException{

        try{

            PstRsvGuest pstRsvGuest = new PstRsvGuest(oid);

            pstRsvGuest.delete();

        }catch(DBException dbe){

            throw dbe;

        }catch(Exception e){

            throw new DBException(new PstRsvGuest(0),DBException.UNKNOWN);

        }

        return oid;

    }

    

    public static Vector listAll(){

        return list(0, 500, "","");

    }

    

    public static Vector list(int limitStart,int recordToGet, String whereClause, String order){

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT * FROM " + TBL_RSV_GUEST;

            if(whereClause != null && whereClause.length() > 0)

                sql = sql + " WHERE " + whereClause;

            if(order != null && order.length() > 0)

                sql = sql + " ORDER BY " + order;

            if(limitStart == 0 && recordToGet == 0)

                sql = sql + "";

            else

                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {

                RsvGuest rsvguest = new RsvGuest();

                resultToObject(rs, rsvguest);

                lists.add(rsvguest);

            }

            rs.close();

            return lists;

            

        }catch(Exception e) {

            System.out.println(e);

        }finally {

            DBResultSet.close(dbrs);

        }

        return new Vector();

    }




    public static Vector listDistinctGuest(int limitStart,int recordToGet, String whereClause, String order){

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT DISTINCT CONTACT_ID FROM " + TBL_RSV_GUEST;

            if(whereClause != null && whereClause.length() > 0)

                sql = sql + " WHERE " + whereClause;

            if(order != null && order.length() > 0)

                sql = sql + " ORDER BY " + order;

            if(limitStart == 0 && recordToGet == 0)

                sql = sql + "";

            else

                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) {

                RsvGuest rsvguest = new RsvGuest();

                //resultToObject(rs, rsvguest);
                rsvguest.setCustomerId(rs.getLong("CONTACT_ID"));

                lists.add(rsvguest);

            }

            rs.close();

            return lists;



        }catch(Exception e) {

            System.out.println(e);

        }finally {

            DBResultSet.close(dbrs);

        }

        return new Vector();

    }

    

    private static void resultToObject(ResultSet rs, RsvGuest rsvguest){

        try{

            rsvguest.setOID(rs.getLong(PstRsvGuest.fieldNames[PstRsvGuest.FLD_RSV_GUEST_ID]));

            rsvguest.setCustomerId(rs.getLong(PstRsvGuest.fieldNames[PstRsvGuest.FLD_CUSTOMER_ID]));

            rsvguest.setReservationId(rs.getLong(PstRsvGuest.fieldNames[PstRsvGuest.FLD_RESERVATION_ID]));

            rsvguest.setType(rs.getInt(PstRsvGuest.fieldNames[PstRsvGuest.FLD_TYPE]));

            rsvguest.setExtension(rs.getString(PstRsvGuest.fieldNames[PstRsvGuest.FLD_EXTENSION]));



        }catch(Exception e){ }

    }

    

    public static boolean checkOID(long rsvGuestId){

        DBResultSet dbrs = null;

        boolean result = false;

        try{

            String sql = "SELECT * FROM " + TBL_RSV_GUEST + " WHERE " +

            PstRsvGuest.fieldNames[PstRsvGuest.FLD_RSV_GUEST_ID] + " = " + rsvGuestId;

            

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            

            while(rs.next()) { result = true; }

            rs.close();

        }catch(Exception e){

            System.out.println("err : "+e.toString());

        }finally{

            DBResultSet.close(dbrs);

            return result;

        }

    }

    

    public static int getCount(String whereClause){

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT COUNT("+ PstRsvGuest.fieldNames[PstRsvGuest.FLD_RSV_GUEST_ID] + ") FROM " + TBL_RSV_GUEST;

            if(whereClause != null && whereClause.length() > 0)

                sql = sql + " WHERE " + whereClause;

            

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            

            int count = 0;

            while(rs.next()) { count = rs.getInt(1); }

            

            rs.close();

            return count;

        }catch(Exception e) {

            return 0;

        }finally {

            DBResultSet.close(dbrs);

        }

    }

    

    

    /* This method used to find current data */

    public static int findLimitStart( long oid, int recordToGet, String whereClause){

        String order = "";

        int size = getCount(whereClause);

        int start = 0;

        boolean found =false;

        for(int i=0; (i < size) && !found ; i=i+recordToGet){

            Vector list =  list(i,recordToGet, whereClause, order);

            start = i;

            if(list.size()>0){

                for(int ls=0;ls<list.size();ls++){

                    RsvGuest rsvguest = (RsvGuest)list.get(ls);

                    if(oid == rsvguest.getOID())

                        found=true;

                }

            }

        }

        if((start >= size) && (size > 0))

            start = start - recordToGet;

        

        return start;

    }

    

    public static int mergeRsvGuest(Vector vListId, long lMasterId) throws DBException{

        String stIdParam = "";

        String stSQL = "";

        int iResult = 0;

        

        if(vListId != null && vListId.size() > 0){

            for(int i=0; i<vListId.size(); i++){

                if(i > 0){

                    stIdParam += ","; 

                }

                stIdParam += vListId.get(i);

            }

            stSQL = " UPDATE "+PstRsvGuest.TBL_RSV_GUEST +

            " SET "+PstRsvGuest.fieldNames[PstRsvGuest.FLD_CUSTOMER_ID]+" = "+ lMasterId +

            " WHERE "+PstRsvGuest.fieldNames[PstRsvGuest.FLD_CUSTOMER_ID] +" in ("+stIdParam+")";

            

            try{

                iResult = DBHandler.execUpdate(stSQL);

            }

            catch(DBException dbe){

                dbe.printStackTrace();

                throw dbe;

            }

        }

        return iResult;

    }

}


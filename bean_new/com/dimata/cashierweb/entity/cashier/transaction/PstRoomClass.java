 

/* Created on 	:  [date] [time] AM/PM 

 * 

 * @author  	:  [authorName] 

 * @version  	:  [version] 

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

////import com.dimata.qdep.db.DBHandler;

////import com.dimata.qdep.db.DBException;

////import com.dimata.qdep.db.DBLogger;

import com.dimata.hanoman.entity.masterdata.*;import com.dimata.harisma.entity.masterdata.*; 



public class PstRoomClass extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 



	//public static final  String TBL_ROOM_CLASS = "ROOM_CLASS";

    public static final  String TBL_ROOM_CLASS = "room_class";



	public static final  int FLD_ROOM_CLASS_ID = 0;

	public static final  int FLD_HOTEL_PROFILE_ID = 1;

	public static final  int FLD_ROOM_CLASS = 2;

	public static final  int FLD_NUMBER_OF_ROOM = 3;

	public static final  int FLD_ROOM_RATE_RP = 4;

	public static final  int FLD_ROOM_EXTRA_BED_RATE_RP = 5;

	public static final  int FLD_ROOM_DAY_USE_RATE_RP = 6;

	public static final  int FLD_ROOM_QUARTER_RATE_RP = 7;

	public static final  int FLD_ROOM_RATE_$ = 8;

	public static final  int FLD_ROOM_EXTRA_BED_RATE_$ = 9;

	public static final  int FLD_ROOM_DAY_USE_RATE_$ = 10;

	public static final  int FLD_ROOM_QUANRTER_RATE_$ = 11;

	public static final  int FLD_ROOM_WITH_BREAK_FAST = 12;

	public static final  int FLD_SERVICE_PERCENTAGE = 13;

	public static final  int FLD_TAX_PERCENTAGE = 14;

	public static final  int FLD_DESCRIPTION = 15;

	public static final  int FLD_FACILITIES = 16;

    public static final int FLD_BREAKFAST_ID = 17;



    public static final int FLD_BREAKFAST_INCLUDE_IN_ROOM = 18;

    public static final int FLD_BREAKFAST_CHANGEABLE = 19;

    

    public static final int FLD_PRICE_TYPE = 20;

    public static final int FLD_SINGLE_PAX_PRICE_RP = 21;

    public static final int FLD_DOUBLE_PAX_PRICE_RP = 22;

    public static final int FLD_TRIPLE_PAX_PRICE_RP = 23;

    public static final int FLD_SINGLE_PAX_PRICE_USD = 24;

    public static final int FLD_DOUBLE_PAX_PRICE_USD = 25;

    public static final int FLD_TRIPLE_PAX_PRICE_USD = 26;
    
    public static final int FLD_SORT_INDEX = 27;

    public static final int FLD_BREAKFAST_NUMBER = 28;

    public static final int FLD_ROOM_CLASS_TYPE = 29;

	public static final  String[] fieldNames = {

		"ROOM_CLASS_ID",

		"HOTEL_PROFILE_ID",

		"ROOM_CLASS",

		"NUMBER_OF_ROOM",

		"ROOM_RATE_RP",

		"ROOM_EXTRA_BED_RATE_RP",

		"ROOM_DAY_USE_RATE_RP",

		"ROOM_QUARTER_RATE_RP",

		"ROOM_RATE_$",

		"ROOM_EXTRA_BED_RATE_$",

		"ROOM_DAY_USE_RATE_$",

		"ROOM_QUANRTER_RATE_$",

		"ROOM_WITH_BREAK_FAST",

		"SERVICE_PERCENTAGE",

		"TAX_PERCENTAGE",

		"DESCRIPTION",

		"FACILITIES",

        "BREAKFAST_ID",

        "BREAKFAST_INCLUDE_IN_ROOM",

        "BREAKFAST_CHANGEABLE",

        "PRICE_TYPE",

        "SINGLE_PAX_PRICE_RP",

        "DOUBLE_PAX_PRICE_RP",

        "TRIPLE_PAX_PRICE_RP",

        "SINGLE_PAX_PRICE_USD",

        "DOUBLE_PAX_PRICE_USD",

        "TRIPLE_PAX_PRICE_USD",
                
                "SORT_INDEX",
                "BREAKFAST_NUMBER",
                "ROOM_CLASS_TYPE"

	 }; 



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_LONG,

		TYPE_STRING,

		TYPE_INT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_INT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_STRING,

		TYPE_STRING,

        TYPE_LONG,

        TYPE_BOOL,

        TYPE_BOOL,        
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,                
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
	 }; 

         

         

       public static final int PRICE_TYPE_ROOM = 0;

       public static final int PRICE_TYPE_PAX  = 1;       



	public PstRoomClass(){

	}



	public PstRoomClass(int i) throws DBException { 

		super(new PstRoomClass()); 

	}



	public PstRoomClass(String sOid) throws DBException { 

		super(new PstRoomClass(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstRoomClass(long lOid) throws DBException { 

		super(new PstRoomClass(0)); 

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

		return TBL_ROOM_CLASS;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstRoomClass().getClass().getName(); 

	}



	public long fetchExc(Entity ent) throws Exception{ 

		RoomClass roomclass = fetchExc(ent.getOID()); 

		ent = (Entity)roomclass; 

		return roomclass.getOID(); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((RoomClass) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((RoomClass) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent.getOID()); 

	}



	public static RoomClass fetchExc(long oid) throws DBException{ 

		try{ 

			RoomClass roomclass = new RoomClass();

			PstRoomClass pstRoomClass = new PstRoomClass(oid); 

			roomclass.setOID(oid);

			roomclass.setHotelProfileId(pstRoomClass.getlong(FLD_HOTEL_PROFILE_ID));

			roomclass.setRoomClass(pstRoomClass.getString(FLD_ROOM_CLASS));

			roomclass.setNumberOfRoom(pstRoomClass.getInt(FLD_NUMBER_OF_ROOM));

			roomclass.setRoomRateRp(pstRoomClass.getdouble(FLD_ROOM_RATE_RP));

			roomclass.setRoomExtraBedRateRp(pstRoomClass.getdouble(FLD_ROOM_EXTRA_BED_RATE_RP));

			roomclass.setRoomDayUseRateRp(pstRoomClass.getdouble(FLD_ROOM_DAY_USE_RATE_RP));

			roomclass.setRoomQuarterRateRp(pstRoomClass.getdouble(FLD_ROOM_QUARTER_RATE_RP));

			roomclass.setRoomRate$(pstRoomClass.getdouble(FLD_ROOM_RATE_$));

			roomclass.setRoomExtraBedRate$(pstRoomClass.getdouble(FLD_ROOM_EXTRA_BED_RATE_$));

			roomclass.setRoomDayUseRate$(pstRoomClass.getdouble(FLD_ROOM_DAY_USE_RATE_$));

			roomclass.setRoomQuanrterRate$(pstRoomClass.getdouble(FLD_ROOM_QUANRTER_RATE_$));

			roomclass.setRoomWithBreakFast(pstRoomClass.getInt(FLD_ROOM_WITH_BREAK_FAST));

			roomclass.setServicePercentage(pstRoomClass.getdouble(FLD_SERVICE_PERCENTAGE));

			roomclass.setTaxPercentage(pstRoomClass.getdouble(FLD_TAX_PERCENTAGE)); 

			roomclass.setDescription(pstRoomClass.getString(FLD_DESCRIPTION));

			roomclass.setFacilities(pstRoomClass.getString(FLD_FACILITIES));

                        roomclass.setBreakfastId(pstRoomClass.getlong(FLD_BREAKFAST_ID));
                        roomclass.setBreakfastIncludeInRoom(pstRoomClass.getboolean(FLD_BREAKFAST_INCLUDE_IN_ROOM));
                        roomclass.setBreakfastChangable(pstRoomClass.getboolean(FLD_BREAKFAST_CHANGEABLE));
                        roomclass.setPriceType(pstRoomClass.getInt(FLD_PRICE_TYPE));
                        roomclass.setSinglePaxPriceRp(pstRoomClass.getdouble(FLD_SINGLE_PAX_PRICE_RP));
                        roomclass.setDoublePaxPriceRp(pstRoomClass.getdouble(FLD_DOUBLE_PAX_PRICE_RP));
                        roomclass.setTriplePaxPriceRp(pstRoomClass.getdouble(FLD_TRIPLE_PAX_PRICE_RP));
                        roomclass.setSinglePaxPriceUsd(pstRoomClass.getdouble(FLD_SINGLE_PAX_PRICE_USD));
                        roomclass.setDoublePaxPriceUsd(pstRoomClass.getdouble(FLD_DOUBLE_PAX_PRICE_USD));
                        roomclass.setTriplePaxPriceUsd(pstRoomClass.getdouble(FLD_TRIPLE_PAX_PRICE_USD));
                        roomclass.setSortIndex(pstRoomClass.getInt(FLD_SORT_INDEX));
                        roomclass.setBreakfastNumber(pstRoomClass.getInt(FLD_BREAKFAST_NUMBER));
                        roomclass.setRoomClassType(pstRoomClass.getInt(FLD_ROOM_CLASS_TYPE));
                        
			return roomclass; 

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstRoomClass(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(RoomClass roomclass) throws DBException{ 

		try{ 

			PstRoomClass pstRoomClass = new PstRoomClass(0);



			pstRoomClass.setLong(FLD_HOTEL_PROFILE_ID, roomclass.getHotelProfileId());

			pstRoomClass.setString(FLD_ROOM_CLASS, roomclass.getRoomClass());

			pstRoomClass.setInt(FLD_NUMBER_OF_ROOM, roomclass.getNumberOfRoom());

			pstRoomClass.setDouble(FLD_ROOM_RATE_RP, roomclass.getRoomRateRp());

			pstRoomClass.setDouble(FLD_ROOM_EXTRA_BED_RATE_RP, roomclass.getRoomExtraBedRateRp());

			pstRoomClass.setDouble(FLD_ROOM_DAY_USE_RATE_RP, roomclass.getRoomDayUseRateRp());

			pstRoomClass.setDouble(FLD_ROOM_QUARTER_RATE_RP, roomclass.getRoomQuarterRateRp());

			pstRoomClass.setDouble(FLD_ROOM_RATE_$, roomclass.getRoomRate$());

			pstRoomClass.setDouble(FLD_ROOM_EXTRA_BED_RATE_$, roomclass.getRoomExtraBedRate$());

			pstRoomClass.setDouble(FLD_ROOM_DAY_USE_RATE_$, roomclass.getRoomDayUseRate$());

			pstRoomClass.setDouble(FLD_ROOM_QUANRTER_RATE_$, roomclass.getRoomQuanrterRate$());

			pstRoomClass.setInt(FLD_ROOM_WITH_BREAK_FAST, roomclass.getRoomWithBreakFast());

			pstRoomClass.setDouble(FLD_SERVICE_PERCENTAGE, roomclass.getServicePercentage());

			pstRoomClass.setDouble(FLD_TAX_PERCENTAGE, roomclass.getTaxPercentage());

			pstRoomClass.setString(FLD_DESCRIPTION, roomclass.getDescription());

			pstRoomClass.setString(FLD_FACILITIES, roomclass.getFacilities());

                        pstRoomClass.setLong(FLD_BREAKFAST_ID, roomclass.getBreakfastId());

                        pstRoomClass.setboolean(FLD_BREAKFAST_INCLUDE_IN_ROOM, roomclass.getBreakfastIncludeInRoom());

                        pstRoomClass.setboolean(FLD_BREAKFAST_CHANGEABLE, roomclass.getBreakfastChangable());

                        

                        pstRoomClass.setInt(FLD_PRICE_TYPE, roomclass.getPriceType());

                        pstRoomClass.setDouble(FLD_SINGLE_PAX_PRICE_RP, roomclass.getSinglePaxPriceRp());

                        pstRoomClass.setDouble(FLD_DOUBLE_PAX_PRICE_RP, roomclass.getDoublePaxPriceRp());

                        pstRoomClass.setDouble(FLD_TRIPLE_PAX_PRICE_RP, roomclass.getTriplePaxPriceRp());

                        pstRoomClass.setDouble(FLD_SINGLE_PAX_PRICE_USD, roomclass.getSinglePaxPriceUsd());

                        pstRoomClass.setDouble(FLD_DOUBLE_PAX_PRICE_USD, roomclass.getDoublePaxPriceUsd());

                        pstRoomClass.setDouble(FLD_TRIPLE_PAX_PRICE_USD, roomclass.getTriplePaxPriceUsd());
                        pstRoomClass.setInt(FLD_SORT_INDEX, roomclass.getSortIndex());
                        pstRoomClass.setInt(FLD_BREAKFAST_NUMBER, roomclass.getBreakfastNumber());
                        pstRoomClass.setInt(FLD_ROOM_CLASS_TYPE, roomclass.getRoomClassType());
                        
			pstRoomClass.insert(); 

			roomclass.setOID(pstRoomClass.getlong(FLD_ROOM_CLASS_ID));

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstRoomClass(0),DBException.UNKNOWN); 

		}

		return roomclass.getOID();

	}



	public static long updateExc(RoomClass roomclass) throws DBException{ 

		try{ 

			if(roomclass.getOID() != 0){ 

				PstRoomClass pstRoomClass = new PstRoomClass(roomclass.getOID());



				pstRoomClass.setLong(FLD_HOTEL_PROFILE_ID, roomclass.getHotelProfileId());

				pstRoomClass.setString(FLD_ROOM_CLASS, roomclass.getRoomClass());

				pstRoomClass.setInt(FLD_NUMBER_OF_ROOM, roomclass.getNumberOfRoom());

				pstRoomClass.setDouble(FLD_ROOM_RATE_RP, roomclass.getRoomRateRp());

				pstRoomClass.setDouble(FLD_ROOM_EXTRA_BED_RATE_RP, roomclass.getRoomExtraBedRateRp());

				pstRoomClass.setDouble(FLD_ROOM_DAY_USE_RATE_RP, roomclass.getRoomDayUseRateRp());

				pstRoomClass.setDouble(FLD_ROOM_QUARTER_RATE_RP, roomclass.getRoomQuarterRateRp());

				pstRoomClass.setDouble(FLD_ROOM_RATE_$, roomclass.getRoomRate$());

				pstRoomClass.setDouble(FLD_ROOM_EXTRA_BED_RATE_$, roomclass.getRoomExtraBedRate$());

				pstRoomClass.setDouble(FLD_ROOM_DAY_USE_RATE_$, roomclass.getRoomDayUseRate$());

				pstRoomClass.setDouble(FLD_ROOM_QUANRTER_RATE_$, roomclass.getRoomQuanrterRate$());

				pstRoomClass.setInt(FLD_ROOM_WITH_BREAK_FAST, roomclass.getRoomWithBreakFast());

				pstRoomClass.setDouble(FLD_SERVICE_PERCENTAGE, roomclass.getServicePercentage());

				pstRoomClass.setDouble(FLD_TAX_PERCENTAGE, roomclass.getTaxPercentage());

				pstRoomClass.setString(FLD_DESCRIPTION, roomclass.getDescription());

				pstRoomClass.setString(FLD_FACILITIES, roomclass.getFacilities());

                                pstRoomClass.setLong(FLD_BREAKFAST_ID, roomclass.getBreakfastId());

                                pstRoomClass.setboolean(FLD_BREAKFAST_INCLUDE_IN_ROOM, roomclass.getBreakfastIncludeInRoom());

                                pstRoomClass.setboolean(FLD_BREAKFAST_CHANGEABLE, roomclass.getBreakfastChangable());

                    

                                pstRoomClass.setInt(FLD_PRICE_TYPE, roomclass.getPriceType());

                                pstRoomClass.setDouble(FLD_SINGLE_PAX_PRICE_RP, roomclass.getSinglePaxPriceRp());

                                pstRoomClass.setDouble(FLD_DOUBLE_PAX_PRICE_RP, roomclass.getDoublePaxPriceRp());

                                pstRoomClass.setDouble(FLD_TRIPLE_PAX_PRICE_RP, roomclass.getTriplePaxPriceRp());

                                pstRoomClass.setDouble(FLD_SINGLE_PAX_PRICE_USD, roomclass.getSinglePaxPriceUsd());

                                pstRoomClass.setDouble(FLD_DOUBLE_PAX_PRICE_USD, roomclass.getDoublePaxPriceUsd());

                                pstRoomClass.setDouble(FLD_TRIPLE_PAX_PRICE_USD, roomclass.getTriplePaxPriceUsd());
                                pstRoomClass.setInt(FLD_SORT_INDEX, roomclass.getSortIndex());                                
                                pstRoomClass.setInt(FLD_BREAKFAST_NUMBER, roomclass.getBreakfastNumber());
                                pstRoomClass.setInt(FLD_ROOM_CLASS_TYPE, roomclass.getRoomClassType());
                                
				pstRoomClass.update(); 
				return roomclass.getOID();
			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstRoomClass(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstRoomClass pstRoomClass = new PstRoomClass(oid);

			pstRoomClass.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstRoomClass(0),DBException.UNKNOWN); 

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

			String sql = "SELECT * FROM " + TBL_ROOM_CLASS; 

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

				RoomClass roomclass = new RoomClass();

				resultToObject(rs, roomclass);

				lists.add(roomclass);

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



	public static void resultToObject(ResultSet rs, RoomClass roomclass){

		try{

			roomclass.setOID(rs.getLong(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS_ID]));

			roomclass.setHotelProfileId(rs.getLong(PstRoomClass.fieldNames[PstRoomClass.FLD_HOTEL_PROFILE_ID]));

			roomclass.setRoomClass(rs.getString(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS]));

			roomclass.setNumberOfRoom(rs.getInt(PstRoomClass.fieldNames[PstRoomClass.FLD_NUMBER_OF_ROOM]));

			roomclass.setRoomRateRp(rs.getDouble(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_RATE_RP]));

			roomclass.setRoomExtraBedRateRp(rs.getDouble(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_EXTRA_BED_RATE_RP]));

			roomclass.setRoomDayUseRateRp(rs.getDouble(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_DAY_USE_RATE_RP]));

			roomclass.setRoomQuarterRateRp(rs.getDouble(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_QUARTER_RATE_RP]));

			roomclass.setRoomRate$(rs.getDouble(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_RATE_$]));

			roomclass.setRoomExtraBedRate$(rs.getDouble(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_EXTRA_BED_RATE_$]));

			roomclass.setRoomDayUseRate$(rs.getDouble(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_DAY_USE_RATE_$]));

			roomclass.setRoomQuanrterRate$(rs.getDouble(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_QUANRTER_RATE_$]));

			roomclass.setRoomWithBreakFast(rs.getInt(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_WITH_BREAK_FAST]));

			roomclass.setServicePercentage(rs.getDouble(PstRoomClass.fieldNames[PstRoomClass.FLD_SERVICE_PERCENTAGE]));

			roomclass.setTaxPercentage(rs.getDouble(PstRoomClass.fieldNames[PstRoomClass.FLD_TAX_PERCENTAGE]));

			roomclass.setDescription(rs.getString(PstRoomClass.fieldNames[PstRoomClass.FLD_DESCRIPTION]));

			roomclass.setFacilities(rs.getString(PstRoomClass.fieldNames[PstRoomClass.FLD_FACILITIES]));

                        roomclass.setBreakfastId(rs.getLong(PstRoomClass.fieldNames[PstRoomClass.FLD_BREAKFAST_ID]));

                        roomclass.setBreakfastIncludeInRoom(rs.getBoolean(PstRoomClass.fieldNames[PstRoomClass.FLD_BREAKFAST_INCLUDE_IN_ROOM]));

                        roomclass.setBreakfastChangable(rs.getBoolean(PstRoomClass.fieldNames[PstRoomClass.FLD_BREAKFAST_CHANGEABLE]));

                        

                        roomclass.setPriceType(rs.getInt(PstRoomClass.fieldNames[FLD_PRICE_TYPE]));

                        roomclass.setSinglePaxPriceRp(rs.getDouble(PstRoomClass.fieldNames[FLD_SINGLE_PAX_PRICE_RP]));

                        roomclass.setDoublePaxPriceRp(rs.getDouble(PstRoomClass.fieldNames[FLD_DOUBLE_PAX_PRICE_RP]));

                        roomclass.setTriplePaxPriceRp(rs.getDouble(PstRoomClass.fieldNames[FLD_TRIPLE_PAX_PRICE_RP]));

                        roomclass.setSinglePaxPriceUsd(rs.getDouble(PstRoomClass.fieldNames[FLD_SINGLE_PAX_PRICE_USD]));

                        roomclass.setDoublePaxPriceUsd(rs.getDouble(PstRoomClass.fieldNames[FLD_DOUBLE_PAX_PRICE_USD]));

                        roomclass.setTriplePaxPriceUsd(rs.getDouble(PstRoomClass.fieldNames[FLD_TRIPLE_PAX_PRICE_USD]));

                        roomclass.setSortIndex(rs.getInt(PstRoomClass.fieldNames[FLD_SORT_INDEX]));
                        
                        roomclass.setBreakfastNumber(rs.getInt(PstRoomClass.fieldNames[FLD_BREAKFAST_NUMBER]));

                        roomclass.setRoomClassType(rs.getInt(PstRoomClass.fieldNames[FLD_ROOM_CLASS_TYPE]));
                        
		}catch(Exception e){ }

	}



	public static boolean checkOID(long roomClassId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_ROOM_CLASS + " WHERE " + 

						PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS_ID] + " = " + roomClassId;



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

			String sql = "SELECT COUNT("+ PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS_ID] + ") FROM " + TBL_ROOM_CLASS;

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

        

        //method untuk mencari jumlah room masing2 type kamar

        public static int getNumberRoom(long oid){

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT "+ PstRoomClass.fieldNames[PstRoomClass.FLD_NUMBER_OF_ROOM] + " FROM " + TBL_ROOM_CLASS+

                                     " WHERE "+PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS_ID]+"="+oid;
                                     
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
                        System.out.println("SQL jumlah kamar"+sql);
			int count = 0;
			while(rs.next()) { 
                            count = rs.getInt(1); }
			rs.close();
			return count;

		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);

		}

	}



}


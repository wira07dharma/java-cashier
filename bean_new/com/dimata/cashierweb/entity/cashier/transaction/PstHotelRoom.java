 

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

import com.dimata.harisma.entity.masterdata.*;




public class PstHotelRoom extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 



	//public static final  String TBL_HOTEL_ROOM = "HOTEL_ROOM";

    public static final  String TBL_HOTEL_ROOM = "hotel_room";



	public static final  int FLD_HOTEL_ROOM_ID 		= 0;

	public static final  int FLD_ROOM_CLASS_ID 		= 1;

	public static final  int FLD_ROOM_NUMBER 		= 2;

        public static final  int FLD_NOTE 				= 3;

	public static final  int FLD_LAST_NIGHT_STATUS 	= 4;

	public static final  int FLD_PHONE_EXTENSION 	= 5;

	public static final  int FLD_NEW_STATUS 		= 6;

        public static final  int FLD_REG_DATE			= 7;

        public static final  int FLD_SORT_POSITION		= 8;
        public static final  int FLD_PARENT_ROOM_ID = 9;



	public static final  String[] fieldNames = {

		"HOTEL_ROOM_ID",

		"ROOM_CLASS_ID",

		"ROOM_NUMBER",

                "NOTE",

                "LAST_NIGHT_STATUS",

		"PHONE_EXTENSION",

		"NEW_STATUS",

                "REG_DATE",

                "SORT_POSITION",
                "PARENT_ROOM_ID"
	 }; 



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_LONG,

		TYPE_STRING,

		TYPE_STRING,

                TYPE_INT ,

		TYPE_STRING,

                TYPE_INT,

                TYPE_DATE,

                TYPE_INT,
                
                TYPE_LONG

	 };





    public static final int STATUS_VACANT_CLEAN	 = 0;

    public static final int STATUS_VACANT_DIRTY	 = 1;

    public static final int STATUS_OCCU_CLEAN	 = 2;

    public static final int STATUS_OCCU_DIRTY	 = 3;

    public static final int STATUS_HOUSE_USE	 = 4;
    public static final int STATUS_OUT_OF_ORDER	 = 5;
    public static final int STATUS_SERVICE_REFUSED= 6;


    public static final String[] statusKey ={"Vacant Clean", "Vacant Dirty",

        					"Occupied Clean", "Occupied Dirty", "House Used", "Out Of Order", "Service Refused"};



    public static final String[] statusValue ={"0","1","2","3", "4", "5","6"};

        

    public static final String[] statusCode ={"VC","VD","OC","OD", "HU", "OO", "SR"};



    public static final int STATUS_UPDATE_CLEAN = 0;

    public static final int STATUS_UPDATE_DIRTY = 1;

    public static final int STATUS_UPDATE_SERVICE_REFUSED = 2;
    public static final int STATUS_UPDATE_VACANT = 3;

    public static final String[] statusValueUpdate ={"0","1","2","3"};



    public static final String[] statusKeyUpdate = {"Clean", "Dirty","Service Refused","Vacant"};



    public static Vector listStatusKeyUpdate(){

    	Vector result = new Vector(1,1);

        for(int i=0;i<statusKeyUpdate.length;i++){

        	result.add(statusKeyUpdate[i]);

        }
        return result;
    }


    public static Vector listStatusValueUpdate(){

    	Vector result = new Vector(1,1);

        for(int i=0;i<statusValueUpdate.length;i++){

        	result.add(statusValueUpdate[i]);

        }

        return result;

    }



    public static Vector listStatusKey(){

    	Vector result = new Vector(1,1);

        for(int i=0;i<statusKey.length;i++){

        	result.add(statusKey[i]);

        }

        return result;

    }



    public static Vector listStatusValue(){

    	Vector result = new Vector(1,1);

        for(int i=0;i<statusValue.length;i++){

        	result.add(statusValue[i]);

        }

        return result;

    }



    public static final String[] statusStyle = {"","styleDirty"};



	public PstHotelRoom(){

	}



	public PstHotelRoom(int i) throws DBException { 

		super(new PstHotelRoom()); 

	}



	public PstHotelRoom(String sOid) throws DBException { 

		super(new PstHotelRoom(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstHotelRoom(long lOid) throws DBException { 

		super(new PstHotelRoom(0)); 

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

		return TBL_HOTEL_ROOM;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstHotelRoom().getClass().getName(); 

	}



	public long fetchExc(Entity ent) throws Exception{ 

		HotelRoom hotelroom = fetchExc(ent.getOID()); 

		ent = (Entity)hotelroom; 

		return hotelroom.getOID(); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((HotelRoom) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((HotelRoom) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent.getOID()); 

	}



	public static HotelRoom fetchExc(long oid) throws DBException{ 

		try{ 

			HotelRoom hotelroom = new HotelRoom();

			PstHotelRoom pstHotelRoom = new PstHotelRoom(oid); 

			hotelroom.setOID(oid);



			hotelroom.setRoomClassId(pstHotelRoom.getlong(FLD_ROOM_CLASS_ID));

			hotelroom.setRoomNumber(pstHotelRoom.getString(FLD_ROOM_NUMBER));

			hotelroom.setLastNightStatus(pstHotelRoom.getInt(FLD_LAST_NIGHT_STATUS));

			hotelroom.setPhoneExtension(pstHotelRoom.getString(FLD_PHONE_EXTENSION));

			hotelroom.setNote(pstHotelRoom.getString(FLD_NOTE));

                        hotelroom.setNewStatus(pstHotelRoom.getInt(FLD_NEW_STATUS));

                        hotelroom.setRegDate(pstHotelRoom.getDate(FLD_REG_DATE));

                        hotelroom.setSortPosition(pstHotelRoom.getInt(FLD_SORT_POSITION));
                        hotelroom.setParentRoomId(pstHotelRoom.getlong(FLD_PARENT_ROOM_ID));

			return hotelroom; 

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstHotelRoom(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(HotelRoom hotelroom) throws DBException{ 

		try{ 

			PstHotelRoom pstHotelRoom = new PstHotelRoom(0);



			pstHotelRoom.setLong(FLD_ROOM_CLASS_ID, hotelroom.getRoomClassId());

			pstHotelRoom.setString(FLD_ROOM_NUMBER, hotelroom.getRoomNumber());

			pstHotelRoom.setInt(FLD_LAST_NIGHT_STATUS, hotelroom.getLastNightStatus());

			pstHotelRoom.setString(FLD_PHONE_EXTENSION, hotelroom.getPhoneExtension());

			pstHotelRoom.setString(FLD_NOTE, hotelroom.getNote());

                        pstHotelRoom.setInt(FLD_NEW_STATUS, hotelroom.getNewStatus());

                        pstHotelRoom.setDate(FLD_REG_DATE, hotelroom.getRegDate());

                        pstHotelRoom.setInt(FLD_SORT_POSITION, hotelroom.getSortPosition());

                        pstHotelRoom.setLong(FLD_PARENT_ROOM_ID, hotelroom.getParentRoomId());

			pstHotelRoom.insert(); 

			hotelroom.setOID(pstHotelRoom.getlong(FLD_HOTEL_ROOM_ID));

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstHotelRoom(0),DBException.UNKNOWN); 

		}

		return hotelroom.getOID();

	}



	public static long updateExc(HotelRoom hotelroom) throws DBException{ 

		try{ 

			if(hotelroom.getOID() != 0){ 

				PstHotelRoom pstHotelRoom = new PstHotelRoom(hotelroom.getOID());



				pstHotelRoom.setLong(FLD_ROOM_CLASS_ID, hotelroom.getRoomClassId());

				pstHotelRoom.setString(FLD_ROOM_NUMBER, hotelroom.getRoomNumber());

				pstHotelRoom.setInt(FLD_LAST_NIGHT_STATUS, hotelroom.getLastNightStatus());

				pstHotelRoom.setString(FLD_PHONE_EXTENSION, hotelroom.getPhoneExtension());

				pstHotelRoom.setString(FLD_NOTE, hotelroom.getNote());

                                pstHotelRoom.setInt(FLD_NEW_STATUS, hotelroom.getNewStatus());

                                pstHotelRoom.setDate(FLD_REG_DATE, hotelroom.getRegDate());

                                pstHotelRoom.setInt(FLD_SORT_POSITION, hotelroom.getSortPosition());

                                pstHotelRoom.setLong(FLD_PARENT_ROOM_ID, hotelroom.getParentRoomId());

				pstHotelRoom.update(); 

				return hotelroom.getOID();



			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstHotelRoom(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstHotelRoom pstHotelRoom = new PstHotelRoom(oid);

			pstHotelRoom.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstHotelRoom(0),DBException.UNKNOWN); 

		}

		return oid;

	}



	public static Vector listAll(){ 

		return list(0, 500, "",""); 

	}
        
    public static Vector listRoomByParentOID(long oid){ 
                String where = fieldNames[FLD_PARENT_ROOM_ID]+"="+oid;       
		return list(0, 500, where, ""); 
	}


	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){

		Vector lists = new Vector(); 

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT * FROM " + TBL_HOTEL_ROOM; 

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;

			if(order != null && order.length() > 0)

				sql = sql + " ORDER BY " + order;

			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;





            //System.out.println("------------->"+sql);



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {

				HotelRoom hotelroom = new HotelRoom();

				resultToObject(rs, hotelroom);

				lists.add(hotelroom);

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



	public static Vector listInnerJoinRoomClass(int limitStart,int recordToGet, String whereClause, String order){

		Vector lists = new Vector();

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT * FROM " + TBL_HOTEL_ROOM +" r " +
                             " inner join " +  PstRoomClass.TBL_ROOM_CLASS +" c on c."+
                             PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS_ID]+"=r."+fieldNames[FLD_ROOM_CLASS_ID];

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;

			if(order != null && order.length() > 0)

				sql = sql + " ORDER BY " + order;

			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;





            //System.out.println("------------->"+sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {

				HotelRoom hotelroom = new HotelRoom();

				resultToObject(rs, hotelroom);

				lists.add(hotelroom);

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



	public static void resultToObject(ResultSet rs, HotelRoom hotelroom){

		try{

			hotelroom.setOID(rs.getLong(PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]));

			hotelroom.setRoomClassId(rs.getLong(PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_CLASS_ID]));

			hotelroom.setRoomNumber(rs.getString(PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER]));

			hotelroom.setLastNightStatus(rs.getInt(PstHotelRoom.fieldNames[PstHotelRoom.FLD_LAST_NIGHT_STATUS]));

			hotelroom.setPhoneExtension(rs.getString(PstHotelRoom.fieldNames[PstHotelRoom.FLD_PHONE_EXTENSION]));

			hotelroom.setNote(rs.getString(PstHotelRoom.fieldNames[PstHotelRoom.FLD_NOTE]));

                        hotelroom.setNewStatus(rs.getInt(PstHotelRoom.fieldNames[PstHotelRoom.FLD_NEW_STATUS]));

                        hotelroom.setRegDate(rs.getDate(PstHotelRoom.fieldNames[PstHotelRoom.FLD_REG_DATE]));

                        hotelroom.setSortPosition(rs.getInt(PstHotelRoom.fieldNames[PstHotelRoom.FLD_SORT_POSITION]));
                        hotelroom.setParentRoomId(rs.getLong(PstHotelRoom.fieldNames[PstHotelRoom.FLD_PARENT_ROOM_ID]));


		}catch(Exception e){ }

	}



	public static boolean checkOID(long hotelRoomId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_HOTEL_ROOM + " WHERE " + 

						PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID] + " = " + hotelRoomId;



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

			String sql = "SELECT COUNT("+ PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID] + ") FROM " + TBL_HOTEL_ROOM;

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;



            System.out.println(sql);



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



    public static void updateRoomStatus(long oid){

		try {

            String sql = "UPDATE "+TBL_HOTEL_ROOM+" SET "+PstHotelRoom.fieldNames[PstHotelRoom.FLD_NEW_STATUS]+

                		 " = "+PstHotelRoom.STATUS_VACANT_DIRTY +" WHERE "+PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]+

                         " = '"+oid+"'";



            DBHandler.execUpdate(sql);



        }catch(Exception exc){

            	System.out.println("...."+exc.toString());

		}

    }





    public static Vector listRoomByClass(long roomClassId)

    {

    	DBResultSet dbrs = null;

		Vector result = new Vector(1,1);

		try{

			String sql = "  SELECT RC."+PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS_ID]+

                		 ", RC."+PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS]+

                         ", HR."+PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]+

                         ", HR."+PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER]+

                         ", HR."+PstHotelRoom.fieldNames[PstHotelRoom.FLD_NEW_STATUS]+

                         ", HR."+PstHotelRoom.fieldNames[PstHotelRoom.FLD_PHONE_EXTENSION]+

                         ", HR."+PstHotelRoom.fieldNames[PstHotelRoom.FLD_REG_DATE]+

                         ", HR."+PstHotelRoom.fieldNames[PstHotelRoom.FLD_SORT_POSITION]+

                         ", HR."+PstHotelRoom.fieldNames[PstHotelRoom.FLD_PARENT_ROOM_ID]+
                         
                         " FROM " + TBL_HOTEL_ROOM +  " HR "+

                         " INNER JOIN "+PstRoomClass.TBL_ROOM_CLASS+ " RC "+

                         " ON RC."+PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS_ID]+

                         " = HR."+PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_CLASS_ID]+

                         " WHERE " +

						 " HR."+PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_CLASS_ID]+

                         " = "+roomClassId+

                         " ORDER BY HR."+PstHotelRoom.fieldNames[PstHotelRoom.FLD_SORT_POSITION];



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();



			while(rs.next()) {

                Vector temp = new Vector(1,1);

                RoomClass roomClass = new RoomClass();

                HotelRoom hotelRoom = new HotelRoom();



                roomClass.setOID(rs.getLong(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS_ID]));

                roomClass.setRoomClass(rs.getString(PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS]));



                hotelRoom.setOID(rs.getLong(PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]));

                hotelRoom.setRoomNumber(rs.getString(PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER]));

                hotelRoom.setNewStatus(rs.getInt(PstHotelRoom.fieldNames[PstHotelRoom.FLD_NEW_STATUS]));

                hotelRoom.setPhoneExtension(rs.getString(PstHotelRoom.fieldNames[PstHotelRoom.FLD_PHONE_EXTENSION]));

                hotelRoom.setRegDate(rs.getDate(PstHotelRoom.fieldNames[PstHotelRoom.FLD_REG_DATE]));

                hotelRoom.setSortPosition(rs.getInt(PstHotelRoom.fieldNames[PstHotelRoom.FLD_SORT_POSITION]));
                hotelRoom.setParentRoomId(rs.getLong(PstHotelRoom.fieldNames[PstHotelRoom.FLD_PARENT_ROOM_ID]));

                temp.add(roomClass);

                temp.add(hotelRoom);

                result.add(temp);

            }

			rs.close();

            return result;



		}catch(Exception e){

			System.out.println("err : "+e.toString());

		}finally{

			DBResultSet.close(dbrs);

		}

        return new Vector(1,1);

    }





    public static void updateStatus(int status, long oid)

    {

        if(oid!=0){

	        try{

	

	            HotelRoom hr = new HotelRoom();

                hr = PstHotelRoom.fetchExc(oid);



                switch(hr.getLastNightStatus()){

	                case STATUS_OCCU_CLEAN :
                            switch (status){
                                case STATUS_UPDATE_CLEAN: status = STATUS_OCCU_CLEAN; break;
                                case STATUS_UPDATE_DIRTY : status = STATUS_OCCU_DIRTY; break;
                                case STATUS_UPDATE_SERVICE_REFUSED: status = STATUS_SERVICE_REFUSED; break;
                                case STATUS_UPDATE_VACANT: status = STATUS_VACANT_CLEAN; break;
                            }
	    	            break;

	                case STATUS_OCCU_DIRTY :
                            switch (status){
                                case STATUS_UPDATE_CLEAN: status = STATUS_OCCU_CLEAN; break;
                                case STATUS_UPDATE_DIRTY : status = STATUS_OCCU_DIRTY; break;
                                case STATUS_UPDATE_SERVICE_REFUSED: status = STATUS_SERVICE_REFUSED; break;
                                case STATUS_UPDATE_VACANT: status = STATUS_VACANT_DIRTY; break;
                            }
	    	            break;

	

	                 case STATUS_VACANT_CLEAN :

	                    if(status==STATUS_UPDATE_CLEAN){

	                       	status = STATUS_VACANT_CLEAN ;

		                }

		                else{

	                        status = STATUS_VACANT_DIRTY;

		                }

	    	            break;

	

	                 case STATUS_VACANT_DIRTY :

	                    if(status==STATUS_UPDATE_CLEAN){

	                       	status = STATUS_VACANT_CLEAN ;

		                }

		                else{

	                        status = STATUS_VACANT_DIRTY;

		                }

	    	            break;

	                case STATUS_SERVICE_REFUSED:
                            switch (status){
                                case STATUS_UPDATE_CLEAN: status = STATUS_OCCU_CLEAN; break;
                                case STATUS_UPDATE_DIRTY : status = STATUS_OCCU_DIRTY; break;
                                case STATUS_UPDATE_SERVICE_REFUSED: status = STATUS_SERVICE_REFUSED; break;
                                case STATUS_UPDATE_VACANT: status = STATUS_VACANT_DIRTY; break;
                            }
	    	            break;


                }



	    		String sql = "UPDATE "+TBL_HOTEL_ROOM+" SET "+PstHotelRoom.fieldNames[PstHotelRoom.FLD_NEW_STATUS]+

	                		 " = "+status+" WHERE "+PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]+

	                         " = '"+oid+"'";

	

	            DBHandler.execUpdate(sql);

	

	        }catch(Exception exc){

	            	System.out.println("...."+exc.toString());

			}

        }

    }



    public static void updateStatusForReservation(int status, long oid)

    {

        if(oid!=0){

	        try{

	

	            HotelRoom hr = new HotelRoom();

                hr = PstHotelRoom.fetchExc(oid);



	    		String sql = "UPDATE "+TBL_HOTEL_ROOM+" SET "+PstHotelRoom.fieldNames[PstHotelRoom.FLD_NEW_STATUS]+

	                		 " = "+status+", "+PstHotelRoom.fieldNames[PstHotelRoom.FLD_LAST_NIGHT_STATUS]+"="+status+

                             " WHERE "+PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]+

	                         " = '"+oid+"'";

	

	            DBHandler.execUpdate(sql);

	

	        }catch(Exception exc){

	            	System.out.println("...."+exc.toString());

			}

        }

    }



    

    public static boolean isExtensionExist(HotelRoom hotelRoom){

        boolean found = false;

        if(hotelRoom!=null && hotelRoom.getPhoneExtension()!=null && hotelRoom.getPhoneExtension().length()>0){

            // check multiple on current entry

            StringTokenizer st = new StringTokenizer(hotelRoom.getPhoneExtension(), "/");

            Hashtable hash = new Hashtable();

            String temp;

            while(st.hasMoreTokens()){

                temp = st.nextToken();

                found = temp.equals(hash.put(temp,temp));

            }



            if(!found){

                //check multiple on persistent

                String where = PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]+

                        "<>"+hotelRoom.getOID();
                
                if(hotelRoom.getParentRoomId()!=0){
                    where=where + " AND "+ PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]+
                        "<>"+hotelRoom.getParentRoomId();
                }
                
                
                Vector childRooms = listRoomByParentOID(hotelRoom.getOID());
                if(childRooms!=null && childRooms.size()>0){
                    for(int cr=0;cr<childRooms.size();cr++){
                        HotelRoom hr= (HotelRoom) childRooms.get(cr);
                        where=where + " AND "+ PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]+
                        "<>"+hr.getOID();
                    }
                }
                

                Vector list = PstHotelRoom.list(0,0,where,"");

                int listSize = list.size();

                HotelRoom room = new HotelRoom();

                hash.clear();

                for(int i = 0; i <listSize; i++){

                    room = (HotelRoom) list.get(i);

                    st = new StringTokenizer(room.getPhoneExtension(),"/");

                    while(st.hasMoreTokens()){

                        temp = st.nextToken();

                        hash.put(temp,temp);

                    }

                }

                st = new StringTokenizer(hotelRoom.getPhoneExtension(),"/");

                while(st.hasMoreTokens() && !found){

                    found = hash.containsKey(st.nextToken());

                }

            }

        }

        return found;

    }



    /**

     * methode for checking if room number already exist

     * return true if exist

     * by wpulantara

     * @param hotelRoom

     * @return

     */

    public static boolean isRoomNumberExist(HotelRoom hotelRoom){

        boolean found = false;

        if(hotelRoom!=null){

            String where = PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER]+

                    "='"+hotelRoom.getRoomNumber()+"'"+

                    " AND "+PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]+

                    "<>"+hotelRoom.getOID();

            found = PstHotelRoom.getCount(where)>0;

        }

        return found;

    }

    public static int isRoomWithParentExist(){
		DBResultSet dbrs = null;	
                int cnt=0;
		try{
			String sql = "SELECT COUNT("+PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]+") AS CNT FROM " + 
                                TBL_HOTEL_ROOM + " WHERE " + PstHotelRoom.fieldNames[PstHotelRoom.FLD_PARENT_ROOM_ID] + " <> 0 "; ;
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();                        
			while(rs.next()) { 
                            cnt = rs.getInt("CNT");                            
                        }
			rs.close();
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
			DBResultSet.close(dbrs);
			return cnt;
		}
    }

    public static long getParentRoomOid(long roomID){
		DBResultSet dbrs = null;	
                long oid=0;
		try{
			String sql = "SELECT "+PstHotelRoom.fieldNames[PstHotelRoom.FLD_PARENT_ROOM_ID]+" AS OID FROM " + 
                                TBL_HOTEL_ROOM + " WHERE " + PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID] + " = '"
                                +roomID+"'"; 
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();                        
			while(rs.next()) { 
                            oid = rs.getLong("OID");                            
                        }
			rs.close();
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
			DBResultSet.close(dbrs);
			return oid;
		}
    }
    
    public static Vector getChildRoomsOid(long roomID)    {
		DBResultSet dbrs = null;	
        Vector vct = new Vector();
		try{
			String sql = "SELECT "+PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID]+" AS OID FROM " + 
                                TBL_HOTEL_ROOM + " WHERE " + PstHotelRoom.fieldNames[PstHotelRoom.FLD_PARENT_ROOM_ID] + " = '"
                                +roomID+"'"; 
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();                        
                        long oid=0;
			while(rs.next()) { 
                            oid = rs.getLong("OID");                            
                            vct.add(new Long(oid));
                        }
			rs.close();
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
			DBResultSet.close(dbrs);
			return vct;
		}
        
    }
    

}


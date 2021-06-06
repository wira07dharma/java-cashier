

package com.dimata.hanoman.entity.masterdata;

/**
 *
 * @author Witar
 */
import com.dimata.qdep.entity.Entity;

public class CustomePackageSchedule extends Entity {

    private long customScheduleId = 0;
    private long customPackBillingId = 0;
    private long roomId = 0;
    private long tableId = 0;
    private String startDate = "";
    private String endDate = "";
    private long pic = 0;
    private int status = 0;
    
    //ADITIONAL
    
    private long locationID = 0;
    private String reservationNum = "";
    private String salutation ="";
    private String personName="";

    public long getCustomScheduleId() {
        return customScheduleId;
    }

    public void setCustomScheduleId(long customScheduleId) {
        this.customScheduleId = customScheduleId;
    }

    public long getCustomPackBillingId() {
        return customPackBillingId;
    }

    public void setCustomPackBillingId(long customPackBillingId) {
        this.customPackBillingId = customPackBillingId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getPic() {
        return pic;
    }

    public void setPic(long pic) {
        this.pic = pic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the locationID
     */
    public long getLocationID() {
        return locationID;
    }

    /**
     * @param locationID the locationID to set
     */
    public void setLocationID(long locationID) {
        this.locationID = locationID;
    }

    /**
     * @return the reservationNum
     */
    public String getReservationNum() {
        return reservationNum;
    }

    /**
     * @param reservationNum the reservationNum to set
     */
    public void setReservationNum(String reservationNum) {
        this.reservationNum = reservationNum;
    }

    /**
     * @return the salutation
     */
    public String getSalutation() {
        return salutation;
    }

    /**
     * @param salutation the salutation to set
     */
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * @return the personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * @param personName the personName to set
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

}

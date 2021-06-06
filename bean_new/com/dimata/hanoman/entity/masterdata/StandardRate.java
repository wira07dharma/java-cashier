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
package com.dimata.hanoman.entity.masterdata;

/* package java */
import java.util.Date;



/* package qdep */

import com.dimata.qdep.entity.*;

public class StandardRate extends Entity {

    private long employeeId;
    private long countryId;
    private Date createDate;
    private Date historyDate;
    private double rate;
    private String description = "";
    private int status;
    private long currencyTypeId; //updated by gwawan@dimata 20080508

    public long getCurrencyTypeId() {
        return currencyTypeId;
    }

    public void setCurrencyTypeId(long currencyId) {
        this.currencyTypeId = currencyId;
    }
    
    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            description = "";
        }
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

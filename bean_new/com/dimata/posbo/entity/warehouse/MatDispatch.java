package com.dimata.posbo.entity.warehouse;

/* package java */

import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;
import com.dimata.util.Formater;
import java.util.Vector;

public class MatDispatch extends Entity {
    private long locationId = 0;
    private long dispatchTo = 0;
    private int locationType = 0;
    private Date dispatchDate = new Date();
    private String dispatchCode = "";
    private int dispatchCodeCounter = 0;
    private int dispatchStatus = 0;
    private String remark = "";
    private String invoiceSupplier = "";

    /** Holds value of property transferStatus. */
    private int transferStatus;
    private Date last_update = new Date();

    /**
     * Ari_wiweka 20130905
     * menambah cash bill main untuk cashier invoice
     * @return
     */
    private long cashBillMainId = 0;
    
    private Vector listItem = new Vector();
    
    public Vector getListItem() {
        return listItem;
    }

    public void setListItem(MatDispatchItem matDispatchItem) {
        this.listItem.add(matDispatchItem);
    }
    
    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public long getDispatchTo() {
        return dispatchTo;
    }

    public void setDispatchTo(long dispatchTo) {
        this.dispatchTo = dispatchTo;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public Date getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public String getDispatchCode() {
        return dispatchCode;
    }

    public void setDispatchCode(String dispatchCode) {
        if (dispatchCode == null) {
            dispatchCode = "";
        }
        this.dispatchCode = dispatchCode;
    }

    public int getDispatchCodeCounter() {
        return dispatchCodeCounter;
    }

    public void setDispatchCodeCounter(int dispatchCodeCounter) {
        this.dispatchCodeCounter = dispatchCodeCounter;
    }

    public int getDispatchStatus() {
        return dispatchStatus;
    }

    public void setDispatchStatus(int dispatchStatus) {
        this.dispatchStatus = dispatchStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        if (remark == null) {
            remark = "";
        }
        this.remark = remark;
    }

    public String getInvoiceSupplier() {
        return invoiceSupplier;
    }

    public void setInvoiceSupplier(String invoiceSupplier) {
        if (invoiceSupplier == null) {
            invoiceSupplier = "";
        }
        this.invoiceSupplier = invoiceSupplier;
    }

    /** Getter for property transferStatus.
     * @return Value of property transferStatus.
     *
     */
    public int getTransferStatus() {
        return this.transferStatus;
    }

    /** Setter for property transferStatus.
     * @param transferStatus New value of property transferStatus.
     *
     */
    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    /**
     * @return the cash_bill_main_id
     */
    public long getCashBillMainId() {
        return cashBillMainId;
}

    /**
     * @param cash_bill_main_id the cash_bill_main_id to set
     */
    public void setCashBillMainId(long cashBillMainId) {
        this.cashBillMainId = cashBillMainId;
    }

    //by dyas
    //tambah methods getLogDetail
    public String getLogDetail(Entity prevDoc) {
       String includePpnWord = "";
        MatDispatch prevMatDispatch = (MatDispatch)prevDoc;
        Location location1 = null;
        Location location2 = null;
        try{
          if(this!=null && getDispatchTo()!=0 && (prevMatDispatch == null || prevMatDispatch.getOID()==0 || prevMatDispatch.getDispatchTo() != this.getDispatchTo() ) )
          {
              location1 = PstLocation.fetchExc(getDispatchTo());
}
          //=====================================================================================
          if(this!=null && getLocationId()!=0 )
          {
            location2 = PstLocation.fetchExc(getLocationId());
          }
        }catch(Exception exc){

        }

        return (prevMatDispatch == null ||  prevMatDispatch.getOID()==0 || prevMatDispatch.getLocationId()==0 ||  prevMatDispatch.getLocationId() != this.getLocationId() ?
                ("Lokasi Asal : "+ location2.getName() +" ;" ) : "" )+

                (prevMatDispatch == null ||  prevMatDispatch.getOID()==0 || prevMatDispatch.getDispatchTo()==0 ||  prevMatDispatch.getDispatchTo() != this.getDispatchTo() ?
                ("Lokasi Tujuan : "+ location1.getName() +" ;" ) : "" ) +

                (prevMatDispatch == null ||  prevMatDispatch.getOID()==0 || !Formater.formatDate(prevMatDispatch.getDispatchDate(), "yyyy-MM-dd").equals(Formater.formatDate(this.getDispatchDate(), "yyyy-MM-dd"))?
                (" Date Time : " + Formater.formatDate(this.getDispatchDate(), "yyyy-MM-dd") + " ;") : "") +

                (prevMatDispatch == null ||  prevMatDispatch.getOID()==0 ||  prevMatDispatch.getDispatchStatus()!= this.getDispatchStatus() ?
                (" Status : " + I_DocStatus.fieldDocumentStatus[this.getDispatchStatus()] +" ;") : "" )+

                ((prevMatDispatch == null || prevMatDispatch.getOID()==0 || prevMatDispatch.getRemark()==null || prevMatDispatch.getRemark().compareToIgnoreCase(this.getRemark())!=0) ?
                (" Remark : " + this.getRemark()+" ;") : "" );

}
}

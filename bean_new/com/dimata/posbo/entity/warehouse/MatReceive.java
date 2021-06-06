package com.dimata.posbo.entity.warehouse;

/* package java */

import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.logger.I_LogHistory;
import com.dimata.common.entity.payment.CurrencyType;
import com.dimata.common.entity.payment.PstCurrencyType;
import com.dimata.posbo.entity.purchasing.PstPurchaseOrder;
import com.dimata.posbo.entity.purchasing.PurchaseOrder;
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;
import com.dimata.util.Formater;
import java.util.Vector;

public class MatReceive extends Entity implements I_LogHistory {

    private long locationId;
    private long receiveFrom = 0;
    private int locationType = 0;
    private Date receiveDate = new Date();
    private String recCode = "";
    private int recCodeCnt = 0;
    private int receiveStatus = 0;
    private int receiveSource = 0;
    private long supplierId = 0;
    private long purchaseOrderId = 0;
    private long dispatchMaterialId = 0;
    private long returnMaterialId = 0;
    private String remark = "";
    private String invoiceSupplier = "";
    private double totalPpn = 0.00;

    // NEW
    private int reason = 0;
    private int transferStatus;
    private int termOfPayment = 0;
    private int creditTime = 0;
    private Date expiredDate = new Date();
    private double discount = 0.0;
    private Date lastUpdate = new Date();
    private long currencyId = 0;
    private double transRate = 0;

   //new include ppn
    private int includePpn = 0;

    public long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public int getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(int creditTime) {
        this.creditTime = creditTime;
    }

    public int getTermOfPayment() {
        return termOfPayment;
    }

    public void setTermOfPayment(int termOfPayment) {
        this.termOfPayment = termOfPayment;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public long getReceiveFrom() {
        return receiveFrom;
    }

    public void setReceiveFrom(long receiveFrom) {
        this.receiveFrom = receiveFrom;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getRecCode() {
        return recCode;
    }

    public void setRecCode(String recCode) {
        if (recCode == null) {
            recCode = "";
        }
        this.recCode = recCode;
    }

    public int getRecCodeCnt() {
        return recCodeCnt;
    }

    public void setRecCodeCnt(int recCodeCnt) {
        this.recCodeCnt = recCodeCnt;
    }

    public int getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(int receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public int getReceiveSource() {
        return receiveSource;
    }

    public void setReceiveSource(int receiveSource) {
        this.receiveSource = receiveSource;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public long getDispatchMaterialId() {
        return dispatchMaterialId;
    }

    public void setDispatchMaterialId(long dispatchMaterialId) {
        this.dispatchMaterialId = dispatchMaterialId;
    }

    public long getReturnMaterialId() {
        return returnMaterialId;
    }

    public void setReturnMaterialId(long returnMaterialId) {
        this.returnMaterialId = returnMaterialId;
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

    public double getTotalPpn() {
        return totalPpn;
    }

    public void setTotalPpn(double totalPpn) {
        this.totalPpn = totalPpn;
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
     * Getter for property transRate.
     * @return Value of property transRate.
     */
    public double getTransRate() {
        return transRate;
    }
    
    /**
     * Setter for property transRate.
     * @param transRate New value of property transRate.
     */
    public void setTransRate(double transRate) {
        this.transRate = transRate;
    }

    /**
     * @return the includePpn
     */
    public int getIncludePpn() {
        return includePpn;
    }

    /**
     * @param includePpn the includePpn to set
     */
    public void setIncludePpn(int includePpn) {
        this.includePpn = includePpn;
    }
    
    public String getLogDetail(Entity prevDoc) {
       String includePpnWord = "";
        MatReceive prevMatReceive = (MatReceive)prevDoc;
        ContactList contactList = null;
        Location location = null;
        CurrencyType currencyType = null;
        PurchaseOrder po = null;
        try{
          if(this!=null && getSupplierId()!=0 && (prevMatReceive == null || prevMatReceive.getOID()==0 || prevMatReceive.getSupplierId() != this.getSupplierId() ) )
          {
              contactList = PstContactList.fetchExc(getSupplierId());
}
          //=====================================================================================
          if(this!=null && getLocationId()!=0 )
          {
            location = PstLocation.fetchExc(getLocationId());
          }
          if(this!=null && getPurchaseOrderId()!=0){
              po = PstPurchaseOrder.fetchExc(getPurchaseOrderId());
          }

          if(this!=null && getCurrencyId()!=0 )
          {
            currencyType = PstCurrencyType.fetchExc(getCurrencyId());
          }
          if(getIncludePpn() == 0)
          {
              includePpnWord = "TIDAK";
          }
          else
          {
              includePpnWord = "YA";
          }
        }catch(Exception exc){

        }

        return (prevMatReceive == null ||  prevMatReceive.getOID()==0 || prevMatReceive.getLocationId()==0 ||  prevMatReceive.getLocationId() != this.getLocationId() ?
                ("Location : "+ location.getName() +" ;" ) : "" ) +

                (prevMatReceive == null ||  prevMatReceive.getOID()==0 || !Formater.formatDate(prevMatReceive.getReceiveDate(), "yyyy-MM-dd").equals(Formater.formatDate(this.getReceiveDate(), "yyyy-MM-dd"))?
                (" Date Time : " + Formater.formatDate(this.getReceiveDate(), "yyyy-MM-dd") + " ;") : "") +

                (contactList!=null ?
                (" Supplier : " + contactList.getCompName()+" ;"
                +" Contact : " + contactList.getPersonName()+" ;"
                +" Address : " + contactList.getBussAddress()+" ;"
                +" Telephone. : " + contactList.getTelpNr()+" ;")  :"")   +

                ((this!=null && getPurchaseOrderId()!=0)?
                ("Nomor PO : "+ po.getPoCode() +" ;" ) : "" )+

                (prevMatReceive == null ||  prevMatReceive.getOID()==0 || !prevMatReceive.getInvoiceSupplier().equals(this.getInvoiceSupplier()) ?
                (" Nota Supplier : " + this.getInvoiceSupplier() +" ;") : "") +

                (prevMatReceive == null ||  prevMatReceive.getOID()==0 || prevMatReceive.getCurrencyId()!=this.getCurrencyId()?
                (" Currency : " + currencyType.getCode() +" ;" ) : "") +
                
                (prevMatReceive == null ||  prevMatReceive.getOID()==0 ||  prevMatReceive.getTransRate() != this.getTransRate() ?
                (" Exchange Rate : " + Formater.formatNumber(this.getTransRate(), "###.###") +" ;") : "") +
                
                (prevMatReceive == null ||  prevMatReceive.getOID()==0 ||  prevMatReceive.getTermOfPayment() != this.getTermOfPayment() ?
                (" Terms : " + PstPurchaseOrder.fieldsPaymentType[this.getTermOfPayment()] +" ;") : "") +

                (prevMatReceive == null ||  prevMatReceive.getOID()==0 || prevMatReceive.getCreditTime() != this.getCreditTime() ?
                (" Days : " + this.getCreditTime() +" ;") : "") +

                ((prevMatReceive == null || prevMatReceive.getOID()==0 || prevMatReceive.getRemark()==null || prevMatReceive.getRemark().compareToIgnoreCase(this.getRemark())!=0) ?
                (" Remark : " + this.getRemark()+" ;") : "" ) +

                ((prevMatReceive == null || prevMatReceive.getOID()==0 || prevMatReceive.getReceiveStatus()!=this.getReceiveStatus()) ?
                (" Status : " + I_DocStatus.fieldDocumentStatus[this.getReceiveStatus()]) : "" ) +

                ((prevMatReceive == null || prevMatReceive.getOID()==0 || prevMatReceive.getIncludePpn()!=this.getIncludePpn()) ?
                (" Include Ppn : " + includePpnWord + " ;") : "");

    }
    
    
    private Vector listItem = new Vector();
        public Vector getListItem() {
            return listItem;
        }

        public void setListItem(MatReceiveItem recItem) {
            this.listItem.add(recItem);
        }
}

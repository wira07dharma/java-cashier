package com.dimata.posbo.entity.warehouse;

/* package java */

import com.dimata.common.entity.logger.I_LogHistory;
import java.util.Date;

import com.dimata.posbo.entity.masterdata.*;
/* package qdep */
import com.dimata.qdep.entity.*;

/**
 * update opie-eyek 20130812 tambahkan receiveDate untuk last receive date
 * @author dimata005
 */
public class MatReceiveItem extends Entity implements I_LogHistory{

    private long receiveMaterialId;
    private long materialId;
    private Date expiredDate = new Date();
    private long unitId = 0;
    private double cost = 0.00;
    private long currencyId = 0;
    private double qty = 0;
    private double discount = 0.00;
    private double total = 0.00;
    private double residueQty;
    private double discount2 = 0.00;
    private double discNominal = 0.00;
    private double currBuyingPrice = 0.00;
    private double forwarderCost = 0;
    private Material materialTarget = new Material();
    private Unit unitTarget = new Unit();
    private Date receiveDate = new Date();
    private String recCode;
    private long supplierId;
    private double qtyEntry=0.0;
    private long unitKonversi;
    
    //tambahkan exhange rate
    private double exchangeRate=0.0;
    
    //price order
    private double priceKonv=0.0;
    
    //untuk bonus
    private int bonus;
    
    public double getCurrBuyingPrice() {
        return currBuyingPrice;
    }

    public void setCurrBuyingPrice(double currBuyingPrice) {
        this.currBuyingPrice = currBuyingPrice;
    }

    public double getDiscNominal() {
        return discNominal;
    }

    public void setDiscNominal(double discNominal) {
        this.discNominal = discNominal;
    }

    public double getDiscount2() {
        return discount2;
    }

    public void setDiscount2(double discount2) {
        this.discount2 = discount2;
    }

    public long getReceiveMaterialId() {
        return receiveMaterialId;
    }

    public void setReceiveMaterialId(long receiveMaterialId) {
        this.receiveMaterialId = receiveMaterialId;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public double getResidueQty() {
        return this.residueQty;
    }
    
    public void setResidueQty(double residueQty) {
        this.residueQty = residueQty;
    }
    
    public double getForwarderCost() {
        return forwarderCost;
    }
    
    public void setForwarderCost(double forwarderCost) {
        this.forwarderCost = forwarderCost;
    }

    /**
     * @return the materialTarget
     */
    public Material getMaterialTarget() {
        return materialTarget;
    }

    /**
     * @param materialTarget the materialTarget to set
     */
    public void setMaterialTarget(Material materialTarget) {
        this.materialTarget = materialTarget;
    }

    /**
     * @return the unitTarget
     */
    public Unit getUnitTarget() {
        return unitTarget;
    }

    /**
     * @param unitTarget the unitTarget to set
     */
    public void setUnitTarget(Unit unitTarget) {
        this.unitTarget = unitTarget;
    }

    /**
     * @return the receiveDate
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * @param receiveDate the receiveDate to set
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * @return the recCode
     */
    public String getRecCode() {
        return recCode;
    }

    /**
     * @param recCode the recCode to set
     */
    public void setRecCode(String recCode) {
        this.recCode = recCode;
    }

    /**
     * @return the supplierId
     */
    public long getSupplierId() {
        return supplierId;
    }

    /**
     * @param supplierId the supplierId to set
     */
    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getLogDetail(Entity prevDocItem) {
        MatReceiveItem prevMatReceiveItem = (MatReceiveItem)prevDocItem;
        Material material = null;
        try
        {
            if(this!=null && getMaterialId()!=0 )
            {
                material = PstMaterial.fetchExc(getMaterialId());
            }
        }
        catch(Exception e)
        {
        }
        return  "SKU : " + material.getSku()+

                (prevMatReceiveItem == null ||  prevMatReceiveItem.getOID()==0 || prevMatReceiveItem.getMaterialId() == 0 ||  prevMatReceiveItem.getMaterialId() != this.getMaterialId() ?
                (" Nama Barang : " + material.getName()) : "")+

                (prevMatReceiveItem == null ||  prevMatReceiveItem.getOID()==0 || prevMatReceiveItem.getDiscount() != this.getDiscount() ?
                ( " Diskon : " + this.getDiscount() ) : "" ) +

                (prevMatReceiveItem == null ||  prevMatReceiveItem.getOID()==0 || prevMatReceiveItem.getDiscount2() != this.getDiscount2() ?
                ( " Diskon 2 : " + this.getDiscount2() ) : "" ) +

                (prevMatReceiveItem == null ||  prevMatReceiveItem.getOID()==0 || prevMatReceiveItem.getDiscNominal() != this.getDiscNominal() ?
                ( " Diskon Nominal : " + this.getDiscNominal() ) : "" )+

                (prevMatReceiveItem == null ||  prevMatReceiveItem.getOID()==0 || prevMatReceiveItem.getCurrBuyingPrice()!= this.getCurrBuyingPrice() ?
                ( " Current Buying Price : " + this.getCurrBuyingPrice() ) : "" )+

                (prevMatReceiveItem == null ||  prevMatReceiveItem.getOID()==0 || prevMatReceiveItem.getCost()!= this.getCost() ?
                ( " Cost : " + this.getCost() ) : "" )+

                (prevMatReceiveItem == null ||  prevMatReceiveItem.getOID()==0 || prevMatReceiveItem.getQty()!= this.getQty() ?
                ( " Qty : " + this.getQty() ) : "" )+

                (prevMatReceiveItem == null ||  prevMatReceiveItem.getOID()==0 || prevMatReceiveItem.getTotal()!= this.getTotal() ?
                ( " Total : " + this.getTotal() ) : "" )
                ;
}

    /**
     * @return the qtyEntry
     */
    public double getQtyEntry() {
        return qtyEntry;
    }

    /**
     * @param qtyEntry the qtyEntry to set
     */
    public void setQtyEntry(double qtyEntry) {
        this.qtyEntry = qtyEntry;
    }

    /**
     * @return the unitKonversi
     */
    public long getUnitKonversi() {
        return unitKonversi;
    }

    /**
     * @param unitKonversi the unitKonversi to set
     */
    public void setUnitKonversi(long unitKonversi) {
        this.unitKonversi = unitKonversi;
    }

    /**
     * @return the exchangeRate
     */
    public double getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate the exchangeRate to set
     */
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * @return the priceKonv
     */
    public double getPriceKonv() {
        return priceKonv;
    }

    /**
     * @param priceKonv the priceKonv to set
     */
    public void setPriceKonv(double priceKonv) {
        this.priceKonv = priceKonv;
    }

    /**
     * @return the bonus
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * @param bonus the bonus to set
     */
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }




}

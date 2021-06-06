/**

 * Created by IntelliJ IDEA.

 * User: eka

 * Date: Sep 14, 2004

 * Time: 12:04:37 PM

 * To change this template use Options | File Templates.

 */

package com.dimata.hanoman.entity.cashier;



import com.dimata.qdep.entity.Entity;



import java.util.Date;

public class CashCashier extends Entity {
    private long cashMasterId;
    private Date openDate;
    private long appUserId;
    private long svpOid;
    private String svpName="";
    private long svpCloseOid=0;
    private String svpCloseName="";

    //variable shift
    private long shiftId;

    //untuk close date
    private Date closeDate;

    // untuk cash_balance
    private double balanceValue;
    private long currencyId=0;
    private int type=0;
    private long locationId=0;

    //untuk total opening cashier
    private double rupiah;
    private double usd;
    private double subTotal1;
    private double subTotal2;


    //untuk amount
    private double amount1;
    private double amount2;

    //update by fredy (2014-01-16)
    private String namaLengkap;
    private String lokasiNama;
    private Date startDate;
    private Date endDate;

    public CashCashier(){
    }

   public long getCashMasterId(){
        return cashMasterId;
    }

    public void setCashMasterId(long cashMasterId){
        this.cashMasterId=cashMasterId;
    }

    public Date getCashDate(){
        return getOpenDate();
    }
    public void setCashDate(Date openDate){
        this.setOpenDate(openDate);
    }


    public long getAppUserId(){
        return appUserId;
    }

    public void setAppUserId(long appUserId){
        this.appUserId=appUserId;
    }


    public long getSpvOid(){
        return svpOid;
    }
    public void setSpvOid(long svpOid){
        this.svpOid=svpOid;
    }

    public String getSpvName(){
        return svpName;}
    public void setSpvName(String svpName){
        this.svpName=svpName;}

      public long getSpvCloseOid(){
        return svpCloseOid;
    }

    public void setSpvCloseOid(long svpCloseOid){
        this.svpCloseOid=svpCloseOid;
    }

    public String getSpvCloseName(){
        return svpCloseName;}
    public void setSpvCloseName(String svpCloseName){
        this.svpCloseName=svpCloseName;}

    /**
     * @return the shiftId
     */
    public long getShiftId() {
        return shiftId;
    }

    /**
     * @param shiftId the shiftId to set
     */
    public void setShiftId(long shiftId) {
        this.shiftId = shiftId;
    }

    /**
     * @return the openDate
     */
    public java.util.Date getOpenDate() {
        return openDate;
    }

    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate(java.util.Date openDate) {
        this.openDate = openDate;
    }

    // untuk cash balance

    /**
     * @return the balanceValue
     */
    public double getBalanceValue() {
        return balanceValue;
    }

    /**
     * @param balanceValue the balanceValue to set
     */
    public void setBalanceValue(double balanceValue) {
        this.balanceValue = balanceValue;
    }

    /**
     * @return the currancyId
     */
    public long getCurrencyId() {
        return currencyId;
    }

    /**
     * @param currancyId the currancyId to set
     */
    public void setCurrencyId(long currancyId) {
        this.currencyId = currancyId;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the closeDate
     */
    public Date getCloseDate() {
        return closeDate;
    }

    /**
     * @param closeDate the closeDate to set
     */
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * @return the rupiah
     */
    public double getRupiah() {
        return rupiah;
    }

    /**
     * @param rupiah the rupiah to set
     */
    public void setRupiah(double rupiah) {
        this.rupiah = rupiah;
    }

    /**
     * @return the usd
     */
    public double getUsd() {
        return usd;
    }

    /**
     * @param usd the usd to set
     */
    public void setUsd(double usd) {
        this.usd = usd;
    }

    /**
     * @return the amount1
     */
    public double getAmount1() {
        return amount1;
    }

    /**
     * @param amount1 the amount1 to set
     */
    public void setAmount1(double amount1) {
        this.amount1 = amount1;
    }

    /**
     * @return the amount2
     */
    public double getAmount2() {
        return amount2;
    }

    /**
     * @param amount2 the amount2 to set
     */
    public void setAmount2(double amount2) {
        this.amount2 = amount2;
    }

    /**
     * @return the subTotal1
     */
    public double getSubTotal1() {
        return subTotal1;
    }

    /**
     * @param subTotal1 the subTotal1 to set
     */
    public void setSubTotal1(double subTotal1) {
        this.subTotal1 = subTotal1;
    }

    /**
     * @return the subTotal2
     */
    public double getSubTotal2() {
        return subTotal2;
    }

    /**
     * @param subTotal2 the subTotal2 to set
     */
    public void setSubTotal2(double subTotal2) {
        this.subTotal2 = subTotal2;
    }

    /**
     * @return the nama_lengkap
     */
    public String getNamaLengkap() {
        return namaLengkap;
    }

    /**
     * @param nama_lengkap the nama_lengkap to set
     */
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
}

    /**
     * @return the lokasi_nama
     */
    public String getLokasiNama() {
        return lokasiNama;
    }
 
    /**
     * @param lokasiNama the lokasi_nama to set
     */
    public void setLokasiNama(String lokasiNama) {
        this.lokasiNama = lokasiNama;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
}

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}








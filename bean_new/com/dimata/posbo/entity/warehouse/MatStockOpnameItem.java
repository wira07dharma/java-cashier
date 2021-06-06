package com.dimata.posbo.entity.warehouse;

/* package java */
import com.dimata.common.entity.logger.I_LogHistory;
import com.dimata.posbo.entity.masterdata.Material;
import com.dimata.posbo.entity.masterdata.PstMaterial;
import com.dimata.posbo.entity.masterdata.PstUnit;
import com.dimata.posbo.entity.masterdata.Unit;

/* package qdep */
import com.dimata.qdep.entity.*;

public class MatStockOpnameItem extends Entity implements I_LogHistory{
    
    private long stockOpnameId;
    private long materialId;
    private long unitId;
    private double qtyOpname = 0;
    private double qtySold = 0;
    private double qtySystem = 0;
    private double cost = 0.00;
    private double price = 0.00;

    //counter
    private int stockOpnameCounter = 0; 
    
    public long getStockOpnameId(){
        return stockOpnameId;
    }
    
    public void setStockOpnameId(long matStockOpnameId){
        this.stockOpnameId = matStockOpnameId;
    }
    
    public long getMaterialId(){
        return materialId;
    }
    
    public void setMaterialId(long materialId){
        this.materialId = materialId;
    }
    
    public long getUnitId(){
        return unitId;
    }
    
    public void setUnitId(long unitId){
        this.unitId = unitId;
    }
    
    public double getQtyOpname() {
        return qtyOpname;
    }
    
    public void setQtyOpname(double qtyOpname) {
        this.qtyOpname = qtyOpname;
    }
    
    public double getQtySold() {
        return qtySold;
    }
    
    public void setQtySold(double qtySold) {
        this.qtySold = qtySold;
    }
    
    public double getQtySystem() {
        return qtySystem;
    }
    
    public void setQtySystem(double qtySystem) {
        this.qtySystem = qtySystem;
    }
    
    public double getCost() {
        return cost;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stockOpnameCounter
     */
    public int getStockOpnameCounter() {
        return stockOpnameCounter;
    }

    /**
     * @param stockOpnameCounter the stockOpnameCounter to set
     */
    public void setStockOpnameCounter(int stockOpnameCounter) {
        this.stockOpnameCounter = stockOpnameCounter;
    }
    
    //dyas 20131128
    public String getLogDetail(Entity prevDocItem) {
        MatStockOpnameItem prevMatStockOpnameItem = (MatStockOpnameItem)prevDocItem;
        Material material = null;
        Unit unit = null;
        try
        {
            if(this!=null && getMaterialId()!=0 )
            {
                material = PstMaterial.fetchExc(getMaterialId());
}
            if(this!=null && getUnitId()!=0 )
            {
                unit = PstUnit.fetchExc(getUnitId());
            }
        }
        catch(Exception e)
        {
        }
        return  "SKU : " + material.getSku()+

                (prevMatStockOpnameItem == null ||  prevMatStockOpnameItem.getOID()==0 || prevMatStockOpnameItem.getMaterialId() == 0 ||  prevMatStockOpnameItem.getMaterialId() != this.getMaterialId() ?
                (" Nama Barang : " + material.getName()) : "")+

                (prevMatStockOpnameItem == null ||  prevMatStockOpnameItem.getOID()==0 || prevMatStockOpnameItem.getUnitId()== 0 ||  prevMatStockOpnameItem.getUnitId() != this.getUnitId() ?
                (" Unit : " + unit.getName()) : "")+

                (prevMatStockOpnameItem == null ||  prevMatStockOpnameItem.getOID()==0 || prevMatStockOpnameItem.getQtyOpname()!= this.getQtyOpname() ?
                ( " Qty Opname : " + this.getQtyOpname() ) : "" ) +

                (prevMatStockOpnameItem == null ||  prevMatStockOpnameItem.getOID()==0 || prevMatStockOpnameItem.getQtySold() != this.getQtySold() ?
                ( " Qty Sold : " + this.getQtySold() ) : "" )+

                (prevMatStockOpnameItem == null ||  prevMatStockOpnameItem.getOID()==0 || prevMatStockOpnameItem.getCost()!= this.getCost() ?
                ( " Cost : " + this.getCost() ) : "" )+

                (prevMatStockOpnameItem == null ||  prevMatStockOpnameItem.getOID()==0 || prevMatStockOpnameItem.getPrice()!= this.getPrice() ?
                ( " Price : " + this.getPrice() ) : "" );
}

}

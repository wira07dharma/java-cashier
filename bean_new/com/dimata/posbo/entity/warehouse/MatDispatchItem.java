package com.dimata.posbo.entity.warehouse;

/* package java */

import com.dimata.posbo.entity.masterdata.*;
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class MatDispatchItem extends Entity {

    private long dispatchMaterialId = 0;
    private long materialId = 0;
    private long unitId = 0;
    private double qty = 0;
    private double residueQty;
    private double hpp = 0.0;
    private double hppTotal = 0.0;
    private Material materialSource= new Material();
    private Unit unitSource= new Unit();
    
    public double getHppTotal() {
        return hppTotal;
    }

    public void setHppTotal(double hppTotal) {
        this.hppTotal = hppTotal;
    }

    public double getHpp() {
        return hpp;
    }

    public void setHpp(double hpp) {
        this.hpp = hpp;
    }

    public long getDispatchMaterialId() {
        return dispatchMaterialId;
    }

    public void setDispatchMaterialId(long dispatchMaterialId) {
        this.dispatchMaterialId = dispatchMaterialId;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    /** Getter for property residueQty.
     * @return Value of property residueQty.
     *
     */
    public double getResidueQty() {
        return this.residueQty;
    }

    /** Setter for property residueQty.
     * @param residueQty New value of property residueQty.
     *
     */
    public void setResidueQty(double residueQty) {
        this.residueQty = residueQty;
    }

    /**
     * @return the materialSource
     */
    public Material getMaterialSource() {
        return materialSource;
    }

    /**
     * @param materialSource the materialSource to set
     */
    public void setMaterialSource(Material materialSource) {
        this.materialSource = materialSource;
    }

    /**
     * @return the unitSource
     */
    public Unit getUnitSource() {
        return unitSource;
    }

    /**
     * @param unitSource the unitSource to set
     */
    public void setUnitSource(Unit unitSource) {
        this.unitSource = unitSource;
    }

    //by dyas
    //tambah methods getLogDetail
    public String getLogDetail(Entity prevDocItem) {
        MatDispatchItem prevMatDispatchItem = (MatDispatchItem)prevDocItem;
        Material material = null;
        Unit unit = null;
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
        try{
            if(prevMatDispatchItem == null ||  prevMatDispatchItem.getOID()==0 || prevMatDispatchItem.getUnitId()== 0 || prevMatDispatchItem.getUnitId() != this.getUnitId()){
                unit = PstUnit.fetchExc(getUnitId());
            }
        }catch(Exception e){

        }
        return  "SKU : " + material.getSku()+

                (prevMatDispatchItem == null ||  prevMatDispatchItem.getOID()==0 || prevMatDispatchItem.getMaterialId() == 0 ||  prevMatDispatchItem.getMaterialId() != this.getMaterialId() ?
                (" Nama Barang : " + material.getName()) : "")+

                (prevMatDispatchItem == null ||  prevMatDispatchItem.getOID()==0 || prevMatDispatchItem.getUnitId()== 0 || prevMatDispatchItem.getUnitId() != this.getUnitId() ?
                ( " Unit : " + unit.getName()) : "")+

                (prevMatDispatchItem == null ||  prevMatDispatchItem.getOID()==0 || prevMatDispatchItem.getQty()!= this.getQty() ?
                ( " Qty : " + this.getQty() ) : "" ) +

                (prevMatDispatchItem == null ||  prevMatDispatchItem.getOID()==0 || prevMatDispatchItem.getHpp()!= this.getHpp() ?
                ( " HPP : " + this.getHpp() ) : "" )+

                (prevMatDispatchItem == null ||  prevMatDispatchItem.getOID()==0 || prevMatDispatchItem.getHppTotal()!= this.getHppTotal() ?
                ( " HPP Total : " + this.getHppTotal() ) : "" )
                ;
}



}

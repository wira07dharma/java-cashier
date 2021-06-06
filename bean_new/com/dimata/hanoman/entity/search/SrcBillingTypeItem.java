/**

 * Created by IntelliJ IDEA.

 * User: EKA

 * Date: Jul 14, 2004

 * Time: 4:22:25 PM

 * To change this template use Options | File Templates.

 */

package com.dimata.hanoman.entity.search;



public class SrcBillingTypeItem {



    private String strGroup = "";

    private String strItemCode = "";

    private String strItemName = "";

    private double dSellingPriceRp;

    private double dItemCostRp;

    private double dSellingPriceUsd;

    private double dItemCostUsd;

    private String strDescription = "";



    public String getGroup(){

        return strGroup;

    }

    public void setGroup(String strGroup){

        this.strGroup = strGroup;

    }



    public String getItemCode(){

        return strItemCode;

    }

    public void setItemCode(String strItemCode){

        this.strItemCode = strItemCode;

    }



    public String getItemNane(){

        return strItemName;

    }

    public void setItemName(String strItemName){

        this.strItemName = strItemName;

    }



    public double getSellingPriceRp(){

        return dSellingPriceRp;

    }

    public void setSellingPriceRp(double dSellingPriceRp){

        this.dSellingPriceRp = dSellingPriceRp;

    }



    public double getSellingPriceUsd(){

        return dSellingPriceUsd;

    }

    public void setSellingPriceUsd(double dSellingPriceUsd){

        this.dSellingPriceUsd = dSellingPriceUsd;

    }



     public double getdItemCostRp(){

        return dItemCostRp;

    }

    public void setdItemCostRp(double dItemCostRp){

        this.dItemCostRp = dItemCostRp;

    }



    public double getdItemCostUsd(){

        return dItemCostUsd;

    }

    public void setdItemCostUsd(double dItemCostUsd){

        this.dItemCostUsd = dItemCostUsd;

    }

    public String getDesc(){

        return strItemName;

    }

    public void setDesc(String strDescription){

        this.strDescription = strDescription;

    }



}


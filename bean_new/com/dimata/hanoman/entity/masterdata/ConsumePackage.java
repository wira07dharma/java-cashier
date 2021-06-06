/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.hanoman.entity.masterdata;

import com.dimata.qdep.entity.Entity;

public class ConsumePackage extends Entity {

    private long consumePackageBillingId = 0;
    private long billDetailId = 0;

    public long getConsumePackageBillingId() {
        return consumePackageBillingId;
    }

    public void setConsumePackageBillingId(long consumePackageBillingId) {
        this.consumePackageBillingId = consumePackageBillingId;
    }

    public long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(long billDetailId) {
        this.billDetailId = billDetailId;
    }

}

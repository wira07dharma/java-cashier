/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.pos.entity.billing;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author IanRizky
 */
public class BillDetailMappingType extends Entity {
	private long billDetailId;
    private long typeId;

	/**
	 * @return the billDetailId
	 */
	public long getBillDetailId() {
		return billDetailId;
	}

	/**
	 * @param billDetailId the billDetailId to set
	 */
	public void setBillDetailId(long billDetailId) {
		this.billDetailId = billDetailId;
	}

	/**
	 * @return the typeId
	 */
	public long getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
}

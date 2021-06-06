/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.posbo.form.masterdata;

/* java package */

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.posbo.entity.masterdata.*;

/**
 *
 * @author Dimata 007
 */
public class FrmRoom extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private Room room;

    public static final String FRM_NAME_ROOM = "FRM_NAME_ROOM";

    public static final int FRM_FIELD_ROOM_ID = 0;
    public static final int FRM_FIELD_LOCATION_ID = 1;
    public static final int FRM_FIELD_CODE = 2;
    public static final int FRM_FIELD_NAME = 3;
    public static final int FRM_FIELD_DESCRIPTION = 4;

    public static String[] fieldNames =
            {
                "FRM_FIELD_ROOM_ID", "FRM_FIELD_LOCATION_ID", "FRM_FIELD_CODE", "FRM_FIELD_NAME",
                "FRM_FIELD_DESCRIPTION"
            };

    public static int[] fieldTypes =
            {
                TYPE_LONG, TYPE_LONG, TYPE_STRING + ENTRY_REQUIRED,
                TYPE_STRING, TYPE_STRING,

            };

    public FrmRoom() {
    }

    public FrmRoom(Room room) {
        this.room = room;
    }

    public FrmRoom(HttpServletRequest request, Room room) {
        super(new FrmRoom(room), request);
        this.room = room;
    }

    public String getFormName() {
        return FRM_NAME_ROOM;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public Room getEntityObject() {
        return room;
    }

    public void requestEntityObject(Room room) {
        try {
            this.requestParam();
            room.setLocationId(getLong(FRM_FIELD_LOCATION_ID));
            room.setCode(getString(FRM_FIELD_CODE));
            room.setName(getString(FRM_FIELD_NAME));
            room.setDescription(getString(FRM_FIELD_DESCRIPTION));

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}

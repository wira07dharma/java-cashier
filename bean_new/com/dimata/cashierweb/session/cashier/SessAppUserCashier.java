/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.session.cashier;

import com.dimata.cashierweb.entity.admin.AppUser;
import com.dimata.cashierweb.entity.admin.PstAppUser;
import com.dimata.posbo.entity.masterdata.PstSales;
import com.dimata.posbo.entity.masterdata.Sales;
import java.util.Vector;

/**
 *
 * @author Ardiadi
 */
public class SessAppUserCashier {
    public static AppUser getByLoginIDAndPassword(String loginID, String password) {
        if((loginID==null) || (loginID.length()<1) || (password==null) || (password.length()<1))
        return null;

        try{

            String whereClause = " "+ PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]+"='"+loginID.trim()+"' AND "
            +PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD] +"='"+password.trim()+"' "
		    + "AND "+PstAppUser.fieldNames[PstAppUser.FLD_USER_GROUP_NEW]+"='0'";

            Vector appUsers = PstAppUser.listFullObj(0,0, whereClause, "");

            if( (appUsers==null) || (appUsers.size()!=1))
                return new AppUser();

            return (AppUser)  appUsers.get(0);

        } catch(Exception e) {
            System.out.println("getByLoginIDAndPassword " +e);
            return null;
        }
    }
    
    public static AppUser getByLoginIDAndPasswordAllowSpv(String loginID, String password) {
        if((loginID==null) || (loginID.length()<1) || (password==null) || (password.length()<1))
        return null;

        try{

            String whereClause = " "+ PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]+"='"+loginID.trim()+"' AND "
            +PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD] +"='"+password.trim()+"' "
		    + "AND ("+PstAppUser.fieldNames[PstAppUser.FLD_USER_GROUP_NEW]+"='0' OR "+PstAppUser.fieldNames[PstAppUser.FLD_USER_GROUP_NEW]+"='1' OR "+PstAppUser.fieldNames[PstAppUser.FLD_USER_GROUP_NEW]+"='4')";

            Vector appUsers = PstAppUser.listFullObj(0,0, whereClause, "");

            if( (appUsers==null) || (appUsers.size()!=1))
                return new AppUser();

            return (AppUser)  appUsers.get(0);

        } catch(Exception e) {
            System.out.println("getByLoginIDAndPassword " +e);
            return null;
        }
    }
    
    public static Sales getByLoginIDAndPasswordSales(String loginID, String password) {
        if((loginID==null) || (loginID.length()<1) || (password==null) || (password.length()<1))
        return null;

        try{

            String whereClause = " "+ PstSales.fieldNames[PstSales.FLD_LOGIN_ID]+"='"+loginID.trim()+"' AND "
            +PstSales.fieldNames[PstSales.FLD_PASSWORD] +"='"+password.trim()+"'";

            Vector appUsers = PstSales.list(0,0, whereClause, "");

            if( (appUsers==null) || (appUsers.size()!=1))
                return new Sales();

            return (Sales)  appUsers.get(0);

        } catch(Exception e) {
            System.out.println("getByLoginIDAndPassword " +e);
            return null;
        }
    }
}

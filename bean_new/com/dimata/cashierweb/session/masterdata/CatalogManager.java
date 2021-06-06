/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.session.masterdata;

/**
 *
 * @author IanRizky
 */
public class CatalogManager {
	public static boolean running = false;
	
	public static long userId = 0;
	
	public static String statusProses ="";
	
	public static String statusData = "";
	
	public static CatalogMonitor lck = new CatalogMonitor();
	
	public CatalogManager() {

    }
    
    public static void startCheck(){    
        
        if(running) return;
            
            CatalogManager objMan = new  CatalogManager();

            Thread thLocker = new Thread(new CatalogMonitor());

            thLocker.setDaemon(false);

            running = true;
            
            thLocker.start();

    }

    public void stopMonitor() {
		
        running = false;
        
    }

    public boolean getStatus() {

        return running;

    }
	
	public static long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId){
         this.userId = userId;
     }
	
	public String getProses() {

        return statusProses;

    }
	
	public String getDataProses() {

        return statusData;

    }
}

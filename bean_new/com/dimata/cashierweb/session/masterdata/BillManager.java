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
public class BillManager {
	public static boolean running = false;
	
	public static String statusProses ="";
	
	public static String statusData = "";
	
	public static long userId = 0;
	
	public static BillMonitor lck = new BillMonitor();
	
	public BillManager() {

    }
    
    public static void startCheck(){    
        
        if(running) return;
            
            BillManager objMan = new  BillManager();

            Thread thLocker = new Thread(new BillMonitor());

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
	
	public long getUserId() {
		return userId;
	}
	
	public String getProses() {

        return statusProses;

    }
	
	public String getDataProses() {

        return statusData;

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.session.masterdata;

import com.dimata.posbo.utility.ServiceStockCheck;

/**
 *
 * @author IanRizky
 */
public class CatalogMonitor implements Runnable {
	public CatalogMonitor() {
            
    }
	
	public void run() {

        System.out.println("start .... ");
    
        while (CatalogManager.running) {
            
            try {
				CatalogManager.statusProses = "";
                ServiceStockCheck stockCheck = new ServiceStockCheck();
                long userId = CatalogManager.getUserId();
				stockCheck.setUserId(userId);
				stockCheck.getCatalogAPI();
				
                Thread.sleep((long) (2 * 60000));//milisecond tiap 4 jam = 240 menit
                
            } catch (Exception e) {
                System.out.println("Interrupted " + e);
            }
            //DTaxIntegrationManagerPaymentPbb.running=false;
        }
        System.out.println("stop .... ");
    }
	
}

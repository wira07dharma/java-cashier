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
public class BillMonitor implements Runnable {
	public BillMonitor() {
            
    }
	
	public void run() {

        System.out.println("start .... ");
    
        while (BillManager.running) {
            
            try {
				BillManager.statusProses = "";
                ServiceStockCheck stockCheck = new ServiceStockCheck();
				stockCheck.sendCashBillMain();
                
                Thread.sleep((long) (60 * 60000));//milisecond tiap 4 jam = 240 menit
                
            } catch (Exception e) {
                System.out.println("Interrupted " + e);
            }
            //DTaxIntegrationManagerPaymentPbb.running=false;
        }
        System.out.println("stop .... ");
    }
	
}

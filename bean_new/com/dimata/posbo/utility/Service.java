/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.posbo.utility;

/**
 *
 * @author dimata005
 */
public class Service {
    public static final int SERVICE_OFF = 0;
    public static final int SERVICE_ON = 1;
    public static String statusTitle[] = {"OFF", "ON"};
    public static int status = 0;
    public static long interval = 300000;
    public static int error = 0;
    public static String message = "";
}

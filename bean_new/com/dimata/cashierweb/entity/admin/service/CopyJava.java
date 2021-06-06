/* Generated by Together */

package com.dimata.cashierweb.entity.admin.service;

import java.io.*;
import java.util.*;

public class CopyJava {
    
    public void copyDirFiles(String sourceDir, String targetDir){
        String errMsg = "";

        File myDir = new File(parsePath(sourceDir));
        File[] myList = myDir.listFiles();
        File checkDir = new File(parsePath(targetDir));
        File checkDirSource = new File(parsePath(sourceDir)); 

        if (checkDirSource.exists()) {
            System.out.println("target Directory OK");
        } else {
            errMsg="<font color=\"yellow\">Unable to locate source directory..., please check source path directory !!!</font>";
            //err.add((String)errMsg);
            this.listErr.add(errMsg);
            System.out.println(errMsg);
            return;
        }
        
        if (checkDir.exists()) {
            System.out.println("target Directory OK");
        } else {
            System.out.println("Creating target directory...");
            if (checkDir.mkdirs())
                System.out.println("Create success...");
            else
                System.out.println("Create fail...");
        }
        
        for (int i = 0; i < myList.length; i++)
            {
            File tmp = (File)myList[i];
            //System.out.println(tmp.length());
            if (tmp.isFile())
                {
                System.out.println("copying " + tmp.getName() + "...");
                File x_file = new File(targetDir + systemPathDelimeter + tmp.getName());
                FileOutputStream fOut = null;
                FileInputStream fIn = null;
                try
                    {
                    fOut = new FileOutputStream(x_file);
                    fIn = new FileInputStream(tmp);
                    int maxbuf = 1024;
                    int rbyte = maxbuf;
                    for (int ib = 0; (ib < Integer.MAX_VALUE) && (rbyte == maxbuf); ib = ib + maxbuf)
                        {
                        byte buffer[] = new byte[maxbuf];
                        rbyte = fIn.read(buffer); //, 0, maxbuf);
                        if (rbyte > 0)
                            fOut.write(buffer); //, ib, rbyte);
                        }
                    }
                catch (IOException e)
                    {
                    System.out.println(e);
                    errMsg = "<font color=\"yellow\">Cannot copy " + tmp.getName() + ". " + e.getMessage()+"</font>";
                    System.out.println(errMsg);
                    this.listErr.add(errMsg);
                    return;
                    }
                catch (Exception e)
                    {
                    System.out.println(e);
                    errMsg = "<font color=\"yellow\">Cannot copy " + tmp.getName() + ". " + e.getMessage()+"</font>";
                    System.out.println(errMsg);
                    this.listErr.add(errMsg);
                    return;
                    }
                finally
                    {
                    try
                        {
                        fOut.close();
                        fIn.close();
                        }
                    catch (Exception e)
                        {
                        System.out.println(e);
                        }
                    }
                }
            }
        }

    public static String parsePath(String path){
        if(path!=null && path.length()>0){
           StringTokenizer st=new StringTokenizer(path,"/");
           String newPath="";
           while(st.hasMoreTokens())
               newPath=newPath+st.nextToken()+systemPathDelimeter;
           newPath=newPath.substring(0,newPath.length()-systemPathDelimeter.length());
           System.out.println("new Path ="+newPath);
           return newPath;
        }else{
            return "";
        }

    }

   public static void main(String[] a)
        {
        String str="d://test/abc";
        System.out.println(parsePath(str));
        }



    public Vector getListErr()
        {
        return listErr;
        }

    public void setListErr(Vector listErr)
        {
        this.listErr = listErr;
        }

    public static String systemPathDelimeter=System.getProperty("file.separator");
    public Vector listErr=new Vector();
    }

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.ajax.cashier;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Ardiadi
 */
public class SignatureImage {
    public static BufferedImage decodeToImage(String imageString){
	BufferedImage image = null;
	byte[] imageByte;
	
	try{
	    BASE64Decoder decoder = new BASE64Decoder();
	    imageByte = decoder.decodeBuffer(imageString);
	    ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	    image = ImageIO.read(bis);
	    bis.close();
	}catch(Exception ex){
	    ex.printStackTrace();
	}
	
	return image;
    }
    
    public SignatureImage(String imageString, String path, String imageFormat, 
	    String imageName) throws IOException{
	BufferedImage newImage;
	newImage = decodeToImage(imageString);
	ImageIO.write(newImage, imageFormat, new File(path+"/"+imageName+"."+imageFormat));
    }
}

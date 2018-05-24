package com.serverlet;

//import it.sauronsoftware.base64.Base64;

import it.sauronsoftware.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class ZIPgood {
//  ѹ���ַ���  
    public static String compressData(String data) {  
        try {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            DeflaterOutputStream zos = new DeflaterOutputStream(bos);  
            zos.write(data.getBytes());  
            zos.close();  
            return new String(getenBASE64inCodec(bos.toByteArray()));  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return "ZIP_ERR";  
        }  
    }  
      
//  ʹ��apche codec���������encode  
    public static String getenBASE64inCodec(byte [] b) {  
        if (b == null)  
            return null;  
        return new String((new Base64()).encode(b));  
    }  
      
      
      
      
//  base64ת��Ϊstring  
      
    public static byte[] getdeBASE64inCodec(String s) {  
        if (s == null)  
            return null;  
        return new Base64().decode(s.getBytes());  
    }  
      
//  �����ַ���  
    public String decompressData(String encdata) {  
        try {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            InflaterOutputStream zos = new InflaterOutputStream(bos);  
            zos.write(getdeBASE64inCodec(encdata));   
//          byte [] b = encdata.getBytes();  
//          int len = b.length;  
//          zos.write(b, 0, len);  
//          zos.write(getdeBASE64(encdata.getBytes()),0,(encdata.getBytes()).length);  
            zos.close();  
            return new String(bos.toByteArray());  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return "UNZIP_ERR";  
        }  
    }  
//    public static void main(String[] args) {  
//    	ZIPgood zwz = new ZIPgood();  
//        String compString = zwz.compressData("111");
//        		System.out.println(compString);  
//          
//        String decompString = zwz.decompressData(compString);  
//        System.out.println(decompString);  
//          
//    }  
}

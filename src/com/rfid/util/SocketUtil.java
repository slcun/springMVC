package com.rfid.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.rfid.api.RFIDApi; 

public class SocketUtil {
	
	private static final Logger log = Logger.getLogger(SocketUtil.class);
	
    /**
     * 将字节数组转化为string
     * @param bytes 要转换的byte数组
     * @param start 起始索引
     * @param end 结束索引
     * @return String
     */
    public String bytesToString(byte [] bytes,int start,int end){
        String str = "";
        if(bytes.length<end-start){
            return str;
        }
        byte [] bs = new byte[end-start];
        for(int i = 0;i<end-start;i++){
            bs[i] = bytes[start++];
        }
        str = new String(bs);
        return str;
    }
    
    /**  
     * 转换 char 为 byte  
     * @param c char  
     * @return byte  
     */  
     private static byte charToByte(char c) {   
        return (byte) "0123456789ABCDEF".indexOf(c);   
    }  
    
    /**
     * 16进制的String转换为byte数组
     * @param hexString
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {   
        if (hexString == null || hexString.equals("")) {   
            return null;   
        }   
        hexString = hexString.toUpperCase();   
        int length = hexString.length() / 2;   
        char[] hexChars = hexString.toCharArray();   
        byte[] d = new byte[length];   
        for (int i = 0; i < length; i++) {   
            int pos = i * 2;   
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
        }   
        return d;   
    } 
    
    
    /**
     * byte数组转为16进制String
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src){   
        StringBuilder stringBuilder = new StringBuilder("");   
        if (src == null || src.length <= 0) {   
            return null;   
        }   
        for (int i = 0; i < src.length; i++) {   
            int v = src[i] & 0xFF;   
            String hv = Integer.toHexString(v);   
            if (hv.length() < 2) {   
                stringBuilder.append(0);   
            }   
            stringBuilder.append(hv);   
        }   
        return stringBuilder.toString();   
    } 
    
    public static byte[] streamToBytes(InputStream inputStream,int len){
        /**
         * inputStream.read(要复制到的字节数组,起始位置下标,要复制的长度)
         * 该方法读取后input的下标会自动的后移，下次读取的时候还是从上次读取后移动到的下标开始读取
         * 所以每次读取后就不需要在制定起始的下标了
         */
        byte [] bytes= new byte[len];
        try {
            inputStream.read(bytes, 0, len);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 分割字符串
     * @param str
     * @return String[]
     */
    public static String[] cutStr(String str) {
    	String[] arr;
    	if(str.length()%2 != 0) {
    		log.error("待分割的字符串长度不是偶数位");
    		return null;
    	} else {
    		arr = new String[str.length()/2]; 
    		int i = 0;
    		for(int j =0;j<arr.length;j++) {
    			arr[j] = str.substring(i, i+2);
    			i+=2;
    		}
    		return arr;
    	}
    }
    
    public static HashSet<String> getString(String str, String regx) { 
	    //1.将正在表达式封装成对象Patten 类来实现 
	    Pattern pattern = Pattern.compile(regx); 
	    //2.将字符串和正则表达式相关联 
	    Matcher matcher = pattern.matcher(str);
	    //3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。 
	    
	    HashSet<String>  set= new HashSet<String>();
	    //查找符合规则的子串 
	    while(matcher.find()){ 
//	    	set.add(matcher.group().substring(10, matcher.group().length()-12));
	    	set.add(matcher.group());
	    } 
	    return set;
	  }
    
    
}

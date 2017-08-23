package com.rfid.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rfid.util.SocketUtil;

public class RfidUtil {
    public static final String IP_ADDR = "127.0.0.1";//服务器地址   
    public static final int PORT = 9001;//服务器端口号    
    public static void main(String[] args) {
    	try {
    		startInventoryMultiple();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    /**
     * 该函数用于开始循环读标签
     * @throws IOException 
     */
    public static void startInventoryMultiple() throws IOException {
        
    	Socket socketClient=null;
    	socketClient = new Socket(IP_ADDR,PORT);
    	OutputStream output=null;
        InputStream input =null;
        // 同步字符串(3byte)
        byte[] sync = null; //
        output = socketClient.getOutputStream();
        //循环查询
        output.write(SocketUtil.hexStringToBytes("BB17020000190D0A"));
        input = socketClient.getInputStream();
        
//        sync = SocketUtil.streamToBytes(input,1000);
        

        
        
        byte[] buffer = new byte[1024];  
//        int len = -1;  
//        HashSet<String> hashSet = new HashSet<>();
        
        byte[] b = new byte[1024];
		
		int len;
		while((len=input.read(b)) != -1) {
//			log.info("读取");
			System.out.println(SocketUtil.bytesToHexString(b));
//			Thread.sleep(2000);
		}
        
//        while ((len = input.read(buffer)) != -1) {  
//            byte [] bytes= new byte[len];
//
//            input.read(bytes, 0, len);
//   
//        	
//    		String string = SocketUtil.bytesToHexString(bytes); 
//    		String pattern = "bb97(.*?)0d0a";
//    		
//    		hashSet.addAll(getString(string, pattern));
//
//        	System.out.println(hashSet);
//        	System.out.println("\n");
//        } 
        
        
        socketClient.close();
          
    
    }
    /**
     * //停止循环查询
     * @throws IOException
     */
    public static void  stopInventoryMultiple() throws IOException {
    	 
    	Socket socketClient=null;
    	socketClient = new Socket(IP_ADDR,PORT);
    	OutputStream output=null;
        InputStream input =null;
        // 同步字符串(3byte)
        byte[] sync = null; //
        output = socketClient.getOutputStream();
        
        output.write(SocketUtil.hexStringToBytes("BB1800180D0A"));
        input = socketClient.getInputStream();
        sync = SocketUtil.streamToBytes(input,7);
        
        
        System.out.print(SocketUtil.bytesToHexString(sync));
        socketClient.close();
          
    
    }
	/** 
	   * 获取查询的字符串 
	   * 将匹配的字符串取出 
	   */
	  public static HashSet<String> getString(String str, String regx) { 
	    //1.将正在表达式封装成对象Patten 类来实现 
	    Pattern pattern = Pattern.compile(regx); 
	    //2.将字符串和正则表达式相关联 
	    Matcher matcher = pattern.matcher(str);
	    //3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。 
	    
	    HashSet<String>  set=new HashSet<String>();
	    //查找符合规则的子串 
	    while(matcher.find()){ 
	    	set.add(matcher.group().substring(10, matcher.group().length()-12));
	    } 
	    return set;
	  }
}

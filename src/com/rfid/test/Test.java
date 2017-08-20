package com.rfid.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.rfid.api.RFIDApi;

public class Test {
	public static void main(String[] args) throws IOException {
//		Integer CRC = 0x00 + 0x03 + 0x17 +0x1E ;
//		System.out.println(CRC);
//		String binaryString = Integer.toBinaryString(CRC);
//		System.out.println(binaryString.length());
////		String string = binaryString.substring(binaryString.length()-8);
////		int int1 = Integer.parseInt(string);
//		System.out.println(binaryString);
//		System.out.println(CRC >>> 2);
//		int ii = 0b11111111;
//		System.out.println(ii);
//		System.out.println(Integer.toHexString(ii));
//		String s1 = "0D";
//		String s2 = "0A";
//		System.out.println(s1 + null);
//		Integer i = Integer.parseInt(s1,16)+Integer.parseInt(s2,16);
//		System.out.println(i);
//		StringBuilder sb = new StringBuilder("ABC");
//		sb.insert(0, "123");
//		System.out.println(sb);
//		System.out.println(Integer.toHexString(500));
		
//		SocketService socket = new SocketService();
//		
//		Socket client = socket.getClient();
		
		
//		String str = "BB08010A130D0A1122334455667788";
//		String[] arr = new String[str.length()/2]; 
//			int i = 0;
//			for(int j =0;j<arr.length;j++) {
//				arr[j] = str.substring(i, i+2);
//				i+=2;
//			}
//		System.out.println(Arrays.toString(arr));
//		System.out.println(100%9);

//		while(1==1){
//			String timestamp = String.valueOf(System.currentTimeMillis());
//			
//			System.out.println(System.currentTimeMillis());
//		}

		
		Map<String,String> map = new HashMap();
		
		map.remove("111");
		
		map.put("111", "222");
		map.put("111", "333");
		
		
		System.out.println(map);
		
		
		
		
	}
	
	
	
	@org.junit.Test
	public void testName() throws Exception {
		
	}
	
	
}

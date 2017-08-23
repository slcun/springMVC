package com.rfid.api;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.rfid.constant.Response;
import com.rfid.constant.Type;
import com.rfid.socket.ScoketClient;
import com.rfid.util.SocketUtil;

public class RFIDApi {

	private static final Logger log = Logger.getLogger(RFIDApi.class);
	
	/**
	 * 帧起始标志,共1个字节,为固定值 0xBB
	 */
	public static final String HEAD = "BB";
	/**
	 * 数据包的结束， 共 2 个字节， 为固定值 0x0d、 0x0a
	 */
	public static final String END1 = "0D";
	/**
	 * 数据包的结束， 共 2 个字节， 为固定值 0x0d、 0x0a
	 */
	public static final String END2 = "0A";
	
	ScoketClient scoketClient = new ScoketClient();
	public ScoketClient getScoketClient() {
		return scoketClient;
	}

	private int len;
	
	/**
	 * 生成指令文本
	 * @param type 指令类型
	 * @param data 发送的数据,个数可变,为null时表示不发送数据
	 * @return 指令文本
	 */
	public String getCommand(String type , String... data) {
		/*
		 * 协议帧传输格式 HEAD + type + len + data + CRC + END1 + END2, data可有可无,不定个数
		 * type 为指令类型,  HEAD,END1,END2为常量,固定不变
		 * len 为 data 的个数, CRC 由 type,len,data计算而来
		 */
		//指令的后半部
		String commandSuf = calculateCRC(type,data) + END1 + END2;
		String lenStr = "00";
		if(null != data){
			lenStr = Integer.toHexString(len);
			if(lenStr.length() == 1){
				lenStr = "0"+lenStr;
			}
			StringBuilder temp = new StringBuilder();
			for(String s : data){
				temp.append(s);
			}
			temp.append(commandSuf);
			commandSuf = temp.toString();
		}
		//指令的前半部
		String commandPre = HEAD + type + lenStr;
		return commandPre+commandSuf;
	}
	
	/**
	 * 计算CRC = type + len + data...
	 * @param type
	 * @param data
	 * @return
	 */
	public String calculateCRC(String type ,String... data ) {
		/*
		 * CRC校验除去帧起始和帧结束标志， 每帧数据所有字节之和， 取低字节。 如：
		 * 0xBB 0x10 0x05 0x11 0x22 0x33 44 55 CRC 0x0d 0x0a
		 * CRC = 0x10 + 0x05 + 0x11 + 0x22 + 0x33 + 0x44 + 0x55 = 0x14
		 */
		int i = 0;
		int j = 0;
		int cutStrLen = 0;
		String[] cutStr;
		int temp1 = 0;
		int temp2 = 0;
		for(String s : data) {
			i++;
			if(s.length()>2) {
				j++;
				cutStr = SocketUtil.cutStr(s);
				cutStrLen += cutStr.length;
				for(String s1 : cutStr){
					temp1 += Integer.parseInt(s1,16);
				}
			}else {
				temp2 += Integer.parseInt(s,16);
			}
		}
		len = i - j + cutStrLen;
		
		int temp = Integer.parseInt(type,16) + len + temp1 + temp2;
//		for(String s : data){
//			temp += Integer.parseInt(s,16);
//		}
		String crc = Integer.toHexString(temp);
		if(crc.length() == 1){
			crc = "0" + crc;
		} else if(crc.length() > 2) {
			crc = crc.substring(crc.length()-2);
		}
		return crc;
	}
	
	/**
	 * 设置天线
	 * @param data0	选择的天线
	 * @return String 设置天线的响应结果
	 */
	public Boolean setAnt(String data0){
		//需要发送的指令
		String command = getCommand(Type.SET_ANT, data0);
		log.info("指令 : [ "+command+" ]");
		String pattern = ".*"+Response.SET_TX_POWER_SUCCESS+".*";
		try {
			return Pattern.matches(pattern, scoketClient.sendCommand(command).toUpperCase());
		} catch (IOException e) {
			return null;
		}
	}
	
	
	
	/**
	 * 单次查询标签 EPC
	 * @return
	 * @throws IOException 
	 */
	public String queryEPC() throws IOException {
		String response = scoketClient.sendCommand("BB1600160D0A");
		return null;
	}
	
	public void lopQueryEPC(String begin, String end) throws IOException {
		
	}
	
	@Test
	public void testName() throws Exception {
		
//		String crc2 = calculateCRC("1A","1122334400000001000200040011223344556677");
//		
//		System.out.println(crc2);
//		
		String command = getCommand("1c","4433221100000C112233445566778899001122");
		System.out.println(command);
		System.out.println(command.toUpperCase().equals("BB1C134433221100000C112233445566778899001122150D0A"));
	}
	
	
	
}

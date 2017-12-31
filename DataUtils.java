package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    static String Path = "src/com/company";
    static String tcpTxtPath = "/pcapFiles/pcapTcpFiles.txt";
    static String udpTxtPath = "/pcapFiles/pcapUdpFiles.txt";
    static String pcapTcpDir = "/pcapFiles/pcapTcpFiles/";
    static String pcapUdpDir = "/pcapFiles/pcapUdpFiles/";





    static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    static int byteArrayToIntReverse(byte[] small_packet){
        int packetBodyLen = 0;
        for(int i=3; i>=0; i--){
            packetBodyLen = packetBodyLen*256 + (int)(small_packet[i]&0xff);
        }
        return packetBodyLen;
    }
    static int byteArrayToInt4(byte[] small_packet){
        int packetBodyLen = 0;
        for(int i=0; i<4; i++){
            packetBodyLen = packetBodyLen*256 + (int)(((int)small_packet[i])&0xff);
            //System.out.println((int)(((int)small_packet[i])&0xff));
        }
        return packetBodyLen;
    }

    static int byteArrayToInt2(byte[] var1) {

        int var3 = (var1[0] & 255) * 256 + (var1[1] & 255);
        return var3;
    }

    static byte[] cutBytes(byte[] packetBody, int start, int end){
        byte[] packet = new byte[end-start];
        for (int i = 0; i<(end - start); i++){
            packet[i] = packetBody[i+start];
        }
        return packet;
    }

    static List<String> ReadTxt(String txtpath) {
        List<String> pcapPath = new ArrayList<String>();
        try{
            File file = new File(txtpath);
            if(file.isFile()&&file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTXT = null;
                while ((lineTXT=bufferedReader.readLine())!=null){
                    pcapPath.add(lineTXT);
                }
                read.close();
            }else {
                System.out.println("找不到指定的文件！");
            }
        }catch (Exception e){
            System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }

        return pcapPath;
    }

    public static String formatFloatNumber(double value) {
        if(value != 0.00){
            java.text.DecimalFormat df = new java.text.DecimalFormat("########0.000000");
            return df.format(value);
        }else{
            return "0.00";
        }

    }
}

package com.company;
import java.util.*;
import java.io.*;


public class GetTcpUdpSession {

    static int flag = 0;//flag=0表示tcpflag=1表示udp
    //static String path = "./src/com/company/pcapFiles/";

    public static void main(String[] args) {
        //DataUtils dataUtils = new DataUtils();
        String pcapDir;
        //GetTcpUdpSession instance = new GetTcpUdpSession();
        GetTcpUdpSession getTcpUdpSession = new GetTcpUdpSession();
        getTcpUdpSession.go("/Users/sabrina/IdeaProjects/shirewark/src/com/company/pcapFiles/pcapTcpFiles/TCP[192.168.1.40][1593][222.186.189.245][80].pcap");



    }

    public  List<ShireWarkListData> go(String filePath) {
        String tcpDataDir = "pcapFiles"+File.separator+"TcpData" + File.separator;
        String udpDataDir = "pcapFiles"+File.separator+"UdpData" + File.separator;
        String httpDataDir = "pcapFiles"+File.separator+"HttpData"+File.separator;
        PcapFile1 pcapFile = new PcapFile1();
        List<ShireWarkListData> shireWarkListDataList = new ArrayList<ShireWarkListData>();
        if (filePath.contains("TCP")){
            flag = 0;
            pcapFile.getTcpUdpSession(filePath, tcpDataDir, httpDataDir, flag);
        }
        else {
            flag =1;
            pcapFile.getTcpUdpSession(filePath, udpDataDir,httpDataDir, flag);
        }

        return shireWarkListDataList;
    }




}



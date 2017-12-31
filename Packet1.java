package com.company;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
public class Packet1 {
    private IpPacket ipPacket;
    private PacketHeader  packetHeader;
    private byte[] packetBody;
    private int packetBodyLen;
    private int ipHeaderLength;
    private int ipTotalLength;
    private int transHeaderLength;


//    public void setIpHeaderLength(int ipHeaderLength){
//        this.ipHeaderLength = ipHeaderLength;
//    }
//
//    public void setIpTotalLength(int ipTotalLength){
//        this.ipTotalLength = ipTotalLength;
//    }
//
//    public void setTransHeaderLength(int transHeaderLength){
//        this.transHeaderLength = transHeaderLength;
//    }

    public void setPacketBody(byte[] packetBody){
        this.packetBody = packetBody;
    }

//    public int getIpHeaderLength(){
//        return this.ipHeaderLength;
//    }
//
//    public int getIpTotalLength(){
//        return this.ipTotalLength;
//    }
//
//    public int getTransHeaderLength(){
//        return this.transHeaderLength;
//    }

    public PacketHeader getPacketHeader() {
        return packetHeader;
    }

    public void setPacketHeader(PacketHeader packetHeader) {
        this.packetHeader = packetHeader;
    }

    public int getPacketBodyLen() {
        return packetBodyLen;
    }

    public void setPacketBodyLen(int packetBodyLen) {
        this.packetBodyLen = packetBodyLen;
    }

    public byte[] getPacketBody() {
        return packetBody;
    }

    public void setIpPacket(IpPacket ipPacket) {
        this.ipPacket = ipPacket;
    }

    public IpPacket getIpPacket() {
        return ipPacket;
    }

    public void parserPacket(byte[] packetHeaderByte, List<Packet1> pcapPackets, List<ShireWarkListData> shireWarkListDatas, RandomAccessFile raf, int flag){
        PacketHeader packetheader = new PacketHeader();
        packetheader.parseDataHeader(packetHeaderByte);
        setPacketHeader(packetheader);	//set 包头
        setPacketBodyLen(packetHeader.getCapLen());
        byte[] packetBody = new byte[packetBodyLen];
        try{
            raf.read(packetBody);	//提取数据包数据长度
            setPacketBody(packetBody);//设置包体
        }catch(IOException e){
            e.printStackTrace();
        }
        //System.out.println("========packetBodyLen"+packetBodyLen);


        //int netLayerType = byteArrayToInt(cutBytes(packetBody,12,14));	//链路层上一层（网络层）协议类型

        //System.out.println("netLayerType:"+netLayerType);

        IpPacket ippacket = new IpPacket();
        ShireWarkListData shireWarkListData = new ShireWarkListData();

        ippacket.parserIpTransLayer(packetBody, flag);
        setIpPacket(ippacket);
        //this.ipPacket = ipPacket;
        byte[] buffer_4;
        buffer_4 = DataUtils.cutBytes(packetBody,26, 30);
        ippacket.setSrcIp(ipPacket.getIpAddr(buffer_4));
        //ipPacket.srcIp = ipPacket.getIpAddr(buffer_4);
        buffer_4 = DataUtils.cutBytes(packetBody, 30, 34);
        ipPacket.setDstIp(ipPacket.getIpAddr(buffer_4));
        //ipPacket.dstIp = ipPacket.getIpAddr(buffer_4);
        shireWarkListData.setAttribute(packetHeader, ipPacket);
        pcapPackets.add(this);
        //System.out.println("======packet's size======" + pcapPackets.size());

        shireWarkListDatas.add(shireWarkListData);
//        if(netLayerType == IP_FLAG){
//            packet.setSecondLayerType(netLayerType);
//            parserIPTransLayer(packet,packetBody);	//主要是这个____读关键信息（获得五元组信息存在packet中）
//            	//此时提取的即是ip协议下的数据包
//        }
    }
}

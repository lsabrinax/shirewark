package com.company;

public class ShireWarkListData {
    private String time;
    private String srcIp;
    private String dstIp;
    private String protocol;
    private String length;
    private String info;
    private double absoluteTime;
    private String packetSeqNum;
    private int minute = 0;



    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getDstIp() {
        return dstIp;
    }
    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }

    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }

    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSrcIp() {
        return srcIp;
    }
    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public double getAbsoluteTime() {
        return absoluteTime;
    }

    public void setAbsoluteTime(double absoluteTime) {
        this.absoluteTime = absoluteTime;
    }

    public String getPacketSeqNum() {
        return packetSeqNum;
    }

    public void setPacketSeqNum(String packetSeqNum) {
        this.packetSeqNum = packetSeqNum;
    }

    public void setAttribute(PacketHeader packet_header, IpPacket ipPacket) {
        String string;
        double time;
        time = packet_header.getTimeS() + packet_header.getTimeMs()/1000000.0;
        setAbsoluteTime(time);
        string = packet_header.getCapLen() +"";
        setLength(string);
        if(ipPacket.getTransLayerType()== PcapFile1.TCP_FLAG){
            setProtocol("TCP");
            setInfo(ipPacket.getSrcPort() + "--->" + ipPacket.getDstPort());
        }else {
            setProtocol("UDP");
            setInfo(ipPacket.getSrcPort() + "--->" + ipPacket.getDstPort());
        }


        setDstIp(ipPacket.getDstIp());
        setSrcIp(ipPacket.getSrcIp());

    }

    public void sortTime(){

    }

}

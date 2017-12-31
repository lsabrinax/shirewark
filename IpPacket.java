package com.company;
import java.lang.*;

import static java.lang.Math.abs;

public class IpPacket {
    private String srcIp;
    private String dstIp;
    private String srcPort;
    private String dstPort;
    private int ipHeaderLen;
    private int ipTotalLen;
    private int transHeaderLen;
    private int transLayerType;
    private int dataLen;

    public int getDataLen() {
        return dataLen;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }

    public String getDstPort() {
        return dstPort;
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    public int getTransLayerType() {
        return transLayerType;
    }

    public void setTransLayerType(int transLayerType) {
        this.transLayerType = transLayerType;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public int getIpHeaderLen() {
        return ipHeaderLen;
    }

    public void setIpHeaderLen(int ipHeaderLen) {
        this.ipHeaderLen = ipHeaderLen;
    }

    public int getIpTotalLen() {
        return ipTotalLen;
    }

    public void setIpTotalLen(int ipTotalLen) {
        this.ipTotalLen = ipTotalLen;
    }

    public int getTransHeaderLen() {
        return transHeaderLen;
    }

    public void setTransHeaderLen(int transHeaderLen) {
        this.transHeaderLen = transHeaderLen;
    }

    public void parserIpTransLayer(byte[] packetBody, int flag) {
        //取出IP头长度
        ipHeaderLen = 4*(packetBody[14]& 0x0f);
        //System.out.println("=ipHeaderLength==="+ipHeaderLen);
        //取出IP包总长度
        ipTotalLen = DataUtils.byteArrayToInt2(DataUtils.cutBytes(packetBody, 16, 18));
        //System.out.println("======ipTotalLength====="+ipTotalLen);
        if(flag==0){
            transHeaderLen = 4*(((int)(packetBody[46] >> 4)) & 0xf);
            //System.out.println("========packet======="+(int)((packetBody[46]>>4) & 0xff));
        }else {
            transHeaderLen = 8;
        }
        this.transLayerType = ((int)(packetBody[23]))&0xff;
        //System.out.println("=======translayertype========"+transLayerType);
        this.setTransHeaderLen(transHeaderLen);
        //System.out.println("=========transHeaderLen========"+transHeaderLen);
        srcPort = DataUtils.byteArrayToInt2(DataUtils.cutBytes(packetBody,34,36))+"";
        dstPort = DataUtils.byteArrayToInt2(DataUtils.cutBytes(packetBody,36,38))+"";

    }

    public String getIpAddr(byte[] ipaddr) {
        String ipStr = "";
        for(int i=0; i<3; i++){
            ipStr += (int)(ipaddr[i]&0xff);
            ipStr += ".";
        }
        ipStr += (int)(ipaddr[3]&0xff);

        return	ipStr;
    }
}

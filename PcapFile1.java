package com.company;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PcapFile1 {
    static int IP_FLAG = 2048;
    static int TCP_FLAG = 6;
    static int UDP_FLAG = 17;
    private byte[] pcapheader = new byte[24];
    private PcapFileHeader pcapFileHeader;
    private List<Packet1> packets = null;
    private List<ShireWarkListData> shireWarkListDatas = null;

    public PcapFileHeader getPcapFileHeader() {
        return pcapFileHeader;
    }
    public void setPcapFileHeader(PcapFileHeader pcapFileHeader) {
        this.pcapFileHeader = pcapFileHeader;
    }

    public List<Packet1> getPackets() {
        return packets;
    }

    public void setPackets(List<Packet1> packets) {
        this.packets = packets;
    }

    public byte[] getPcapheader() {
        return pcapheader;
    }

    public void setPcapheader(byte[] pcapheader) {
        this.pcapheader = pcapheader;
    }

    public List<ShireWarkListData> getShireWarkListDatas() {
        return shireWarkListDatas;
    }

    public void setShireWarkListDatas(List<ShireWarkListData> shireWarkListDatas) {
        this.shireWarkListDatas = shireWarkListDatas;
    }


    public List<ShireWarkListData> getTcpUdpSession(String pcapfilepath, String writeDataDir, String httpDataDir, int flag) {
        RandomAccessFile raf = null;
        int pathStart;
        int pathEnd;
        httpDataDir = "src/com/company/" + httpDataDir;
        writeDataDir = "src/com/company/" + writeDataDir;
        pathStart = pcapfilepath.lastIndexOf(File.separator);
        pathEnd = pcapfilepath.lastIndexOf(".");
        String writeDataPath = pcapfilepath.substring(pathStart, pathEnd)+".txt";
        long fileLength;//pacap文件长度
        List<Packet1> packets = new ArrayList<Packet1>();
        List<ShireWarkListData> shireWarkListDatas = new ArrayList<ShireWarkListData>();
        byte[] pcapHeader = new byte[24];//文件头
        try {
                raf = new RandomAccessFile(pcapfilepath, "r");
                fileLength = raf.length();

                try {
                    raf.read(pcapHeader);
                    setPcapheader(pcapHeader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    byte[] packetHeader = new byte[16];
                    while (raf.getFilePointer() < fileLength) {
                        raf.read(packetHeader);
                        Packet1 packet = new Packet1();
                        packet.parserPacket(packetHeader, packets, shireWarkListDatas, raf, flag);
                    }
                    setPackets(packets);

                    File file = new File(writeDataDir);
                    File fileHttp = new File(httpDataDir);
                    if(!file.exists())
                    {
                        file.mkdir();
                    }
                    if (!fileHttp.exists()){
                        fileHttp.mkdir();
                    }
                    RandomAccessFile rafWrite = null;
                    rafWrite = new RandomAccessFile(writeDataDir+writeDataPath, "rw");
                    //RandomAccessFile rafHttp = null;
                    //rafHttp = new RandomAccessFile(httpDataDir+writeDataPath,"rw");
                    for (int j = 0; j < packets.size(); j++) {
                        Packet1 packet = packets.get(j);
                        int start = 14 +packet.getIpPacket().getIpHeaderLen()+packet.getIpPacket().getTransHeaderLen();
                        int end =  14 + packet.getIpPacket().getIpTotalLen();

                        rafWrite.write(packet.getPacketBody(), start, end-start);

                        //String protocal = bufferedReader.readLine();
                        String[] protocal = null;
                        String info = null;
                        //System.out.println("=======packetbody======"+packet.getPacketBody());
                        info = new String((DataUtils.cutBytes(packet.getPacketBody(), start, end)));




                        protocal = info.split(System.lineSeparator());
                        //System.out.println("=======长度========"+info.length());
                        if(protocal!=null){
                            if (protocal[0].contains("HTTP")){
                                //start = info.indexOf("");
                                //System.out.println("========start=====" + start);
                                //System.out.println("=====info长度======="+info.length());
                                /*if(start!=-1){
                                    String httpdata  = info.substring(start,info.length());
                                    System.out.println("======info======"+httpdata);
                                    rafHttp.writeBytes(httpdata);
                                    System.out.println("======info======"+httpdata);
                                }*/
                                shireWarkListDatas.get(j).setProtocol("HTTP");
                                shireWarkListDatas.get(j).setInfo(protocal[0]);

                            }
                            if ((protocal[0].contains("FTP"))){
                                shireWarkListDatas.get(j).setProtocol("FTP");
                                shireWarkListDatas.get(j).setInfo(protocal[0]);
                            }
                        }


                    }
                    rafWrite.close();
                    //rafHttp.close();
                    setShireWarkListDatas(shireWarkListDatas);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
        }
    return getPrintInfo();
    }

    public List<ShireWarkListData> getPrintInfo(){
        Collections.sort(shireWarkListDatas, new Comparator<ShireWarkListData>() {
            @Override
            public int compare(ShireWarkListData o1, ShireWarkListData o2) {
                return (o1.getAbsoluteTime() > o2.getAbsoluteTime() ? 1: -1);
            }
        });
        int index = 0;
        for (ShireWarkListData u: shireWarkListDatas){
            double timeTmp;
            if (index==0){
                u.setTime("0.000000s");
            }else {
                timeTmp = u.getAbsoluteTime() - shireWarkListDatas.get(index-1).getAbsoluteTime();
                while (timeTmp>=60.0){
                    int minute = u.getMinute();
                    minute++;
                    u.setMinute(minute);
                    timeTmp -= 60.0;
                }
                if (u.getMinute()>0){
                    u.setTime(u.getMinute()+"m "+DataUtils.formatFloatNumber(timeTmp)+"s");
                }
                else {
                    u.setTime(DataUtils.formatFloatNumber(timeTmp)+"s");
                }

            }
            index++;
            u.setPacketSeqNum(index+"");
        }
        return shireWarkListDatas;
    }

}

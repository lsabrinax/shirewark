package com.company;

public class PacketHeader {
    private int timeS;
    private int timeMs;
    private int capLen;
    private int len;

    public int getTimeS() {
        return timeS;
    }
    public void setTimeS(int timeS) {
        this.timeS = timeS;
    }

    public int getTimeMs() {
        return timeMs;
    }
    public void setTimeMs(int timeMs) {
        this.timeMs = timeMs;
    }

    public int getCapLen() {
        return capLen;
    }
    public void setCapLen(int capLen) {
        this.capLen = capLen;
    }

    public int getLen() {
        return len;
    }
    public void setLen(int len) {
        this.len = len;
    }

    public void parseDataHeader(byte[] data_header) {
        byte[] buffer_4 = new byte[4];
        int timeS;
        int timeMs;
        int capLen;
        int len;

        buffer_4 = DataUtils.cutBytes(data_header,0,4);
        timeS = DataUtils.byteArrayToIntReverse(buffer_4);
        //System.out.println("========秒======="+ timeS);
        buffer_4 = DataUtils.cutBytes(data_header,4,8);
        timeMs = DataUtils.byteArrayToIntReverse(buffer_4);
        //System.out.println("=========微秒======" + timeMs);
        buffer_4 = DataUtils.cutBytes(data_header,8,12);
        capLen = DataUtils.byteArrayToIntReverse(buffer_4);
        buffer_4 = DataUtils.cutBytes(data_header,12,16);
        len = DataUtils.byteArrayToIntReverse(buffer_4);

        setCapLen(capLen);
        setLen(len);
        setTimeMs(timeMs);
        setTimeS(timeS);
    }

}

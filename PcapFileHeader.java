package com.company;

public class PcapFileHeader {
    private int magic;
    private int snaplen;
    private int linktype;

    public int getMagic() {
        return magic;
    }
    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getSnaplen() {
        return snaplen;
    }
    public void setSnaplen(int snaplen) {
        this.snaplen = snaplen;
    }

    public int getLinktype() {
        return linktype;
    }
    public void setLinktype(int linktype) {
        this.linktype = linktype;
    }

    public void parseFileHeader(byte[] file_header) {
    byte[] buffer_4;
    int magic;

    buffer_4 = DataUtils.cutBytes(file_header, 0,4);
    magic = DataUtils.byteArrayToInt4(buffer_4);
    setMagic(magic);

    }
}

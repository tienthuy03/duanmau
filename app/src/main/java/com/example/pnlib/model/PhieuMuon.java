package com.example.pnlib.model;

import java.util.Date;

public class PhieuMuon {
    private int MAPM;
    private String MATT;
    private int MATV;
    private int MASACH;
    private int TIENTHUE;
    private int TRASACH;
    private Date NGAY;

    public PhieuMuon() {
    }

    public PhieuMuon(int MAPM, String MATT, int MATV, int MASACH, int TIENTHUE, int TRASACH, Date NGAY) {
        this.MAPM = MAPM;
        this.MATT = MATT;
        this.MATV = MATV;
        this.MASACH = MASACH;
        this.TIENTHUE = TIENTHUE;
        this.TRASACH = TRASACH;
        this.NGAY = NGAY;
    }

    public int getMAPM() {
        return MAPM;
    }

    public void setMAPM(int MAPM) {
        this.MAPM = MAPM;
    }

    public String getMATT() {
        return MATT;
    }

    public void setMATT(String MATT) {
        this.MATT = MATT;
    }

    public int getMATV() {
        return MATV;
    }

    public void setMATV(int MATV) {
        this.MATV = MATV;
    }

    public int getMASACH() {
        return MASACH;
    }

    public void setMASACH(int MASACH) {
        this.MASACH = MASACH;
    }

    public int getTIENTHUE() {
        return TIENTHUE;
    }

    public void setTIENTHUE(int TIENTHUE) {
        this.TIENTHUE = TIENTHUE;
    }

    public int getTRASACH() {
        return TRASACH;
    }

    public void setTRASACH(int TRASACH) {
        this.TRASACH = TRASACH;
    }

    public Date getNGAY() {
        return NGAY;
    }

    public void setNGAY(Date NGAY) {
        this.NGAY = NGAY;
    }

    @Override
    public String toString() {
        return "PhieuMuon{" +
                "MAPM=" + MAPM +
                ", MATT='" + MATT + '\'' +
                ", MATV=" + MATV +
                ", MASACH=" + MASACH +
                ", TIENTHUE=" + TIENTHUE +
                ", TRASACH=" + TRASACH +
                ", NGAY=" + NGAY +
                '}';
    }
}

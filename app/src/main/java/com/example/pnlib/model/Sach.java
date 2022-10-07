package com.example.pnlib.model;

public class Sach {
    private int MASACH;
    private String TENSACH;
    private int GIATHUE;
    private int MALOAI;

    public Sach() {
        this.MASACH = MASACH;
        this.TENSACH = TENSACH;
        this.GIATHUE = GIATHUE;
        this.MALOAI = MALOAI;
    }

    public int getMASACH() {
        return MASACH;
    }

    public void setMASACH(int MASACH) {
        this.MASACH = MASACH;
    }

    public String getTENSACH() {
        return TENSACH;
    }

    public void setTENSACH(String TENSACH) {
        this.TENSACH = TENSACH;
    }

    public int getGIATHUE() {
        return GIATHUE;
    }

    public void setGIATHUE(int GIATHUE) {
        this.GIATHUE = GIATHUE;
    }

    public int getMALOAI() {
        return MALOAI;
    }

    public void setMALOAI(int MALOAI) {
        this.MALOAI = MALOAI;
    }

    @Override
    public String toString() {
        return TENSACH ;
    }
}

package com.example.pnlib.model;

public class LoaiSach {
    private int MALOAI;
    private String TENLOAI;


    public LoaiSach() {
        this.MALOAI = MALOAI;
        this.TENLOAI = TENLOAI;
    }

    public LoaiSach(String TENLOAI) {
        this.TENLOAI = TENLOAI;
    }

    public int getMALOAI() {
        return MALOAI;
    }

    public void setMALOAI(int MALOAI) {
        this.MALOAI = MALOAI;
    }

    public String getTENLOAI() {
        return TENLOAI;
    }

    public void setTENLOAI(String TENLOAI) {
        this.TENLOAI = TENLOAI;
    }

    @Override
    public String toString() {
        return MALOAI + "|" + TENLOAI;
    }
}

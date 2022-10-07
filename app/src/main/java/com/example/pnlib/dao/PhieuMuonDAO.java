package com.example.pnlib.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.pnlib.database.QuanLyThuVienSqlite;
import com.example.pnlib.model.PhieuMuon;
import com.example.pnlib.model.Sach;
import com.example.pnlib.model.Top;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    QuanLyThuVienSqlite quanLyThuVienSqlite;
    public PhieuMuonDAO(Context context){
        this.context = context;
        quanLyThuVienSqlite = new QuanLyThuVienSqlite(context);
        sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
    }
    public boolean insertPhieuMuon(PhieuMuon pm){
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATT", pm.getMATT());
        contentValues.put("MATV", pm.getMATV());
        contentValues.put("MASACH", pm.getMASACH());
        contentValues.put("NGAY", sdf.format(pm.getNGAY()));
        contentValues.put("TIENTHUE", pm.getTIENTHUE());
        contentValues.put("TRASACH", pm.getTRASACH());
        long check =  sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    public boolean updatePhieuMuon(PhieuMuon pm){
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATT", pm.getMATT());
        contentValues.put("MATV", pm.getMATV());
        contentValues.put("MASACH", pm.getMASACH());
        contentValues.put("NGAY", sdf.format(pm.getNGAY()));
        contentValues.put("TIENTHUE", pm.getTIENTHUE());
        contentValues.put("TRASACH", pm.getTRASACH());
        long check =  sqLiteDatabase.update("PHIEUMUON", contentValues, "MAPM = ?", new String[]{String.valueOf(String.valueOf(pm.getMAPM()))});
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    public  boolean deletePhieuMuon(String id){
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        long check =  sqLiteDatabase.delete("PHIEUMUON", "MAPM = ?", new String[]{id});
        if(check == -1){
            return false;
        }else {
            return true;
        }
    }
    //get data nhieu tham so
    @SuppressLint("Range")
    public List<PhieuMuon> getData(String sql, String...selectionArgs){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            PhieuMuon pm = new PhieuMuon();
            pm.setMAPM(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MAPM"))));
            pm.setMATT(cursor.getString(cursor.getColumnIndex("MATT")));
            pm.setMATV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MATV"))));
            pm.setMASACH(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MASACH"))));
            pm.setTIENTHUE(Integer.parseInt(cursor.getString(cursor.getColumnIndex("TIENTHUE"))));
            try {
                pm.setNGAY(sdf.parse(cursor.getString(cursor.getColumnIndex("NGAY"))));
            }catch (ParseException e){
                e.printStackTrace();
            }
            pm.setTRASACH(Integer.parseInt(cursor.getString(cursor.getColumnIndex("TRASACH"))));
            list.add(pm);
        }
        return list;
    }
    //get tat ca data
    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PHIEUMUON";
        return getData(sql);
    }
    //get tat ca data theo id
    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM PHIEUMUON WHERE MAPM = ?";
        List<PhieuMuon> list = getData(sql, id);
        return list.get(0);
    }
    // Thong ke top 10 sach muon nhieu nhat
    @SuppressLint("Range")
    public List<Top> getTop(){
        String sqlTop = "SELECT MASACH, COUNT(MASACH) AS SOLUONG FROM PHIEUMUON GROUP BY MASACH ORDER BY SOLUONG DESC LIMIT 10";
        List<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor cursor = sqLiteDatabase.rawQuery(sqlTop, null);
        while (cursor.moveToNext()){
            Top top = new Top();
            @SuppressLint("Range") Sach sach = sachDAO.getID(cursor.getString(cursor.getColumnIndex("MASACH")));
            top.setTENSACH(sach.getTENSACH());
            top.setSOLUONG(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOLUONG"))));
            list.add(top);
        }
        return list;
    }
    //thong ke doanh thuu
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay){
        String sqlDoanhThu = "SELECT SUM(TIENTHUE) AS DOANHTHU FROM PHIEUMUON WHERE NGAY BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()){
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("DOANHTHU"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }

}
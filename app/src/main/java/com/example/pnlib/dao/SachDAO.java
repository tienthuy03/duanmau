package com.example.pnlib.dao;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.pnlib.database.QuanLyThuVienSqlite;
import com.example.pnlib.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase sqLiteDatabase;
    QuanLyThuVienSqlite quanLyThuVienSqlite;
    public SachDAO(Context context){
        quanLyThuVienSqlite = new QuanLyThuVienSqlite(context);
        sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
    }
    public boolean insertSach (Sach s){
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENSACH", s.getTENSACH());
        contentValues.put("GIATHUE", s.getGIATHUE());
        contentValues.put("MALOAI", s.getMALOAI());
        long check =  sqLiteDatabase.insert("SACH", null, contentValues);
        if (check == -1){
            return  false;
        }else {
            return true;
        }
    }
    public boolean updateSach(Sach s){
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENSACH", s.getTENSACH());
        contentValues.put("GIATHUE", s.getGIATHUE());
        contentValues.put("MALOAI", s.getMALOAI());
        long check =  sqLiteDatabase.update("SACH", contentValues, "MASACH = ?", new String[]{String.valueOf(s.getMASACH())});
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    public boolean deleteSach (int id){
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        long check =  sqLiteDatabase.delete("SACH", "MASACH = ?", new String[]{String.valueOf(id)});
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    //get data nhieu tham so
    @SuppressLint("Range")
    public List<Sach> getData(String sql, String...selectionArgs){
        List<Sach> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            Sach s = new Sach();
            s.setMASACH(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MASACH"))));
            s.setTENSACH(cursor.getString(cursor.getColumnIndex("TENSACH")));
            s.setGIATHUE(Integer.parseInt(cursor.getString(cursor.getColumnIndex("GIATHUE"))));
            s.setMALOAI(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MALOAI"))));
            list.add(s);
        }
        return list;
    }
    //get tat ca data
    public List<Sach> getAll(){
        String sql = "SELECT * FROM SACH";
        return getData(sql);
    }
    //get tat ca data theo id
    public Sach getID(String id){
        String sql = "SELECT * FROM SACH WHERE MASACH = ?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
    }

}


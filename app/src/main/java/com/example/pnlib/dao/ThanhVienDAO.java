package com.example.pnlib.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.pnlib.database.QuanLyThuVienSqlite;
import com.example.pnlib.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    //GOI LẠI SQL
    private SQLiteDatabase sqLiteDatabase;
    QuanLyThuVienSqlite quanLyThuVienSqlite;
    public ThanhVienDAO(Context context){
        quanLyThuVienSqlite = new QuanLyThuVienSqlite(context);
        sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
    }
    // Ham Insert
    public boolean insertThanhVien(ThanhVien tv){
        // khoi tao Contentvalue
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HOTEN", tv.getHOTEN());
        contentValues.put("NAMSINH", tv.getNAMSINH());
       long check = sqLiteDatabase.insert("THANHVIEN",null,  contentValues );
        if (check == -1) {
            return false;
        }else {
            return true;
        }

    }
    //Ham Update
    public boolean updateThanhVien(ThanhVien tv){
        // Khoi tao ContentValue
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HOTEN", tv.getHOTEN());
        contentValues.put("NAMSINH", tv.getNAMSINH());
        long check =  sqLiteDatabase.update("THANHVIEN", contentValues, "MATV = ?", new String[]{String.valueOf(tv.getMATV())});
        if(check == -1){
            return false;
        }else {
            return true;
        }
    }
    //Ham Delete
    public boolean deleteThanhVien(int id){
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        long check = sqLiteDatabase.delete("THANHVIEN","MATV = ?",new String[]{String.valueOf(id)});
        if(check == -1){
            return  false;
        }
        return true;
    }
    // Ham get tat ca data
    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM THANHVIEN";
        return getData(sql);
    }
    //Ham get data theo id
    public ThanhVien getID(String id){
        String sql = "SELECT * FROM THANHVIEN WHERE MATV = ?";
        List<ThanhVien> list = getData(sql, id);
        return list.get(0);
    }
    //Ham get data nhieu tham so
    @SuppressLint("Range")
    private List<ThanhVien> getData(String sql, String...seletionArgs){
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,seletionArgs);
        while (cursor.moveToNext()){
           ThanhVien tv = new ThanhVien();
            tv.MATV = Integer.parseInt(cursor.getString(cursor.getColumnIndex("MATV")));
            tv.HOTEN = cursor.getString(cursor.getColumnIndex("HOTEN"));
            tv.NAMSINH = cursor.getString(cursor.getColumnIndex("NAMSINH"));
            Log.i("//=====", tv.toString());
            list.add(tv);
        }
        return list;
    }
}

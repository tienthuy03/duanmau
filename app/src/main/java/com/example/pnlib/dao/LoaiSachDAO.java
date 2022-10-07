package com.example.pnlib.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.pnlib.database.QuanLyThuVienSqlite;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    //goi lai SQLite
    private SQLiteDatabase sqLiteDatabase;
    QuanLyThuVienSqlite quanLyThuVienSqlite;
    public LoaiSachDAO(Context context){
        quanLyThuVienSqlite = new QuanLyThuVienSqlite(context);
        sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
    }
    public boolean insertLoaiSach(LoaiSach ls){
        //Tao contentvalues
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENLOAI", ls.getTENLOAI());
        long check =  sqLiteDatabase.insert("LOAISACH", null,contentValues);
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    public boolean updateLoaiSach(LoaiSach ls){
        //Tao contentvalues
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d("ls",ls.toString() + "============================");
        contentValues.put("TENLOAI", ls.getTENLOAI());
        long check =  sqLiteDatabase.update("LOAISACH", contentValues, "MALOAI = ?", new String[]{String.valueOf(ls.getMALOAI())});
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    public boolean deleteLoaisach(int id){
        SQLiteDatabase sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
        long check = sqLiteDatabase.delete("LOAISAch","MALOAI = ?",new String[]{String.valueOf(id)});
        if(check == -1){
            return  false;
        }
        return true;
    }
    //get data nhieu tham so
    @SuppressLint("Range")
    public List<LoaiSach> getData(String sql, String...seletionArgs){
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, seletionArgs);
        while (cursor.moveToNext()){
            LoaiSach ls = new LoaiSach();
           ls.setMALOAI(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MALOAI"))));
           ls.setTENLOAI(cursor.getString(cursor.getColumnIndex("TENLOAI")));
            list.add(ls);
        }
        return list;
    }
    //get tat ca data
    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LOAISACH";
        return getData(sql);
    }
    // get data theo id
    public LoaiSach getID(String id){
        String sql = "SELECT * FROM LOAISACH WHERE MALOAI = ?";
        List<LoaiSach> list = getData(sql, id);
        return list.get(0);
    }


}

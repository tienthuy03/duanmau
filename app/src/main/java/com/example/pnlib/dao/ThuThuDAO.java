package com.example.pnlib.dao;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.pnlib.database.QuanLyThuVienSqlite;
import com.example.pnlib.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    //goi lai sqlite
    private SQLiteDatabase sqLiteDatabase;
    QuanLyThuVienSqlite quanLyThuVienSqlite;
    public ThuThuDAO(Context context){
        quanLyThuVienSqlite = new QuanLyThuVienSqlite(context);
        sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();
    }
    public long insertThuThu(ThuThu tt){
        // Tao contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATT", tt.getMATT());
        contentValues.put("HOTEN", tt.getHOTEN());
        contentValues.put("MATKHAU", tt.getMATKHAU());
       return  sqLiteDatabase.insert("THUTHU", null, contentValues);

    }
    public long updatePass(ThuThu tt){
        //Tao contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put("HOTEN", tt.getHOTEN());
        contentValues.put("MATKHAU", tt.getMATKHAU());
        return sqLiteDatabase.update("THUTHU",contentValues, "MATT = ?", new String[]{tt.getMATT()});
    }
    public long deleteThuThu(String id){
        return sqLiteDatabase.delete("THUTHU", "MATT = ?", new String[]{id});
    }
    @SuppressLint("Range")
    public List<ThuThu> getData(String sql, String...selectionArgs){
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            ThuThu tt = new ThuThu();
            tt.setMATT(cursor.getString(cursor.getColumnIndex("MATT")));
            tt.setHOTEN(cursor.getString(cursor.getColumnIndex("HOTEN")));
            tt.setMATKHAU(cursor.getString(cursor.getColumnIndex("MATKHAU")));
            Log.i("//=====", tt.toString());
            list.add(tt);
        }
        return list;
    }
    //get tat ca data
    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM THUTHU";
        return getData(sql);
    }
    // get data theo id
    public ThuThu getID(String id){
        String sql = "SELECT * FROM THUTHU WHERE MATT =  ?";
        List<ThuThu> list = getData(sql, id);
        return  list.get(0);
    }
    // kiem tra login
    public int checkLogin(String id, String matkhau){
        String sql = "SELECT * FROM THUTHU WHERE MATT = ? AND MATKHAU = ?";
        List<ThuThu> list = getData(sql, id, matkhau);
        if(list.size() ==  0){
            return -1;
        }else {
            return 1;
        }
    }
}

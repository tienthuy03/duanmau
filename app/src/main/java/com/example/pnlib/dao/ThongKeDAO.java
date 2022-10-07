package com.example.pnlib.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.example.pnlib.database.QuanLyThuVienSqlite;

import java.text.SimpleDateFormat;

public class ThongKeDAO {
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public ThongKeDAO(Context context){
        this.context = context;
        QuanLyThuVienSqlite quanLyThuVienSqlite = new QuanLyThuVienSqlite(context);
        sqLiteDatabase = quanLyThuVienSqlite.getWritableDatabase();

    }

}

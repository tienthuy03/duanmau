package com.example.pnlib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuanLyThuVienSqlite extends SQLiteOpenHelper {
    // khoi tao ten cua DB va Version
    static final String dbName = "PNLIB";
   static final int dbVersion = 1;

    public QuanLyThuVienSqlite(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Tao bang Thu Thu
        String createTbThuThu = "CREATE TABLE THUTHU (MATT TEXT PRIMARY KEY, HOTEN TEXT NOT NULL, MATKHAU TEXT NOT NULL)";
        sqLiteDatabase.execSQL(createTbThuThu);

        //Tao bang Thanh Vien
        String createTbThanhVien = "CREATE TABLE THANHVIEN (MATV INTEGER PRIMARY KEY AUTOINCREMENT, HOTEN TEXT NOT NULL, NAMSINH TEXT NOT NULL)";
        sqLiteDatabase.execSQL(createTbThanhVien);

        //Tao bang Loai Sach
        String createTbLoaiSach = " CREATE TABLE LOAISACH (MALOAI INTEGER PRIMARY KEY AUTOINCREMENT, TENLOAI TEXT NOT NULL)";
        sqLiteDatabase.execSQL(createTbLoaiSach);

        //Tao bang Sach
        String createTbSach = "CREATE TABLE SACH (MASACH INTEGER PRIMARY KEY AUTOINCREMENT, TENSACH TEXT NOT NULL, GIATHUE INTEGER NOT NULL, MALOAI INTEGER REFERENCES LOAISACH(MALOAI))";
        sqLiteDatabase.execSQL(createTbSach);

        // Tao bang Loai Phieu Muon
        String creaTbPhieuMuon = "CREATE TABLE PHIEUMUON (MAPM INTEGER PRIMARY KEY AUTOINCREMENT, MATT TEXT REFERENCES THUTHU(MATT), MATV INTEGER REFERENCES THANHVIEN(MATV), MASACH INTEGER REFERENCES SACH(MASACH),TIENTHUE INTEGER NOT NULL, NGAY DATE NOT NULL, TRASACH INTEGER NOT NULL )";
        sqLiteDatabase.execSQL(creaTbPhieuMuon);

        //inert du lieu mau
        String insThuThu = "INSERT INTO THUTHU VALUES('tt1','Nguyen Van Teo', 'tt123'),('tt2','Nguyen Van be', 'tt125'),('tt3','Nguyen Van Can', 'tt345'),('admin','tienlttps23958', 'ps23958')";
        sqLiteDatabase.execSQL(insThuThu);
        String insThanhVien = "INSERT INTO THANHVIEN VALUES (1, 'Tran Van Nam','2000'),(2, 'Tran van Binh','2002'),(3, 'Nguyen Thi be','1999'),(4, 'Le Thi Mai','2000')";
        sqLiteDatabase.execSQL(insThanhVien);
        String insLoaiSach = "INSERT INTO LOAISACH VALUES (1,'Công Nghệ Thông Tin'),(2,'Kinh Tế-chính Trị'),(3, 'Thiết kế đồ hoạ'), (4, 'Truyện trinh thám')";
        sqLiteDatabase.execSQL(insLoaiSach);
        String insSach = "INSERT INTO SACH VALUES (1, 'Lập trình java 1', 1500, 1),(2, 'Lập trình java 2', 5500, 1),(3, 'Android cơ bản', 2000, 1),(4, 'MacLeNin', 3500, 2),(5, 'Photoshop', 2000, 3)";
        sqLiteDatabase.execSQL(insSach);
        String insPhieuMuon = "INSERT INTO PHIEUMUON VALUES (1, 'tt1', 1, 2, 5500, '2022/10/09',1 ),(2, 'tt1', 1, 3, 4500, '2022/08/09',0 ),(3, 'tt3', 3, 4, 3500, '2022/09/09',1 )";
        sqLiteDatabase.execSQL(insPhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTbLoaiThuThu = "DROP TABLE IF EXISTS THUTHU";
        sqLiteDatabase.execSQL(dropTbLoaiThuThu);
        String dropTbThanhVien = "DROP TABLE IF EXISTS THANHVIEN";
        sqLiteDatabase.execSQL(dropTbThanhVien);
        String dropTbLoaiSach = "DROP TABLE IF EXISTS LOAISACH";
        sqLiteDatabase.execSQL(dropTbLoaiSach);
        String dropTbSach = "DROP TABLE IF EXISTS SACH";
        sqLiteDatabase.execSQL(dropTbSach);
        String dropTbPhieuMuon = "DROP TABLE IF EXISTS PHIEUMUON";
        sqLiteDatabase.execSQL(dropTbPhieuMuon);
        onCreate(sqLiteDatabase);
    }

    }
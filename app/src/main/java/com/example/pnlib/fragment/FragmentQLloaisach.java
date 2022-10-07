package com.example.pnlib.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;


import com.example.pnlib.R;
import com.example.pnlib.adapter.LoaiSachAdapter;
import com.example.pnlib.adapter.ThanhVienAdapter;
import com.example.pnlib.dao.LoaiSachDAO;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentQLloaisach extends Fragment {
    RecyclerView rcvLoaiSach;
    ArrayList<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;
    LoaiSachAdapter loaiSachAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlloaisach, container, false);
        rcvLoaiSach = view.findViewById(R.id.rcvLoaiSach);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvLoaiSach.setLayoutManager(gridLayoutManager);
        loaiSachDAO = new LoaiSachDAO(getContext());
        loadData();

        FloatingActionButton fabAddLoaiSach = view.findViewById(R.id.fabAddLoaiSach);
        fabAddLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemLoaiSach();
            }
        });
        return view;
    }
    public  void ThemLoaiSach(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themloaisach, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText edtTenLS = view.findViewById(R.id.edtTenloaisach);
        Button btnXacnhan = view.findViewById(R.id.btnXacnhanthemls);
        Button btnHuy =view.findViewById(R.id.btnHuythemls);

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenls = edtTenLS.getText().toString();
                LoaiSach ls = new LoaiSach(tenls);
                if (edtTenLS.length() == 0){
                    Toast.makeText(getContext(), "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (loaiSachDAO.insertLoaiSach(ls)) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    public void loadData(){
        list = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        loaiSachAdapter = new LoaiSachAdapter(list, getContext(), loaiSachDAO);
        rcvLoaiSach.setAdapter(loaiSachAdapter);
    }
}

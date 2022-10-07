package com.example.pnlib.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pnlib.R;
import com.example.pnlib.adapter.ThanhVienAdapter;
import com.example.pnlib.dao.ThanhVienDAO;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentQLthanhvien extends Fragment {
    RecyclerView rcvThanhVien;
    ArrayList<ThanhVien> list;
    ThanhVienDAO thanhVienDAO;
    ThanhVienAdapter thanhVienRecycleAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthanhvien, container, false);
        rcvThanhVien = view.findViewById(R.id.rcvThanhVien);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvThanhVien.setLayoutManager(layoutManager);
        thanhVienDAO = new ThanhVienDAO(getContext());
        list = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        thanhVienRecycleAdapter = new ThanhVienAdapter(list, getContext(), thanhVienDAO);
        rcvThanhVien.setAdapter(thanhVienRecycleAdapter);
        FloatingActionButton fabAddThanhVien = view.findViewById(R.id.fabAddThanhVien);


        fabAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThemTV();
            }
        });

        return view;
    }
    public void dialogThemTV (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themthanhvien,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText edtTenTV = view.findViewById(R.id.edtTenTV);
        EditText edtNamSinhTV =view.findViewById(R.id.edtNamSinhTV);
        Button btnXacnhan = view.findViewById(R.id.btnXacnhanthemTV);
        Button btnHuy = view.findViewById(R.id.btnHuythemTV);

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tentv = edtTenTV.getText().toString();
                String namsinh = edtNamSinhTV.getText().toString();
                ThanhVien tv = new ThanhVien(tentv, namsinh);
                if (edtTenTV.length() == 0 || edtNamSinhTV.length() == 0){
                    Toast.makeText(getContext(), "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (thanhVienDAO.insertThanhVien(tv)){
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
     }

    public void loadData(){
        list = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        thanhVienRecycleAdapter = new ThanhVienAdapter(list, getContext(), thanhVienDAO);
        rcvThanhVien.setAdapter(thanhVienRecycleAdapter);
    }

}

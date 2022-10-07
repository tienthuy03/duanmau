package com.example.pnlib.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pnlib.R;
import com.example.pnlib.adapter.SachAdapter;
import com.example.pnlib.adapter.ThanhVienAdapter;
import com.example.pnlib.dao.LoaiSachDAO;
import com.example.pnlib.dao.SachDAO;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.Sach;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentQLsach extends Fragment {
    RecyclerView rcvSach;
    ArrayList<Sach> list;
    SachDAO sachDAO;
    SachAdapter sachAdapter;
    LoaiSachDAO loaiSachDAO;
    ArrayList<LoaiSach> listLS;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlsach, container, false);
        rcvSach = view.findViewById(R.id.rcvSach);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvSach.setLayoutManager(layoutManager);
        sachDAO = new SachDAO(getContext());
        loadData();
        FloatingActionButton fabAddSach = view.findViewById(R.id.fabAddSach);

        fabAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemSach();
            }
        });
        return view;
    }
    public void loadData(){
        list = (ArrayList<Sach>) sachDAO.getAll();
        sachAdapter = new SachAdapter(list, getContext(), sachDAO);
        rcvSach.setAdapter(sachAdapter);
    }
    public void ThemSach() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themsach, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText edtTenSach = view.findViewById(R.id.edtTensach);
        EditText edtGia = view.findViewById(R.id.edtGiathuesach);
        Spinner spnLoaiSach = view.findViewById(R.id.spnTensach);
        Button btnXacnhan = view.findViewById(R.id.btnXacnhanthemsach);
        Button btnHuy = view.findViewById(R.id.btnHuythemsach);

        loaiSachDAO  = new LoaiSachDAO(getContext());
        listLS = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        listLS = new ArrayList<>();
        listLS = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        ArrayAdapter arrayAdapte = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listLS);
        spnLoaiSach.setAdapter(arrayAdapte);
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTenSach.length() == 0 || edtGia.length() == 0){
                    Toast.makeText(getContext(), "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if(listLS.size()>0){
                        Sach sach = new Sach();
                        LoaiSach loaiSach = (LoaiSach) spnLoaiSach.getSelectedItem();
                        sach.setTENSACH(String.valueOf(edtTenSach.getText()));
                        sach.setGIATHUE(Integer.parseInt(edtGia.getText().toString()));
                        sach.setMALOAI(loaiSach.getMALOAI());
                        if (sachDAO.insertSach(sach)) {
                            Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                            loadData();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
                        }
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
}



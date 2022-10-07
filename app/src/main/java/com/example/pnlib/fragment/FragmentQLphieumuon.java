package com.example.pnlib.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.R;
import com.example.pnlib.adapter.LoaiSachAdapter;
import com.example.pnlib.adapter.PhieuMuonAdapter;
import com.example.pnlib.dao.PhieuMuonDAO;
import com.example.pnlib.dao.SachDAO;
import com.example.pnlib.dao.ThanhVienDAO;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.PhieuMuon;
import com.example.pnlib.model.Sach;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentQLphieumuon extends Fragment {
    RecyclerView rcvpm;
    ArrayList<PhieuMuon> list;
    PhieuMuonDAO phieuMuonDAO;
    PhieuMuonAdapter phieuMuonAdapter;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    ArrayList<Sach> lists = new ArrayList<>();
    ArrayList<ThanhVien> listtv = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int matv;
    int mas;
    int tienthue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlphieumuon,container, false );
        rcvpm = view.findViewById(R.id.rcvPM);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvpm.setLayoutManager(layoutManager);
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        loadData();
        FloatingActionButton fabAddpm = view.findViewById(R.id.fabAddPM);


        fabAddpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thempm();
            }
        });

        return view;
    }
    public void Thempm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.dialog_themphieumuon, null);
        builder.setView(view1);
        AlertDialog dialog = builder.create();
        dialog.show();

        Spinner spnTennm = view1.findViewById(R.id.spnNamePM);
        Spinner spnTensach = view1.findViewById(R.id.spnTensachthempm);
        EditText edtngaythue = view1.findViewById(R.id.edtngaythuethempm);
        TextView txtTienthue = view1.findViewById(R.id.txtTienthuethempm);
        CheckBox chkTrangthai = view1.findViewById(R.id.chkTrangthai);
        Button btnXacnhan = view1.findViewById(R.id.btnXacnhanthempm);
        Button btnHuy = view1.findViewById(R.id.btnHuythempm);

        sachDAO = new SachDAO(getContext());
        thanhVienDAO = new ThanhVienDAO(getContext());
        lists = (ArrayList<Sach>) sachDAO.getAll();
        listtv = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        ArrayAdapter adapter_sach = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, lists );
        ArrayAdapter adapter_tv = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listtv);
        spnTennm.setAdapter(adapter_tv);
        spnTensach.setAdapter(adapter_sach);
        edtngaythue.setText(sdf.format(new Date()));
        spnTennm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                matv = listtv.get(i).getMATV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnTensach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mas  = lists.get(i).getMASACH();
                tienthue = lists.get(i).getGIATHUE();
                txtTienthue.setText("" + tienthue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listtv.size() > 0 && lists.size()> 0){
                    PhieuMuon phieuMuon = new PhieuMuon();
                    ThanhVien thanhVien = (ThanhVien) spnTennm.getSelectedItem();
                    Sach sach = (Sach) spnTensach.getSelectedItem();
                    phieuMuon.setMASACH(sach.getMASACH());
                    phieuMuon.setMATV(thanhVien.getMATV());
                    if (chkTrangthai.isChecked()){
                        phieuMuon.setTRASACH(1);
                    }else {
                        phieuMuon.setTRASACH(0);
                    }
                    Sach s = sachDAO.getID(phieuMuon.getMASACH()+"");
                    phieuMuon.setTIENTHUE(s.getGIATHUE());
                    long millis = System.currentTimeMillis();
                    java.sql.Date date = new java.sql.Date(millis);
                    phieuMuon.setNGAY(java.sql.Date.valueOf(String.valueOf(date)));
                    if (phieuMuonDAO.insertPhieuMuon(phieuMuon)){
                        Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
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
                dialog.dismiss();
            }
        });

    }
    public void loadData(){
        list = (ArrayList<PhieuMuon>) phieuMuonDAO.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(list, getContext(), sachDAO, thanhVienDAO, phieuMuonDAO);
        rcvpm.setAdapter(phieuMuonAdapter);
    }
}

package com.example.pnlib.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.BuildConfig;
import com.example.pnlib.R;
import com.example.pnlib.dao.PhieuMuonDAO;
import com.example.pnlib.dao.SachDAO;
import com.example.pnlib.dao.ThanhVienDAO;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.PhieuMuon;
import com.example.pnlib.model.Sach;
import com.example.pnlib.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuomViewHolder> {
    private ArrayList<PhieuMuon> list;
    private Context context;
    private SachDAO sachDAO;
    private ThanhVienDAO thanhVienDAO;
     private PhieuMuonDAO phieuMuonDAO;
     private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    ArrayList<Sach> lists = new ArrayList<>();
    ArrayList<ThanhVien> listtv = new ArrayList<>();
    int matv, mas, tien;
    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context, SachDAO sachDAO, ThanhVienDAO thanhVienDAO, PhieuMuonDAO phieuMuonDAO) {
        this.list = list;
        this.context = context;
        this.sachDAO = sachDAO;
        this.thanhVienDAO = thanhVienDAO;
        this.phieuMuonDAO = phieuMuonDAO;
    }

    @NonNull
    @Override
    public PhieuMuomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.itemphieumuon, parent, false);
        return new PhieuMuomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuomViewHolder holder, int position) {
            PhieuMuon pm = list.get(position);
            sachDAO = new SachDAO(context);
            Sach sach = sachDAO.getID(String.valueOf(pm.getMASACH()));
            thanhVienDAO = new ThanhVienDAO(context);
            ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf((pm.getMATV())));
            holder.txtMapm.setText("Mã phiếu: " +pm.getMAPM());
            holder.txtMaS.setText("Tên sách: " + sach.getTENSACH());
            holder.txtMatv.setText("Thành viên: " + thanhVien.getHOTEN());
            holder.txtTienThue.setText("Tiền thuê: " + pm.getTIENTHUE());
            holder.txtNgay.setText("Ngày thuê: " + sdf.format(pm.getNGAY()));
            if (pm.getTRASACH() == 1){
                holder.txtTrangthai.setTextColor(Color.BLUE);
                holder.txtTrangthai.setText("Đã trả sách");
            }else {
                holder.txtTrangthai.setTextColor(Color.RED);
                holder.txtTrangthai.setText("Chưa trả sách");
            }
            holder.ivdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xoá phiếu mượn " + pm.getMAPM() + " này không ?");
                    builder.setPositiveButton("Không đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                          if (phieuMuonDAO.deletePhieuMuon(String.valueOf(pm.getMAPM()))){
                              Toast.makeText(context, "Xoá thành công phiếu mượn số " + pm.getMAPM(), Toast.LENGTH_SHORT).show();
                              reloadData();
                          }else {
                              Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                          }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            holder.CardViewpm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Capnhatpm(pm);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhieuMuomViewHolder extends RecyclerView.ViewHolder {
        TextView txtMapm, txtMaS, txtMatv, txtTienThue, txtNgay, txtTrangthai;
        CardView CardViewpm;
        ImageView ivdelete;
        public PhieuMuomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMapm = itemView.findViewById(R.id.txtMapm);
            txtMaS = itemView.findViewById(R.id.txtMaS);
            txtMatv = itemView.findViewById(R.id.txtMaTV);
            txtTienThue = itemView.findViewById(R.id.txtTienThue);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTrangthai = itemView.findViewById(R.id.txtTrangThai);
            CardViewpm = itemView.findViewById(R.id.CardViewpm);
            ivdelete = itemView.findViewById(R.id.ivdelete);
        }
    }
    private void reloadData(){
        list.clear();
        list = (ArrayList<PhieuMuon>) phieuMuonDAO.getAll();
        notifyDataSetChanged();
    }

    public void Capnhatpm(PhieuMuon phieuMuon){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_capnhatphieumuon, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        Spinner spnnguoimuon = view.findViewById(R.id.spnNamePMCapnhat);
        Spinner spnTensach = view.findViewById(R.id.spnTensachpmCapnhat);
        EditText edtNgaythue = view.findViewById(R.id.edtngaythueCapnhatpm);
        TextView txtGia = view.findViewById(R.id.txtTienthueCapnhatpm);
        Button btnXacnhan = view.findViewById(R.id.btnXacnhanCapnhatpm);
        Button btnHuy = view.findViewById(R.id.btnHuyCapnhatpm);
        CheckBox chkTrangthai = view.findViewById(R.id.chkTrangthaiCapnhat);

        edtNgaythue.setText("" + sdf.format(phieuMuon.getNGAY()));
        txtGia.setText("" +phieuMuon.getTIENTHUE());
        if (phieuMuon.getTRASACH() == 1){
            chkTrangthai.setChecked(true);
        }else {
            chkTrangthai.setChecked(false);
        }
        listtv = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        lists = (ArrayList<Sach>) sachDAO.getAll();
        ArrayAdapter adapter_thanhvien = new ArrayAdapter(context, android.R.layout.simple_list_item_1, listtv);
        ArrayAdapter adapter_sach = new ArrayAdapter(context, android.R.layout.simple_list_item_1, lists);
        spnnguoimuon.setAdapter(adapter_thanhvien);
        spnTensach.setAdapter(adapter_sach);
        spnnguoimuon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                mas = lists.get(i).getMASACH();
                tien = lists.get(i).getGIATHUE();
                txtGia.setText(""+tien);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int vitri_s = -1;
        int vitri_tv = -1;
        for (int i = 0; i <listtv.size(); i++){
            if (phieuMuon.getMATV()== (listtv.get(i).getMATV())){
                vitri_tv = i;
                break;
            }
        }
        spnnguoimuon.setSelection(vitri_tv);
        for (int i = 0; i <lists.size(); i++){
            if (phieuMuon.getMASACH()== (lists.get(i).getMASACH())){
                vitri_s = i;
                break;
            }
        }
        spnTensach.setSelection(vitri_s);
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThanhVien thanhVien = (ThanhVien) spnnguoimuon.getSelectedItem();
                Sach sach = (Sach) spnTensach.getSelectedItem();
                phieuMuon.setMASACH(sach.getMASACH());
                phieuMuon.setMATV(thanhVien.getMATV());
                long millis = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                phieuMuon.setNGAY(java.sql.Date.valueOf(String.valueOf(date)));
                Sach s = sachDAO.getID(phieuMuon.getMASACH()+"");
                if (chkTrangthai.isChecked()){
                    phieuMuon.setMASACH(1);
                }else {
                    phieuMuon.setTRASACH(0);
                }
                if (phieuMuonDAO.updatePhieuMuon(phieuMuon)){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
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
}

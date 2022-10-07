package com.example.pnlib.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.pnlib.dao.LoaiSachDAO;
import com.example.pnlib.dao.SachDAO;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.Sach;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

import kotlin.jvm.internal.PropertyReference0Impl;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    private ArrayList<Sach> list ;
    private Context context;
    private SachDAO sachDAO;
    private LoaiSachDAO loaiSachDAO;
    private LoaiSach loaiSach;
    private Sach sach;
    List<LoaiSach> listLS = new ArrayList<>();

    public SachAdapter(ArrayList<Sach> list, Context context, SachDAO sachDAO) {
        this.list = list;
        this.context = context;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.itemsach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        Sach sach = list.get(position);
        sachDAO = new SachDAO(context);
        loaiSachDAO = new LoaiSachDAO(context);
        loaiSach = loaiSachDAO.getID(String.valueOf(sach.getMALOAI()));
        holder.txtMaSach.setText("Mã sách: " +sach.getMASACH());
        holder.txtTenSach.setText("Tên sách: " +sach.getTENSACH());
        holder.txtLoaiSach.setText("Loại sách: " +loaiSach.getTENLOAI());
        holder.txtGia.setText("Giá thuê: " +sach.getGIATHUE());

        holder.CardViewSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CapnhatSach(sach);
            }
        });

        holder.ivdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có muốn xoá cuốn sách " + sach.getTENSACH() + " này không");
                builder.setPositiveButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (sachDAO.deleteSach(sach.getMASACH())){
                            Toast.makeText(context, "Xoá thành công sách " + sach.getTENSACH(), Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SachViewHolder extends RecyclerView.ViewHolder {
        CardView CardViewSach;
        TextView txtMaSach, txtTenSach, txtLoaiSach, txtGia;
        ImageView ivdelete;
        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            CardViewSach = itemView.findViewById(R.id.CardViewS);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtLoaiSach = itemView.findViewById(R.id.txtLoaiSach);
            txtGia = itemView.findViewById(R.id.txtGia);
            ivdelete = itemView.findViewById(R.id.ivdelete);
        }
    }
    private void reloadData(){
        list.clear();
        list = (ArrayList<Sach>) sachDAO.getAll();
        notifyDataSetChanged();
    }
    public  void CapnhatSach(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_capnhatsach, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText edtTenSach = view.findViewById(R.id.edtTensachCapnhat);
        EditText edtGia = view.findViewById(R.id.edtGiathuesachCapnhat);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaisachCapnhat);
        Button btnXacnhan = view.findViewById(R.id.btnXacnhancapnhatsach);
        Button btnHuy = view.findViewById(R.id.btnHuycapnhatsach);
        listLS = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        ArrayAdapter arrayAdapte = new ArrayAdapter(context, android.R.layout.simple_list_item_1, listLS);
        spnLoaiSach.setAdapter(arrayAdapte);
        int abc = -1;
        for (int i = 0; i < listLS.size(); i++) {
            if (sach.getMALOAI() == (listLS.get(i).getMALOAI())) {
                abc = i;
                break;
            }
        }
        spnLoaiSach.setSelection(abc);
        edtTenSach.setText(sach.getTENSACH());
        edtGia.setText(sach.getGIATHUE() + "");

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaiSach loaiSach = (LoaiSach) spnLoaiSach.getSelectedItem();
                sach.setTENSACH(edtTenSach.getText().toString());
                sach.setGIATHUE(Integer.parseInt(edtGia.getText().toString()));
                sach.setMALOAI(loaiSach.getMALOAI());
                if (edtTenSach.length() == 0 || edtGia.length()==0 ){
                    Toast.makeText(context, "vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (sachDAO.updateSach(sach)){
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        reloadData();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
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

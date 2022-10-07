package com.example.pnlib.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pnlib.R;
import com.example.pnlib.dao.ThanhVienDAO;
import com.example.pnlib.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    private List<ThanhVien> listtv;
    private Context context;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(ArrayList<ThanhVien> listtv, Context context, ThanhVienDAO thanhVienDAO) {
        this.listtv = listtv;
        this.context = context;
        this.thanhVienDAO = thanhVienDAO;
    }
    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.itemthanhvien, parent, false);
        ThanhVienViewHolder thanhVienViewHolder = new ThanhVienViewHolder(view);
        return thanhVienViewHolder;

    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = listtv.get(position);
        thanhVienDAO = new ThanhVienDAO(context);
        holder.txtMaTV.setText("Mã thành viên: " +thanhVien.getMATV());
        holder.txtTenTV.setText( "Tên thành viên: " + thanhVien.getHOTEN());
        holder.txtNamSinh.setText("Năm sinh: " + thanhVien.getNAMSINH());

        holder.CardViewThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Capnhatthanhvien(thanhVien);
            }
        });

        holder.ivdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xoá thành viên " + thanhVien.getHOTEN() + " này không ? ");
                builder.setPositiveButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setNegativeButton(" đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (thanhVienDAO.deleteThanhVien(thanhVien.getMATV())) {
                            Toast.makeText(context, "Xoá Thành Công Thành Viên: " + thanhVien.getHOTEN(), Toast.LENGTH_SHORT).show();
                            reloadData();
                        } else {
                            Toast.makeText(context, "Xoá Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return listtv.size();
    }
    public static class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenTV, txtNamSinh, txtMaTV;
        ImageView ivdelete;
        CardView CardViewThanhVien;
        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            ivdelete = itemView.findViewById(R.id.ivdelete);
            txtNamSinh = itemView.findViewById(R.id.txtNamSinh);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            CardViewThanhVien = itemView.findViewById(R.id.CardViewThanhVien);
        }
    }
    private void reloadData(){
        listtv.clear();
        listtv = thanhVienDAO.getAll();
        notifyDataSetChanged();
    }
    public void Capnhatthanhvien(ThanhVien tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_capnhatthanhvien, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText edtTenTV = view.findViewById(R.id.edtTentvCN);
        EditText edtNamSinhTV = view.findViewById(R.id.edtNamSinhTVCN);
        Button btnXacnhan = view.findViewById(R.id.btnXacnhancntv);
        Button btnHuy = view.findViewById(R.id.btnHuycntv);

        edtTenTV.setText(tv.getHOTEN());
        edtNamSinhTV.setText(tv.getNAMSINH());

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tentv = edtTenTV.getText().toString();
                String namsinh = edtNamSinhTV.getText().toString();
                tv.setHOTEN(tentv);
                tv.setNAMSINH(namsinh);
                if (edtTenTV.length()==0 || edtNamSinhTV.length() == 0){
                    Toast.makeText(context, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (thanhVienDAO.updateThanhVien(tv)){
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
                dialog.dismiss();
            }
        });
    }
}

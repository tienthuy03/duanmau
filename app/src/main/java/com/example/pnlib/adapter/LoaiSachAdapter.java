package com.example.pnlib.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
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
import com.example.pnlib.dao.LoaiSachDAO;
import com.example.pnlib.model.LoaiSach;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {
    private ArrayList<LoaiSach> listls;
    private Context context;
    private LoaiSachDAO loaiSachDAO;

    public LoaiSachAdapter(ArrayList<LoaiSach> listls, Context context,LoaiSachDAO loaiSachDAO ) {
        this.listls = listls;
        this.context = context;
        this.loaiSachDAO =loaiSachDAO;
    }

    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.itemloaisach, parent,false);
        LoaiSachViewHolder loaiSachViewHolder = new LoaiSachViewHolder(view);
        return loaiSachViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewHolder holder, int position) {
        LoaiSach loaiSach = listls.get(position);
        loaiSachDAO = new LoaiSachDAO(context);
        holder.txtTenLs.setText("Loại sách: " + loaiSach.getTENLOAI());
        holder.txtMaLS.setText("Mã loại sách:" + loaiSach.getMALOAI());

        holder.CardViewLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CapnhatLS(loaiSach);
                Log.d("ls",loaiSach.toString());
            }
        });

        holder.ivdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xoá loại sách " + loaiSach.getTENLOAI() + " này không ? ");
                builder.setPositiveButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setNegativeButton(" đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (loaiSachDAO.deleteLoaisach(loaiSach.getMALOAI())) {
                            Toast.makeText(context, "Xoá Thành Công Loại Sách: " + loaiSach.getTENLOAI(), Toast.LENGTH_SHORT).show();
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
        return listls.size();
    }

    public static class LoaiSachViewHolder extends RecyclerView.ViewHolder {

        TextView txtTenLs, txtMaLS;
        ImageView  ivdelete;
        CardView CardViewLS;
        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenLs =itemView.findViewById(R.id.txtTenLS);
            txtMaLS = itemView.findViewById(R.id.txtMaLS);
            ivdelete = itemView.findViewById(R.id.ivdelete);
            CardViewLS = itemView.findViewById(R.id.CardViewLS);
        }
    }
    private void reloadData(){
        listls.clear();
        listls = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        notifyDataSetChanged();
    }
    private void CapnhatLS (LoaiSach ls){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_capnhatloaisach, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText edtTenLS = view.findViewById(R.id.edtTenloaisach);
        Button btnXacnhan = view.findViewById(R.id.btnXacnhancnls);
        Button btnHuy = view.findViewById(R.id.btnHuycnls);

        edtTenLS.setText(ls.getTENLOAI());

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenls = edtTenLS.getText().toString();
                ls.setTENLOAI(tenls);
                if (loaiSachDAO.updateLoaiSach(ls)){
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

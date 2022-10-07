package com.example.pnlib.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.R;
import com.example.pnlib.model.Top;

import java.util.ArrayList;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder> {
    ArrayList<Top> list;
    Context context;

    public TopAdapter(ArrayList<Top> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.itemtop, parent, false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
        Top top = list.get(position);
        holder.txtTen.setText("Sách: " + top.getTENSACH());
        holder.txtSoLuong.setText("Số lượng: " +top.getSOLUONG());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtSoLuong;
        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
        }
    }
}

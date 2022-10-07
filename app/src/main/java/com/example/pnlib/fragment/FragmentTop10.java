package com.example.pnlib.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.R;
import com.example.pnlib.adapter.TopAdapter;
import com.example.pnlib.dao.PhieuMuonDAO;
import com.example.pnlib.model.Top;

import java.util.ArrayList;


public class FragmentTop10 extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Top> list;
    TopAdapter topAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10, container, false);
        recyclerView = view.findViewById(R.id.recycleTop10);
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = (ArrayList<Top>) phieuMuonDAO.getTop();
        topAdapter = new TopAdapter(list, getContext());
        recyclerView.setAdapter(topAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}

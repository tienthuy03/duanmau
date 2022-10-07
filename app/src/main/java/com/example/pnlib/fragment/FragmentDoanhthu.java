package com.example.pnlib.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pnlib.R;
import com.example.pnlib.dao.PhieuMuonDAO;
import com.example.pnlib.dao.ThongKeDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FragmentDoanhthu extends Fragment {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mYear, mMonth, mDay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanhthu, container, false);
        Button btnTungay = view.findViewById(R.id.btnTungay);
        Button btnDenngay = view.findViewById(R.id.btnDenngay);
        Button btnDoanhthu = view.findViewById(R.id.btnDoanhthu);
        TextView txtDoanhthu = view.findViewById(R.id.txtDoanhthu);
        EditText edtTungay = view.findViewById(R.id.edtTungay);
        EditText edtDenngay = view.findViewById(R.id.edtDenngay);
        btnTungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String ngay = "";
                        String thang = "";
                        if (i2 < 10){
                            ngay = "0" + i2;
                        }else {
                            ngay = String.valueOf(i2);
                        }

                        if ((i1 + 1) < 10){
                            thang = "0" + (i1 + 1);
                        }else {
                            thang = String.valueOf((i1 + 1));
                        }
                        edtTungay.setText(i + "/" + thang + "/" + ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnDenngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String ngay = "";
                        String thang = "";
                        if (i2 < 10){
                            ngay = "0" + i2;
                        }else {
                            ngay = String.valueOf(i2);
                        }

                        if ((i1 + 1) < 10){
                            thang = "0" + (i1 + 1);
                        }else {
                            thang = String.valueOf((i1 + 1));
                        }
                        edtDenngay.setText(i + "/" + thang + "/" + ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        btnDoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                String ngaybatdau = edtTungay.getText().toString();
                String ngayketthuc = edtDenngay.getText().toString();
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getContext());
                txtDoanhthu.setText("Doanh thu: " + phieuMuonDAO.getDoanhThu(ngaybatdau, ngayketthuc)+ "VND");
            }
        });
        return view;
    }

}

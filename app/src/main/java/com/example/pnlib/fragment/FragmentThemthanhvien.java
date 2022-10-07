package com.example.pnlib.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.example.pnlib.R;
import com.example.pnlib.dao.ThuThuDAO;
import com.example.pnlib.model.ThuThu;


public class FragmentThemthanhvien extends Fragment {
    private ThuThuDAO thuThuDAO;
    private EditText edtuser, edtTen, edtpass, edtrepass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgament_themthuthu, container, false);
        thuThuDAO = new ThuThuDAO(getContext());
        edtuser = view.findViewById(R.id.edtUsertv);
        edtTen = view.findViewById(R.id.edtTentv);
        edtpass = view.findViewById(R.id.edtPasstv);
        edtrepass = view.findViewById(R.id.edtRepasstv);
        Button btnXacnhan = view.findViewById(R.id.btnXnthemtv);
        Button btnHuy = view.findViewById(R.id.btnHuythemtv);

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThuThu thuThu = new ThuThu();
                thuThu.setMATT(edtuser.getText().toString());
                thuThu.setHOTEN(edtTen.getText().toString());
                thuThu.setMATKHAU(edtpass.getText().toString());
                if (valiDate() > 0) {
                    if (thuThuDAO.insertThuThu(thuThu) > 0) {
                        Toast.makeText(getContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        edtuser.setText("");
                        edtTen.setText("");
                        edtpass.setText("");
                        edtrepass.setText("");
                      } else {
                        Toast.makeText(getContext(), "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                      }
                  }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtuser.setText("");
                edtTen.setText("");
                edtpass.setText("");
                edtrepass.setText("");
            }
        });
        return view;
    }

    private int valiDate() {
        int check = 1;
        if (edtuser.getText().length() == 0 || edtTen.getText().length() == 0 || edtpass.getText().length() == 0 || edtrepass.getText().length() == 0) {
            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edtpass.getText().toString();
            String passRepeat = edtrepass.getText().toString();
            if (!pass.equals(passRepeat)) {
                Toast.makeText(getContext(), "Mật khẩu lặp lại không đúng", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}

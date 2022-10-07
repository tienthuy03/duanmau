package com.example.pnlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pnlib.dao.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser, edtPass;
    Button btnLogin;
    CheckBox chkRemember;
    String strUser, strPass;
    ThuThuDAO thuThuDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Đăng Nhập");
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnLogin =findViewById(R.id.btnLogin);
        chkRemember = findViewById(R.id.chkRemember);
        thuThuDAO = new ThuThuDAO(LoginActivity.this);



        // đọc user va pass trong sharedPrefernences
        SharedPreferences sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
         String username = sharedPreferences.getString("User","");
        String pass = sharedPreferences.getString("Pass","");
        Boolean remember = sharedPreferences.getBoolean("Remember",false);
        edtUser.setText(username);
        edtPass.setText(pass);
        chkRemember.setChecked(remember);

        //button login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ktLogin();
            }
        });
    }
    //hàm ghi nhớ mật khẩu
    public void remember (String user, String pass, boolean check){
        SharedPreferences sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // kiểm tra điều kiện
        if(!check){
            // Xoá tình trạng lưu trữ trước đó
            editor.clear();
        }else {
            //Lưu dữ liệu
            editor.putString("User", user);
            editor.putString("Pass", pass);
            editor.putBoolean("Remember", check);
        }
        //Lưu lại dữ liệu
        editor.commit();
    }
    // kiểm tra login
    public void ktLogin(){
        strUser = edtUser.getText().toString();
        strPass = edtPass.getText().toString();
        if(strUser.isEmpty() || strPass.isEmpty()){
            Toast.makeText(this, "Tên đăng nhập và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
        }else {
            if(thuThuDAO.checkLogin(strUser, strPass) > 0) {
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                remember(strUser, strPass, chkRemember.isChecked());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("User", strUser);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
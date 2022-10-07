package com.example.pnlib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pnlib.fragment.FragmentDoanhthu;
import com.example.pnlib.fragment.FragmentDoimatkhau;
import com.example.pnlib.fragment.FragmentQLloaisach;
import com.example.pnlib.fragment.FragmentQLphieumuon;
import com.example.pnlib.fragment.FragmentQLsach;
import com.example.pnlib.fragment.FragmentQLthanhvien;
import com.example.pnlib.fragment.FragmentThemthanhvien;
import com.example.pnlib.fragment.FragmentTop10;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    private View headerLayout;
    TextView txtUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.themnd).setVisible(false);
        headerLayout=navigationView.getHeaderView(0);
        txtUser=headerLayout.findViewById(R.id.txtUser);
        Intent intent=getIntent();
        String user =intent.getStringExtra("User");
        txtUser.setText("Welcome "+user+"!");
        if (user.equalsIgnoreCase("admin")){
            navigationView.getMenu().findItem(R.id.themnd).setVisible(true);
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.qlpm:
                        fragment = new FragmentQLphieumuon();
                        break;
                    case R.id.qlls:
                        fragment = new FragmentQLloaisach();
                        break;
                    case R.id.qls:
                        fragment = new FragmentQLsach();
                        break;
                    case R.id.qltv:
                        fragment = new FragmentQLthanhvien();
                        break;
                    case R.id.top10:
                        fragment = new FragmentTop10();
                        break;
                    case R.id.doanhthu:
                        fragment = new FragmentDoanhthu();
                        break;
                    case R.id.themnd:
                        fragment = new FragmentThemthanhvien();
                        break;
                    case R.id.doimk:
                        fragment = new FragmentDoimatkhau();
                        break;
                    case R.id.dx:
                        fragment = new FragmentQLphieumuon();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có chắc chắn muốn thoát khỏi ứng dụng ?");
                        builder.setPositiveButton("Không đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        break;
                    default:
                        fragment = new FragmentQLphieumuon();
                        break;
                }
                manager.beginTransaction()
                        .replace(R.id.maincontent, fragment)
                        .commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle(item.getTitle());
                return false;
            }
        });
        FragmentQLphieumuon qLphieumuon = new FragmentQLphieumuon();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.maincontent, qLphieumuon)
                .commit();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home);
        drawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }
}
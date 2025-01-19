package com.example.pma1011_quanlynhatro_nhom2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pma1011_quanlynhatro_nhom2.fragments.TrangChuFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;




public class MainActivity extends AppCompatActivity {
    String TAG = "zzzzzzzzzzzzzz";
    private DrawerLayout drawer;
    private Toolbar toolBar;
    private View headerView;
    private NavigationView nvView;
    private TextView txtUser;
    FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ánh xạ
        drawer = findViewById(R.id.drawer_layout);
        toolBar = findViewById(R.id.toolBar);
        nvView = findViewById(R.id.nvView);
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        //Set toolbar
        toolBar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        getSupportActionBar().setTitle("Trang chủ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Đọc thông tin người dùng từ SharedPreferences
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        boolean ghiNho = pref.getBoolean("ghinho", false);
        int userID = pref.getInt("userID", -1);
        int vaiTro = pref.getInt("vaiTro", -1);
        String name = pref.getString("tk", null);

        if (!ghiNho) {
            Intent intent = getIntent();
            userID = intent.getIntExtra("userID", -1);
            vaiTro = intent.getIntExtra("vaiTro", -1);
            name = intent.getStringExtra("name");
        }

        // Hiển thị tên người dùng
        headerView = nvView.getHeaderView(0);
        txtUser = headerView.findViewById(R.id.txtUser);
        if (name != null) {
            txtUser.setText("Xin chào " + name + "!");
        } else {
            txtUser.setText("Xin chào Khách!");
        }


        //Hiện thị chức năng theo vai trò của người dùng
        // 0: người dùng, người thuê
        // 1: Chủ trọ, người cho thuê phòng
        // 2: Admin hệ thống
        if(vaiTro == 2){
            nvView.getMenu().findItem(R.id.nav_Admin).setVisible(true);
            nvView.getMenu().findItem(R.id.nav_login_prompt).setVisible(false);
            nvView.getMenu().findItem(R.id.nav_login).setVisible(false);
            nvView.getMenu().findItem(R.id.nav_User).setVisible(true);
            nvView.getMenu().findItem(R.id.nav_HoTro).setVisible(false);
            nvView.getMenu().findItem(R.id.nav_DoiMK).setVisible(false);
        } else if(vaiTro == 1){
            nvView.getMenu().findItem(R.id.nav_QL).setVisible(true);
            nvView.getMenu().findItem(R.id.nav_User).setVisible(true);
            nvView.getMenu().findItem(R.id.nav_login_prompt).setVisible(false);
            nvView.getMenu().findItem(R.id.nav_login).setVisible(false);
        } else if(vaiTro == 0){
            nvView.getMenu().findItem(R.id.nav_User).setVisible(true);
            nvView.getMenu().findItem(R.id.nav_login_prompt).setVisible(false);
            nvView.getMenu().findItem(R.id.nav_login).setVisible(false);
            nvView.getMenu().findItem(R.id.nav_HopDong).setVisible(true);
            nvView.getMenu().findItem(R.id.nav_ThanhToan).setVisible(true);
        } else{
            nvView.getMenu().findItem(R.id.nav_login_prompt).setVisible(true);
            nvView.getMenu().findItem(R.id.nav_login).setVisible(true);
        }


        //Navigation drawer
        fragmentManager = getSupportFragmentManager();
        //Home screen
        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              if(item.getItemId() == R.id.nav_DangXuat){
                    // Xóa dữ liệu trong SharedPreferences khi người dùng đăng xuất
                    SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();  // Xóa tất cả dữ liệu trong SharedPreferences
                    editor.apply();  // Áp dụng thay đổi

                    // Chuyển hướng về màn hình đăng nhập
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();  // Đóng MainActivity để người dùng không quay lại trang này
                } else if(item.getItemId() == R.id.nav_login){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                drawer.closeDrawers(); //when clicked then close
                return true;
            }
        });

        // Bottom Navigation Item Selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home) {
                    setTitleAndFragment("Trang chủ", new TrangChuFragment());
                }
                return true;
            }
        });
        // Set the default selected item for BottomNavigationView
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setTitleAndFragment(String title, Fragment fragment) {
        getSupportActionBar().setTitle(title);
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }






    //Xóa thông tin user khi user thoát khỏi ứng dụng mà không bấm ghi nhớ
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        boolean ghiNho = pref.getBoolean("ghinho", false);

        // Nếu không ghi nhớ tài khoản, xóa toàn bộ thông tin
        if (!ghiNho) {
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();
        }
    }






}
package com.example.pma1011_quanlynhatro_nhom2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        // Chuyển đến màn hình đăng nhập sau 3 giây
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Khởi động màn hình đăng nhập
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                // Thêm dòng này sau khi gọi startActivity trong WelcomeActivity.java
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                finish(); // Đóng WelcomeActivity để không quay lại được
            }
        }, 3000); // 3000ms = 3 giây

    }
}
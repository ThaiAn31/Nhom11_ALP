package com.example.alp_coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profile extends AppCompatActivity {
    Button menuBtn,profileBtn,newsBtn,logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        menuBtn = (Button) findViewById(R.id.menuBtn);
        profileBtn = (Button) findViewById(R.id.profileBtn);
        newsBtn = (Button) findViewById(R.id.newsBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    openLoginActivity();
            }
        });
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileActivity();
            }
        });
        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewsActivity();
            }
        });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, GD_DangNhap.class);
        startActivity(intent);
    }

    private void openNewsActivity() {
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
    }


    private void openProfileActivity() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    private void openMenuActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
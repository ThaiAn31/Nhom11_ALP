package com.example.alp_coffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alp_coffee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GD_DangNhap extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnForgotPassword;
    private Button btnSignUp;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_dang_nhap);
        txtEmail = findViewById(R.id.txtUserName_Login);
        txtPassword = findViewById(R.id.txtPassword_Login);
        btnLogin = findViewById(R.id.btn_Login);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnSignUp = findViewById(R.id.btnSignUp_Login);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GD_DangNhap.this, GD_DangKy.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtEmail.getText().length() == 0) {
                    txtEmail.setError("Nhập email của bạn !!");
                } else if (txtPassword.getText().length() == 0) {
                    txtPassword.setError("Nhap mat khau cua ban !!");
                } else {
                    login();
                }
            }
        });
    }


    private void login() {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        btnLogin.setEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(GD_DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    nextMain();
                } else {
                    Toast.makeText(GD_DangNhap.this, "email or password khong dung!!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void nextMain() {
        Intent intent = new Intent(GD_DangNhap.this, MainActivity.class);
        GD_DangNhap.this.startActivity(intent);
    }


}

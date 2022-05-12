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
import com.example.alp_coffee.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GD_DangKy extends AppCompatActivity {
    private EditText txtUsername_SignUp;
    private EditText txtPassword_SignUp;
    private EditText txtConfirmpassword;
    private EditText txtPhonenumber;
    private EditText txtEmail;
    private EditText txtAddress;
    private Button btnSignUp;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("User");
    private FirebaseAuth mAuth;

    public GD_DangKy() {
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_dang_ky);
        txtUsername_SignUp = findViewById(R.id.txtUsername_SignUp);
        txtPassword_SignUp = findViewById(R.id.txtPassword_SignUp);
        txtConfirmpassword = findViewById(R.id.txtConfirmPassword);
        txtPhonenumber = findViewById(R.id.txtPhonenumber);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        btnSignUp = findViewById(R.id.btnSignUp);;
        Button btnLogin_Signup = findViewById(R.id.btnLogin_SignUp);
        btnLogin_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(GD_DangKy.this, GD_DangNhap.class);
                startActivity(intent1);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taoTk();
            }
        });
    }
    private void taoTk(){
        String username = txtUsername_SignUp.getText().toString();
        String password = txtPassword_SignUp.getText().toString();
        String confirmpassword = txtConfirmpassword.getText().toString();
        String email = txtEmail.getText().toString();
        String phonenumber = txtPhonenumber.getText().toString();
        String address = txtAddress.getText().toString();
        if (username.length() == 0) {
            btnSignUp.setEnabled(true);
            txtUsername_SignUp.setError("Nhập họ và tên của bạn!!");
        } else if (email.length() == 0) {
            btnSignUp.setEnabled(true);
            txtEmail.setError("Nhập vào email của bạn!!");
        } else if (phonenumber.length() == 0) {
            btnSignUp.setEnabled(true);
            txtPhonenumber.setError("Nhập vào số điện thoại của bạn!!");
        } else if (address.length() == 0) {
            btnSignUp.setEnabled(true);
            txtAddress.setError("Nhập vào địa chỉ của bạn!!");
        } else if (password.length() == 0) {
            btnSignUp.setEnabled(true);
            txtPassword_SignUp.setError("Nhập vào mật khẩu!!");
        } else if (confirmpassword.length() == 0) {
            btnSignUp.setEnabled(true);
            txtConfirmpassword.setError("Nhập lại mật khẩu của bạn");
        } else if (password.equals(confirmpassword)) {
            if (password.length() < 6) {
                txtPassword_SignUp.setError("Mật khẩu phải lớn hơn hoặc bằng 6 ký tự");
                txtConfirmpassword.setError("Mật khẩu phải lớn hơn hoặc bằng 6 ký tự");
            } else {
               User user = new User(username, password, confirmpassword ,phonenumber ,email, address);
                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email, password)

                        .addOnCompleteListener(GD_DangKy.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    btnSignUp.setEnabled(true);
                                    myRef.child(phonenumber).setValue(user);
                                    Toast.makeText(GD_DangKy.this,
                                            "Đăng ký tài khoản thành công. Vui lòng đăng nhập để gọi món !!",
                                            Toast.LENGTH_SHORT).show();
                                    nextLogin();

                                } else {
                                    btnSignUp.setEnabled(true);
                                    Toast.makeText(GD_DangKy.this,
                                            "Đăng ký thất bại!!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }


        } else {
            btnSignUp.setEnabled(true);
            Toast.makeText(GD_DangKy.this,
                    "Mật khẩu nhập lại không khớp !!",
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void nextLogin() {
        Intent intent = new Intent(GD_DangKy.this, GD_DangNhap.class);
        GD_DangKy.this.startActivity(intent);
    }

}







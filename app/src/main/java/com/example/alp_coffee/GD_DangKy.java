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
            txtUsername_SignUp.setError("Nh???p h??? v?? t??n c???a b???n!!");
        } else if (email.length() == 0) {
            btnSignUp.setEnabled(true);
            txtEmail.setError("Nh???p v??o email c???a b???n!!");
        } else if (phonenumber.length() == 0) {
            btnSignUp.setEnabled(true);
            txtPhonenumber.setError("Nh???p v??o s??? ??i???n tho???i c???a b???n!!");
        } else if (address.length() == 0) {
            btnSignUp.setEnabled(true);
            txtAddress.setError("Nh???p v??o ?????a ch??? c???a b???n!!");
        } else if (password.length() == 0) {
            btnSignUp.setEnabled(true);
            txtPassword_SignUp.setError("Nh???p v??o m???t kh???u!!");
        } else if (confirmpassword.length() == 0) {
            btnSignUp.setEnabled(true);
            txtConfirmpassword.setError("Nh???p l???i m???t kh???u c???a b???n");
        } else if (password.equals(confirmpassword)) {
            if (password.length() < 6) {
                txtPassword_SignUp.setError("M???t kh???u ph???i l???n h??n ho???c b???ng 6 k?? t???");
                txtConfirmpassword.setError("M???t kh???u ph???i l???n h??n ho???c b???ng 6 k?? t???");
            } else {

                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email, password)

                        .addOnCompleteListener(GD_DangKy.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String id = mAuth.getUid();
                                    User user = new User(id,username, password, confirmpassword ,phonenumber ,email, address);
                                    btnSignUp.setEnabled(true);
                                    myRef.child(id).setValue(user);
                                    Toast.makeText(GD_DangKy.this,
                                            "????ng k?? t??i kho???n th??nh c??ng. Vui l??ng ????ng nh???p ????? g???i m??n !!",
                                            Toast.LENGTH_SHORT).show();
                                    nextLogin();

                                } else {
                                    btnSignUp.setEnabled(true);
                                    Toast.makeText(GD_DangKy.this,
                                            "????ng k?? th???t b???i!!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }


        } else {
            btnSignUp.setEnabled(true);
            Toast.makeText(GD_DangKy.this,
                    "M???t kh???u nh???p l???i kh??ng kh???p !!",
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void nextLogin() {
        Intent intent = new Intent(GD_DangKy.this, GD_DangNhap.class);
        GD_DangKy.this.startActivity(intent);
    }

}







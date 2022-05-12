package com.example.alp_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    Button menuBtn,profileBtn,newsBtn,logoutBtn;
    TextView nameV, phoneV, emailV;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        menuBtn = (Button) findViewById(R.id.menuBtn);
        profileBtn = (Button) findViewById(R.id.profileBtn);
        newsBtn = (Button) findViewById(R.id.newsBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        nameV = findViewById(R.id.Name);
        phoneV = findViewById(R.id.Phone);
        emailV = findViewById(R.id.Email);
        Intent intent = getIntent();
        String data = intent.getStringExtra("key1");
        loadData();

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

    private void loadData(){
        DatabaseReference myRef = database.getReference("User/"+fuser.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                nameV.setText(user.getUserName());
                phoneV.setText(user.getPhoneNumber());
                emailV.setText(user.getEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Get data failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
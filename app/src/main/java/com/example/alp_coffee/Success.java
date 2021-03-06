package com.example.alp_coffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class Success extends AppCompatActivity {
    Button btnOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        btnOk = findViewById(R.id.button6);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoanThanh();
            }
        });

    }

    void hoanThanh() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference mRef1 = database1.getReference("ram/" + id_user);
        mRef1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    String id_Order = UUID.randomUUID().toString();
                    Ram ram = new Ram(new Order(id_Order), new User(id_user));
                    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                    DatabaseReference mRef1 = database1.getReference("ram");
                    mRef1.child(id_user).setValue(ram).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                removeOrderDetail(id_Order);
                            }
                        }
                    });
                }
            }
        });
    }

    void removeBill(String id_bill) {
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference mRef1 = database1.getReference("orderDetail/" + id_bill);
        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderDetail orderDetail = snapshot.getValue(OrderDetail.class);
                if (orderDetail == null) {
                    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                    DatabaseReference mRef2 = database1.getReference("order/" + id_bill);
                    mRef2.removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void removeOrderDetail(String id_bill) {
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference mRef1 = database1.getReference("orderDetail/" + id_bill);
        mRef1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    removeBill(id_bill);
                    Intent intent = new Intent(Success.this, MainActivity.class);
                   // intent.putExtra("key", "0");
                    startActivity(intent);
                }
            }
        });
    }
}

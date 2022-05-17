package com.example.alp_coffee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {
    ImageButton previousBtn;
    Button btnTT;
    RecyclerView mRecyclerView;
    TextView tvTien1, tvTienShip, tvTTT;
    ArrayList<OrderDetail> arrayList = new ArrayList<>();
    PaymentAdapter paymentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mRecyclerView = findViewById(R.id.rcVP);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Payment.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Payment.this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        paymentAdapter = new PaymentAdapter(arrayList, this, new PaymentAdapter.onclick() {
            @Override
            public void add(OrderDetail orderDetail) {
                removeCoffee(orderDetail);
            }
        });
        mRecyclerView.setAdapter(paymentAdapter);
        previousBtn = (ImageButton) findViewById(R.id.previousBtn);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });
        tvTien1 = findViewById(R.id.textView15);
        tvTienShip = findViewById(R.id.textView17);
        tvTienShip.setText("20000" + "đ");
        tvTTT = findViewById(R.id.textView19);
        getPayment();
        setTinhTien();
        btnTT = findViewById(R.id.btnTT);
        btnTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, Success.class);
                startActivity(intent);
            }
        });
        checkPayMent();
    }

    private void getPayment() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ram/" + id_user);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ram ram = snapshot.getValue(Ram.class);
                if (ram != null) {
                    String id_Order = ram.getId_Order().getId();
                    getItemPayment(id_Order);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getItemPayment(String id_Order) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("orderDetail/" + id_Order);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderDetail orderDetail = dataSnapshot.getValue(OrderDetail.class);
                    arrayList.add(orderDetail);
                }
                paymentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openMenuActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void setTinhTien() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("ram/" + id_user);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ram ram = snapshot.getValue(Ram.class);
                if (ram != null) {
                    tinhTien(ram.getId_Order().getId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void removeCoffee(OrderDetail orderDetail) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("orderDetail");
        int soL1 = orderDetail.getSoLuong();
        if (soL1 >= 1) {
            int soL2 = soL1 - 1;
            myRef.child(orderDetail.getId_Order().getId()).child(orderDetail.getId_Coffee().getId()).child("soLuong").setValue(soL2);
            if (soL2 < 1) {
                myRef.child(orderDetail.getId_Order().getId()).child(orderDetail.getId_Coffee().getId()).removeValue();
            }
        }
        setTinhTien();
    }

    void tinhTien(String id_Bill) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("orderDetail/" + id_Bill);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double ship = 20000;
                double tienFood = 0;
                double tong = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderDetail orderDetail = dataSnapshot.getValue(OrderDetail.class);
                    String aa = String.valueOf(orderDetail.getSoLuong());
                    tienFood = (Double.parseDouble(aa) * orderDetail.getId_Coffee().getPrice()) + tienFood;
                    tong = tienFood + ship;
                }
                tvTien1.setText(String.valueOf(tienFood) + "đ");
                tvTTT.setText(String.valueOf(tong) + "đ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Payment.this, "load data failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void checkPayMent() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("ram/" + id_user);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ram ram = snapshot.getValue(Ram.class);
                if (ram != null) {
                    setPayUp(ram.getId_Order().getId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void setPayUp(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("orderDetail/" + id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderDetail orderDetail = snapshot.getValue(OrderDetail.class);
                if (orderDetail == null) {
                    btnTT.setEnabled(false);
                } else
                    btnTT.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
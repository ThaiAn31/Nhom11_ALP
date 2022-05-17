package com.example.alp_coffee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CoffeeDetail extends AppCompatActivity {
    Button toCartBtn, btnCong, btnTru;
    TextView Name, Price, tvSL;
    ImageView Image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);
        Name = (TextView) findViewById(R.id.textViewName);
        Price = (TextView) findViewById(R.id.textViewPrice);
        Image = (ImageView) findViewById(R.id.imageViewCoffee);
        toCartBtn = (Button) findViewById(R.id.toCartBtn);
        btnCong = findViewById(R.id.button3);
        tvSL = findViewById(R.id.textView13);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "Null!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Coffee coffee = (Coffee) bundle.get("Coffee_Detail");
        Glide.with(CoffeeDetail.this).load(coffee.getImage()).into(Image);
        Name.setText(coffee.getName());
        Price.setText(String.valueOf(coffee.getPrice()));
        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soLuong = tvSL.getText().toString();
                int soLuong2 = Integer.parseInt(soLuong) + 1;
                tvSL.setText(String.valueOf(soLuong2));
            }
        });
        btnTru = findViewById(R.id.button2);
        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soLuong = tvSL.getText().toString();
                int soLuong1 = Integer.parseInt(soLuong) - 1;
                if (soLuong1 <= 0) {
                    tvSL.setText("1");
                } else
                    tvSL.setText(String.valueOf(soLuong1));
            }
        });
        toCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIDOrder(coffee.getId());
                Toast.makeText(CoffeeDetail.this, "Đã thêm vào giỏ hàng!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CoffeeDetail.this, MainActivity.class);
                startActivity(intent);

            }
        });


    }

    void getIDOrder(String id_coffee) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("ram/" + id_user);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ram ram = snapshot.getValue(Ram.class);
                if (ram != null) {
                    createBill(ram.getId_Order().getId());
                    createOrderDetail(ram.getId_Order().getId(), id_coffee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void createBill(String id_bill) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("order");
        Order order = new Order(new User(id_user), id_bill);
        mRef.child(id_bill).setValue(order);
    }

    void createOrderDetail(String id_order, String id_coffee) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("orderDetail");
        String name = Name.getText().toString();
        double price = Double.parseDouble(Price.getText().toString());
        int soLuong = Integer.parseInt(tvSL.getText().toString());
        OrderDetail orderDetail = new OrderDetail(new Order(id_order), new Coffee(name, price, id_coffee), soLuong);
        mRef.child(id_order).child(id_coffee).setValue(orderDetail);
    }


}
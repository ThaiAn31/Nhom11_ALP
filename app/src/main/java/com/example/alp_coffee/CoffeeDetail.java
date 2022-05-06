package com.example.alp_coffee;

import androidx.annotation.NonNull;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CoffeeDetail extends AppCompatActivity {
    Button toCartBtn;
    Payment payment;

    RecyclerView rvC;
    String coffeeId = "";
    TextView Name, Price;
    ImageView Image;
    LinearLayoutManager myLYM;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    FirebaseRecyclerAdapter<Coffee, ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Coffee> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);
        Name = (TextView) findViewById(R.id.textViewName);
        Price = (TextView) findViewById(R.id.textViewPrice);
        Image = (ImageView) findViewById(R.id.imageViewCoffee);
        toCartBtn = (Button) findViewById(R.id.toCartBtn);
        toCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openPurchaseActivity();

            }
        });
        //ket noi firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Coffee");
        if(getIntent() != null){
            coffeeId = getIntent().getStringExtra("coffeeId");

        }if(!coffeeId.isEmpty() && coffeeId != null){
            loadCoffeeDetail(coffeeId);
        }

    }

    private void loadCoffeeDetail(String coffeeId) {
//        c.child(foodId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                currentfood=snapshot.getValue(Food.class);
//
//                //Set Image
//                Picasso.with(getBaseContext()).load(currentfood.getImage()).into(imgFood);
//                txtPriceDetail.setText(currentfood.getPrice());
//                txtPrice.setText(currentfood.getPrice());
//                txtName.setText(currentfood.getName());
//                //Lấy đc id nhưng chưa biết map qua category như nào 🙁
//                txtCate.setText(currentfood.getMenuId());
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });
    }


    private void openPurchaseActivity() {

        Intent intent = new Intent(this, Payment.class);
        startActivity(intent);

    }


}
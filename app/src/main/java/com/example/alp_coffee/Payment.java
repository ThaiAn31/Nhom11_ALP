package com.example.alp_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {
    ImageButton previousBtn;
    RecyclerView mRecyclerView;
    ArrayList <Coffee> arrayList;
    CoffeeAdapter coffeeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        previousBtn = (ImageButton) findViewById(R.id.previousBtn);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });
        arrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.rcVP);
        coffeeAdapter = new CoffeeAdapter(arrayList, this, new CoffeeAdapter.click() {
            @Override
            public void itemClick(Coffee coffee) {

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(coffeeAdapter);


        getListCoffee();
    }
    private void getListCoffee() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Coffee");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Coffee coffee = dataSnapshot.getValue(Coffee.class);
                    arrayList.add(coffee);
                }
                coffeeAdapter.notifyDataSetChanged();
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
}
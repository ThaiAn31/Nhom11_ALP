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
    Button toCartBtn;
    Payment payment;
    Coffee coffee;
    RecyclerView rvC;
    String coffeeId = "";
    TextView Name, Price;
    ImageView Image;


    LinearLayoutManager myLYM;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    //    FirebaseRecyclerAdapter<Coffee, ViewHolder> firebaseRecyclerAdapter;
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

                onClickAddDrink(coffee);

            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "Null!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Coffee coffee = (Coffee) bundle.get("Coffee_Detail");
        Glide.with(CoffeeDetail.this).load(coffee.getImage()).into(Image);
        Name.setText(coffee.getName());
        Price.setText(String.valueOf(coffee.getPrice()));

        //ket noi firebase
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference("Coffee");
//        if(getIntent() != null){
//            coffeeId = getIntent().getStringExtra("coffeeId");
//
//        }if(!coffeeId.isEmpty() && coffeeId != null){
//            loadCoffeeDetail(coffeeId);
//        }

    }

    private void loadCoffeeDetail(String coffeeId) {
        mDatabaseReference.child(coffeeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                coffee=snapshot.getValue(Coffee.class);

                //Set Image
//                Picasso.with(getBaseContext()).load(coffee.getImage()).into(Image);
                Picasso.get().load(coffee.getImage()).into(Image);
                Price.setText(coffee.getPrice());
                Name.setText(coffee.getName());


            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    private void onClickAddDrink(Coffee coffee) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Coffee");

        String pathObject = String.valueOf(coffee.getName());
        myRef.child(pathObject).setValue(coffee, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(CoffeeDetail.this, "Add success", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addDrink(){

    }


}
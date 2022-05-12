package com.example.alp_coffee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button menuBtn, profileBtn, newsBtn;
    TextView tvName, tvPrice;
    ImageButton purchaseBtn;
    RecyclerView mRecyclerView;
    EditText txtSearch;
    ArrayList <Coffee> arrayList;
    CoffeeAdapter coffeeAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database

        menuBtn = (Button) findViewById(R.id.menuBtn);
        profileBtn = (Button) findViewById(R.id.profileBtn);
        newsBtn = (Button) findViewById(R.id.newsBtn);
        purchaseBtn = (ImageButton) findViewById(R.id.purchaseBtn);
        tvName = (TextView) findViewById(R.id.textViewName);
        tvPrice = (TextView) findViewById(R.id.textViewPrice);
        txtSearch = findViewById(R.id.Search);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    search(editable.toString());
                } else {
                    search("");
                }

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
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPurchaseActivity();
            }
        });
        arrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.rvC);
        coffeeAdapter = new CoffeeAdapter(arrayList, this, new CoffeeAdapter.click() {
            @Override
            public void itemClick(Coffee coffee) {
                openSearchActivity();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(coffeeAdapter);


        getListCoffee();


    }



    private void openSearchActivity() {
        Intent intent = new Intent(this, CoffeeDetail.class);
        startActivity(intent);
    }

    private void openPurchaseActivity() {
        Intent intent = new Intent(this, Payment.class);
        startActivity(intent);
    }

    private void openNewsActivity() {
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
    }


    private void openProfileActivity() {
        Intent intent1 = new Intent(this, Profile.class);
        Intent intent2 = getIntent();
        String data01 = intent2.getStringExtra("key");
        intent1.putExtra("key1", data01);
        startActivity(intent1);

    }

    private void openMenuActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
//    private void openPurchaseCoffeeActivity() {
//        Intent intent = new Intent(this, Payment.class);
//        startActivity(intent);
//    }

    private void showData(){
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference("Coffee");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void search(String s) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Coffee");
        Query query = databaseReference.orderByChild("name").startAt(s).endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    arrayList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        Coffee alpcoffee = dss.getValue(Coffee.class);
                        arrayList.add(alpcoffee);
                    }
                    CoffeeAdapter coffeeAdapter = new CoffeeAdapter(arrayList, getApplicationContext(), new CoffeeAdapter.click() {
                        @Override
                        public void itemClick(Coffee coffee) {
                            openSearchActivity();
                            onClickAddDrink(coffee);
                        }
                    });
                    mRecyclerView.setAdapter(coffeeAdapter);
                    coffeeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                Toast.makeText(MainActivity.this, "Add success", Toast.LENGTH_SHORT).show();
            }
        });
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

}
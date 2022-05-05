package com.example.alp_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button menuBtn,profileBtn,newsBtn;
    LinearLayoutManager myLYM;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    FirebaseRecyclerAdapter<Coffee, ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Coffee> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database

        menuBtn = (Button) findViewById(R.id.menuBtn);
        profileBtn = (Button) findViewById(R.id.profileBtn);
        newsBtn = (Button) findViewById(R.id.newsBtn);
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




    myLYM = new LinearLayoutManager(this);
    myLYM.setReverseLayout(true);
    myLYM.setStackFromEnd(true);

    mRecyclerView = findViewById(R.id.rvC);
    mFirebaseDatabase = FirebaseDatabase.getInstance();
    mDatabaseReference = mFirebaseDatabase.getReference("Coffee");
    showData();
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


    public void showData() {
        options = new FirebaseRecyclerOptions.Builder<Coffee>().setQuery(mDatabaseReference,Coffee.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Coffee, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Coffee coffee) {
            holder.setDetail(getApplicationContext(),coffee.getName(),coffee.getPrice(),coffee.getImage());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_menu,parent,false);
                ViewHolder viewHolder = new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }
        };
        mRecyclerView.setLayoutManager(myLYM);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }
//    public void buttonProfile(View view){
//        int id = view.getId();
//        Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
//    }
//    public void buttonMenu(View view){
//        Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
//    }
//    public void buttonNews(View view){
//        Toast.makeText(this, "News", Toast.LENGTH_SHORT).show();
//    }
    protected void onStart(){
        super.onStart();
        if(firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
        }
    }

}
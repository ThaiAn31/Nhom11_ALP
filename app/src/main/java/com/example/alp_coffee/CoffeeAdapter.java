package com.example.alp_coffee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHoler> {
    private ArrayList<Coffee> mList;
    private Context ctx;
    private click click;
    public interface click{
        void itemClick(Coffee coffee);
    }

    public CoffeeAdapter(ArrayList<Coffee> mList, Context ctx, CoffeeAdapter.click click) {
        this.mList = mList;
        this.ctx = ctx;
        this.click = click;
    }

    @NonNull
    @Override
    public CoffeeViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_menu, parent, false);

       return new CoffeeViewHoler(view,CoffeeAdapter.this);

    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeViewHoler holder, int position) {
        Coffee alpcoffee = mList.get(position);
        if (alpcoffee == null) {
            return;
        }
        holder.txtName.setText(alpcoffee.getName());
        String price = String.valueOf(alpcoffee.getPrice());
        holder.txtPrice.setText(price);
        Glide.with(ctx).load(mList.get(position).getImage()).into(holder.imageView);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.itemClick(alpcoffee);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }


    public class CoffeeViewHoler extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice;
        ImageView imageView;
        CoffeeAdapter coffeeAdapter;
        View view;




        public CoffeeViewHoler(@NonNull View itemView,CoffeeAdapter coffeeAdapter) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textViewName);
            txtPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageViewCoffee);
            view = itemView.findViewById(R.id.view);
            this.coffeeAdapter = coffeeAdapter;
        }
    }

}
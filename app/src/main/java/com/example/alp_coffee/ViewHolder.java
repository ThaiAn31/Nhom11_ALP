package com.example.alp_coffee;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mview;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mview = itemView;


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });

    }



    private void startActivity(Intent intent) {
    }

    public void setDetail(Context ctx, String name, String price, String image){
        TextView mName = mview.findViewById(R.id.textViewName);
        TextView mPrice = mview.findViewById(R.id.textViewPrice);
        ImageView mImage = mview.findViewById(R.id.imageViewCoffee);


        mName.setText(name);
        mPrice.setText(price);

        Picasso.get().load(image).into(mImage);


    }
    private ViewHolder.ClickListener mClickListener;
    public interface ClickListener{
        void onItemClick(View view,int position);

    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}

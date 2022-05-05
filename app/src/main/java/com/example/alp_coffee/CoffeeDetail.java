package com.example.alp_coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CoffeeDetail extends AppCompatActivity {
    Button toCartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);

        toCartBtn = (Button) findViewById(R.id.toCartBtn);
        toCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPurchaseActivity();
            }
        });

    }
    private void openPurchaseActivity() {
        Intent intent = new Intent(this, Payment.class);
        startActivity(intent);
    }

}
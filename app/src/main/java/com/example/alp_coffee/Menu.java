package com.example.alp_coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    ImageButton addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        addBtn = (ImageButton) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Menu.this, "hêh", Toast.LENGTH_SHORT).show();
                openPurchaseCoffeeActivity();
            }
        });

    }
        private void openPurchaseCoffeeActivity() {
        Intent intent = new Intent(this, Payment.class);
        startActivity(intent);
    }
}
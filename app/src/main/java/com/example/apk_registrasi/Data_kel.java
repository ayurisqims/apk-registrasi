package com.example.apk_registrasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Data_kel extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kel);


        BottomNavigationView bottomNavigationView = findViewById(R.id.button_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_data);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_download:
                        startActivity(new Intent(getApplicationContext()
                            ,Download.class));
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.navigation_data:
                        return false;
                    case R.id.navigation_upload:
                        startActivity(new Intent(getApplicationContext()
                                ,Upload.class));
                        overridePendingTransition(0,0);
                        return false;
                }
                return false;
            }
        });

        Button detailAnggota = findViewById(R.id.btnDetail);
        detailAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Data_kel.this, Data_anggota.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buttom_logout, menu);
        return true;
    }
}
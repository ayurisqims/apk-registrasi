package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Data_anggota extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_anggota);

        TextView tambah = findViewById(R.id.btnTambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tambah = new Intent(Data_anggota.this, Regist_anggota.class);
                startActivity(tambah);
            }
        });
    }
}
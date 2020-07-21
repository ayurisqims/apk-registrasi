package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Regist_anggota extends AppCompatActivity {


        //LinearLayout container; // create a scrollView in which you can put all EditTexts
        //static int totalEditTexts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_anggota);

        //container = findViewById(R.id.linier);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.JenisKelamin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinnerr = findViewById(R.id.spinnerBM);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BidangMinat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerr.setAdapter(adapter1);


    }


    // public void clicked (View view) {
        // totalEditTexts++;
        // if (totalEditTexts > 100)
        // return;
        // EditText editText = new EditText(Regist_anggota.this);
        // container.addView(editText);

        // editText.setTag("EditText" + totalEditTexts);
        //}

}
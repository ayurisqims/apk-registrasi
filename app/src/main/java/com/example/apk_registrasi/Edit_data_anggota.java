package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.example.apk_registrasi.R.layout.edit_data_anggota;

public class Edit_data_anggota extends AppCompatActivity {

    private  int position =0, id=0;
    private EditText Nama, NIM, NO, Email, Sosmed, Alamat;
    private String URL_Update_anggota = "http://192.168.31.154:80/api/data_anggota/update/";

    SharedPreferences userPref;
    RequestQueue requestQueue;
    Spinner JenisKelamin, BidangMinat;
    String jenisKelamin, keahlian;
    CheckBox UI, Web, Frontend, AndroidDev, Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edit_data_anggota);

        init();

    }

    private void init() {

        userPref = getApplicationContext().getSharedPreferences("user", Context. MODE_PRIVATE);
        Nama = findViewById(R.id.editNama);
        NIM = findViewById(R.id.editNIM);
        NO = findViewById(R.id.editNO);
        Email = findViewById(R.id.editEmail);
        Alamat = findViewById(R.id.editAlamat);
        Sosmed = findViewById(R.id.editSosmed);
        JenisKelamin = findViewById(R.id.spinner);
        BidangMinat = findViewById(R.id.spinnerKeahlian);
        UI = findViewById(R.id.cbUI);
        Web = findViewById(R.id.cbWeb);
        Frontend = findViewById(R.id.cbFrontend);
        Database = findViewById(R.id.cbDatabase);
        AndroidDev = findViewById(R.id.cbAndroid);
        position = getIntent().getIntExtra("position", 0);
        id = getIntent().getIntExtra("id_kelompok", 0);

        Button simpan = findViewById(R.id.btnSimpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan_data_anggota();
            }
        });

    }

    private void simpan_data_anggota() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Update_anggota, response -> {

            try {


            } catch (Exception e) {

            }
        }, error -> {

        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer" +token);
                Log.i("autolog", "getHeaders: ");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id_kelompok", id+"");
                params.put("nama", Nama.getText().toString());
                params.put("nim", NIM.getText().toString());
                params.put("no_hp", NO.getText().toString());
                params.put("sosmed", Sosmed.getText().toString());
                params.put("jenis_kelamin", jenisKelamin);
                params.put("keahlian", keahlian);
                params.put("email_anggota", Email.getText().toString());
                params.put("alamat", Alamat.getText().toString());

                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void batalEdit(View view) {
        super.onBackPressed();
    }
}
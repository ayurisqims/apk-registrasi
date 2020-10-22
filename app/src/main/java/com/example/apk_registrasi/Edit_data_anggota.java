package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.Utils.Constant;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.example.apk_registrasi.R.layout.edit_data_anggota;

public class Edit_data_anggota extends AppCompatActivity {


    private  int position=0, id_anggota;
    private EditText Nama, Nim, NoHp, Email, Sosmed, Alamat, JenisKelamin, Keahlian;

    SharedPreferences userPref;
    RequestQueue requestQueue;

    CheckBox UI, Web, Frontend, AndroidDev, Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edit_data_anggota);

        init();
    }

    private void init() {

        userPref    = getApplicationContext().getSharedPreferences("user", Context. MODE_PRIVATE);
        Nama        = findViewById(R.id.editNama);
        Nim         = findViewById(R.id.editNIM);
        NoHp        = findViewById(R.id.editNO);
        Email       = findViewById(R.id.editEmail);
        Alamat      = findViewById(R.id.editAlamat);
        Sosmed      = findViewById(R.id.editSosmed);
        JenisKelamin = findViewById(R.id.spinner);
        Keahlian     = findViewById(R.id.spinnerKeahlian);
        UI          = findViewById(R.id.cbUI);
        Web         = findViewById(R.id.cbWeb); 
        Frontend    = findViewById(R.id.cbFrontend);
        Database    = findViewById(R.id.cbDatabase);
        AndroidDev  = findViewById(R.id.cbAndroid);

        position    = getIntent().getIntExtra("position", 0);
        id_anggota  = getIntent().getIntExtra("id", 0);
        Nama.setText(getIntent().getStringExtra("nama"));
        Nim.setText(getIntent().getStringExtra("nim"));
        JenisKelamin.setText(getIntent().getStringExtra("jenis_kelamin"));
        NoHp.setText(getIntent().getStringExtra("no_hp"));
        Email.setText(getIntent().getStringExtra("email_anggota"));
        Sosmed.setText(getIntent().getStringExtra("sosmed"));
        Alamat.setText(getIntent().getStringExtra("alamat"));
        Keahlian.setText(getIntent().getStringExtra("keahlian"));
        Log.i("Edit_data_anggota", "init: ");

        Button simpan = findViewById(R.id.btnSimpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan_data_anggota();
            }
        });

    }

    private void simpan_data_anggota() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_UPDATE_DATA_ANGGOTA, response -> {
            Log.i("Edit_data_anggota", "simpan_data_anggota");

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    Log.i("simpan_data_anggota", "Update Berhasil ");
                    Toast.makeText(Edit_data_anggota.this, "Update Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Edit_data_anggota.this, Data_anggota.class);
                    startActivity(intent);
                } else if(!object.getBoolean("success")){
                    Toast.makeText(Edit_data_anggota.this, "Update Gagal",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }
        }, error -> {}) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer" +token);
                Log.i("Edit_data_anggota", "getHeaders: ");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id_anggota+"");
                params.put("nama", Nama.getText().toString());
                params.put("nim", Nim.getText().toString());
                params.put("jenis_kelamin", JenisKelamin.getText().toString());
                params.put("no_hp", NoHp.getText().toString());
                params.put("email_anggota", Email.getText().toString());
                params.put("sosmed", Sosmed.getText().toString());
                params.put("alamat", Alamat.getText().toString());
                params.put("keahlian", Keahlian.getText().toString());
                Log.i("Edit_data_anggota", "getParams: "+params);

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
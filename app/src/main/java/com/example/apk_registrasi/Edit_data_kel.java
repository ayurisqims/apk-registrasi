package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.Models.Anggota;
import com.example.apk_registrasi.Utils.Constant;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edit_data_kel extends AppCompatActivity {

    private String id_kelompok;
    private TextInputEditText Univ, Fakultas, Prodi, Alamat, Jumlah, NamaKetua, PeriodeMulai, PeriodeAkhir, Kelompok;

    SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_kelompok);

        init();
    }

    private void init() {

        userPref    = getApplicationContext().getSharedPreferences("user", Context. MODE_PRIVATE);
        Univ        = findViewById(R.id.inputUniv);
        Fakultas    = findViewById(R.id.inputFakultas);
        Prodi       = findViewById(R.id.inputProdi);
        Alamat      = findViewById(R.id.inputAlamat);
        Jumlah      = findViewById(R.id.inputJumlah);
        Kelompok    = findViewById(R.id.spinnerKel);
        NamaKetua   = findViewById(R.id.inputNama);
        PeriodeMulai = findViewById(R.id.inputPeriodeM);
        PeriodeAkhir = findViewById(R.id.inputPeriodeA);

        id_kelompok = getIntent().getStringExtra("id");
        Univ.setText(getIntent().getStringExtra("universitas"));
        Fakultas.setText(getIntent().getStringExtra("fakultas"));
        Prodi.setText(getIntent().getStringExtra("prodi"));
        Alamat.setText(getIntent().getStringExtra("alamat_univ"));
        Jumlah.setText(getIntent().getStringExtra("jumlah_anggota"));

        Kelompok.setText(getIntent().getStringExtra("kelompok"));
        NamaKetua.setText(getIntent().getStringExtra("nama_ketua"));
        PeriodeMulai.setText(getIntent().getStringExtra("periode_mulai"));
        PeriodeAkhir.setText(getIntent().getStringExtra("periode_akhir"));

        Button simpan = findViewById(R.id.btnSimpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan_data_kelompok();

            }
        });

        Button batal = findViewById(R.id.btnBatal);
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_data_kel.super.onBackPressed();
            }
        });

    }

    private void simpan_data_kelompok() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_UPDATE_DATA_KELOMPOK, response -> {

            try{
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success")) {
                    Log.i("autolog", "Update Berhasil");
                    Toast.makeText(Edit_data_kel.this, "Update Berhasil ",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Edit_data_kel.this, Data_kel.class);
                    startActivity(intent);
                } else if(!object.getBoolean("success")) {
                    Toast.makeText(Edit_data_kel.this, "Update Gagal!",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){

            }

        }, error -> {}) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer" +token);
                Log.i("autolog", "getHeaders: ");
                return params;
            }

            //          Mengambil nilai parameter yang dikirim dari client ke server
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id_kelompok + "");
                params.put("universitas", Univ.getText().toString().trim());
                params.put("fakultas", Fakultas.getText().toString().trim());
                params.put("prodi", Prodi.getText().toString().trim());
                params.put("alamat_univ", Alamat.getText().toString().trim());
                params.put("kelompok", Kelompok.getText().toString().trim());
                params.put("jumlah_anggota", Jumlah.getText().toString().trim());
                params.put("periode_mulai", PeriodeMulai.getText().toString().trim());
                params.put("periode_akhir", PeriodeAkhir.getText().toString().trim());
                params.put("nama_ketua", NamaKetua.getText().toString().trim());
                Log.i("autolog", "Params" +params);
                return params;
            }
        };
        
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
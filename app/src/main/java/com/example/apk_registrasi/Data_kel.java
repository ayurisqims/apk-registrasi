package com.example.apk_registrasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.Models.Anggota;
import com.example.apk_registrasi.Models.Kelompok;
import com.example.apk_registrasi.Utils.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Data_kel extends Activity {


    TextView Univ,Fakultas, Prodi, Alamat, Jumlah, Kel, PeriodeM, PeriodeA, NamaKetua;

    RequestQueue requestQueue;
    SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kel);

        tampilData();
        init();

//        Menampilkan BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.button_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_data);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_download:
                        startActivity(new Intent(getApplicationContext()
                                , Download.class));
                        overridePendingTransition(0, 0);
                        return false;
                    case R.id.navigation_data:
                        return false;
                    case R.id.navigation_upload:
                        startActivity(new Intent(getApplicationContext()
                                , Upload.class));
                        overridePendingTransition(0, 0);
                        return false;
                }
                return false;
            }
        });
    }

    private void init() {

        userPref    = getApplicationContext().getSharedPreferences("user", Context. MODE_PRIVATE);
        Univ        = findViewById(R.id.txtJwbUniv);
        Fakultas    = findViewById(R.id.txtJwbFakultas);
        Prodi       = findViewById(R.id.txtJwbProdi);
        Alamat      = findViewById(R.id.txtJwbAlamat);
        Jumlah      = findViewById(R.id.txtJwbJumlah);
        Kel         = findViewById(R.id.txtJwbKel);
        PeriodeM    = findViewById(R.id.txtJwbPeriodM);
        PeriodeA    = findViewById(R.id.txtJwbPeriodA);
        NamaKetua   = findViewById(R.id.txtJwbNamaKetua);

        Button tambahAnggota = findViewById(R.id.btnDetail);
        tambahAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Data_kel.this, Data_anggota.class);
                startActivity(intent);
            }
        });

        Button edit = findViewById(R.id.btnEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = userPref.getInt("id_kelompok", 0);
                String universitas = userPref.getString("universitas", "");
                String fakultas = userPref.getString("fakultas", "");
                String prodi = userPref.getString("prodi", "");
                String kelompok = userPref.getString("kelompok", "");
                String alamat_univ = userPref.getString("alamat_univ", "");
                String jumlah_anggota = userPref.getString("jumlah_anggota", "");
                String periode_mulai = userPref.getString("periode_mulai", "");
                String periode_akhir = userPref.getString("periode_akhir", "");
                String nama_ketua = userPref.getString("nama_ketua", "");

                Intent intent = new Intent(Data_kel.this, Edit_data_kel.class);
                intent.putExtra("id", id.toString());
                intent.putExtra("universitas", universitas);
                intent.putExtra("fakultas", fakultas);
                intent.putExtra("kelompok", kelompok);
                intent.putExtra("prodi", prodi);
                intent.putExtra("alamat_univ", alamat_univ);
                intent.putExtra("jumlah_anggota", jumlah_anggota);
                intent.putExtra("periode_mulai", periode_mulai);
                intent.putExtra("periode_akhir", periode_akhir);
                intent.putExtra("nama_ketua", nama_ketua);
               startActivity(intent);
            }
        });

    }

//    Menampilkan data dengan JSONObject dan SharedPreferences
    private void tampilData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_DATA_KELOMPOK, response ->  {

                try {
                    JSONObject jsonobject = new JSONObject(response);

                    JSONArray jsonArray = jsonobject.getJSONArray("data");
                    JSONObject data = jsonArray.getJSONObject(0);
                    Log.i("autolog", "tampilData: Data");

                    String universitas = data.getString("universitas");
                    String fakultas = data.getString("fakultas");
                    String prodi = data.getString("prodi");
                    String alamatUniv = data.getString("alamat_univ");
                    String kelompok = data.getString("kelompok");
                    String jumlahAnggota = data.getString("jumlah_anggota");
                    String periodeM = data.getString("periode_mulai");
                    String periodeA = data.getString("periode_akhir");
                    String namaKetua = data.getString("nama_ketua");

                    Univ.setText(universitas);
                    Fakultas.setText(fakultas);
                    Prodi.setText(prodi);
                    Alamat.setText(alamatUniv);
                    Kel.setText(kelompok);
                    Jumlah.setText(jumlahAnggota);
                    PeriodeM.setText(periodeM);
                    PeriodeA.setText(periodeA);
                    NamaKetua.setText(namaKetua);

                } catch (JSONException e){
                    e.printStackTrace();
                }
        }, error -> {
            }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer" +token);

                return params;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("univeristas", Univ.getText().toString());
                params.put("fakultas", Fakultas.getText().toString());
                params.put("prodi", Prodi.getText().toString());
                params.put("alamat_univ", Alamat.getText().toString());
                params.put("kelompok", Kel.getText().toString());
                params.put("jumlah_anggota", Jumlah.getText().toString());
                params.put("periode_mulai", PeriodeM.getText().toString());
                params.put("periode_akhir", PeriodeA.getText().toString());
                params.put("nama_ketua", NamaKetua.getText().toString());
                return params;
            }

        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

//    Membuat Method Logout
    private void logout() {

    StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_DATA_KELOMPOK, response -> {

        try {
            JSONObject object = new JSONObject(response);
            if (object.getBoolean("success")) {
                SharedPreferences.Editor editor = userPref.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(Data_kel.this, MainActivity.class));
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }, error -> {
        error.printStackTrace();
    }) {

        public Map<String, String> getHeaders() throws AuthFailureError {
            String token = userPref.getString("token", "");
            HashMap<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer" +token);
            return map;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);

}
}
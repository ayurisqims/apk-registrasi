package com.example.apk_registrasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class Data_kel extends Activity {

    private String URL_DataKel = "http://192.168.100.174:80/api/data/";
    TextView Univ,Fakultas, Prodi, Alamat, Jumlah, Kel, PeriodeM, PeriodeA, NamaKetua, Email;
    RequestQueue requestQueue;
    SharedPreferences userPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kel);

        userPref = getApplicationContext().getSharedPreferences("user", Context. MODE_PRIVATE);
        tampilData();
        tombol();

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

    private void tombol() {

        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        Univ = findViewById(R.id.txtJwbUniv);
        Fakultas = findViewById(R.id.txtJwbFakultas);
        Prodi = findViewById(R.id.txtJwbProdi);
        Alamat = findViewById(R.id.txtJwbAlamat);
        Jumlah = findViewById(R.id.txtJwbJumlah);
        Kel = findViewById(R.id.txtJwbKel);
        PeriodeM = findViewById(R.id.txtJwbPeriodM);
        PeriodeA = findViewById(R.id.txtJwbPeriodA);
        NamaKetua = findViewById(R.id.txtJwbNamaKetua);

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
            }
        });

    }

//    Menampilkan data dengan JSONObject dan SharedPreferences
    private void tampilData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DataKel, response ->  {

                try {
                    JSONObject jsonobject = new JSONObject(response);

                    JSONArray jsonArray = jsonobject.getJSONArray("data");
                    JSONObject data = jsonArray.getJSONObject(0);

                    String StrUniv = data.getString("universitas");
                    String StrFakultas = data.getString("fakultas");
                    String StrProdi = data.getString("prodi");
                    String StrAlamat = data.getString("alamat_univ");
                    String StrKel = data.getString("kelompok");
                    String StrJumlah = data.getString("jumlah_anggota");
                    String StrPeriodeM = data.getString("periode_mulai");
                    String StrPeriodeA = data.getString("periode_akhir");
                    String StrNamaKetua = data.getString("nama_ketua");

                    Univ.setText(StrUniv);
                    Fakultas.setText(StrFakultas);
                    Prodi.setText(StrProdi);
                    Alamat.setText(StrAlamat);
                    Kel.setText(StrKel);
                    Jumlah.setText(StrJumlah);
                    PeriodeM.setText(StrPeriodeM);
                    PeriodeA.setText(StrPeriodeA);
                    NamaKetua.setText(StrNamaKetua);


                } catch (JSONException e){
                    e.printStackTrace();
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Data_kel.this, "Gagal", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
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

    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DataKel, response -> {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buttom_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.logout: {

                AlertDialog.Builder alertDialogBuilder = new  AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Apakah anda ingin keluar?");
                alertDialogBuilder.setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                });
                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.show();
            }

        }
        return super.onOptionsItemSelected(item);
    }



}
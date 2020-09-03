package com.example.apk_registrasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data_kel extends Activity {

    TextView Univ,Fakultas, Prodi, Alamat, Jumlah, Kel, PeriodeM, PeriodeA, NK, Email;
    private SharedPreferences sharedPreferences;
    private String URL_DataKel = "http://192.168.43.248:80/api/data/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kel);

        tombol();

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_data);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
//                jsonParse();
//                getData();
            }
        });

        Univ = findViewById(R.id.txtUniv);
        Fakultas = findViewById(R.id.txtFakultas);
        Prodi = findViewById(R.id.txtUniv);
        Alamat = findViewById(R.id.txtFakultas);
        Jumlah = findViewById(R.id.txtUniv);
        Kel = findViewById(R.id.txtFakultas);
        PeriodeM = findViewById(R.id.txtPeriodeM);
        PeriodeA = findViewById(R.id.txtPeriodeA);
        NK = findViewById(R.id.txtUniv);
        Email = findViewById(R.id.txtFakultas);

    }

//    private void init() {
//
//        sharedPreferences = getApplicationContext().getSharedPreferences("data", Regist_kel.MODE_PRIVATE);
//    }

//    private void getData() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DataKel, response -> {
//
//            try {
//                JSONObject object = new JSONObject(response);
//                if (object.getBoolean("success")) {
//                    sharedPreferences = getApplicationContext().getSharedPreferences("data", Regist_kel.MODE_PRIVATE);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> Toast.makeText(Data_kel.this, "Error" +error.toString(),
//                Toast.LENGTH_SHORT).show()) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                String token = sharedPreferences.getString("token", "");
//                HashMap<String, String> params = new HashMap<>();
//                params.put("Authorization", "Bearer" +token);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }


}
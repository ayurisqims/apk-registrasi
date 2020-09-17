package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Data_anggota extends AppCompatActivity {

    private String URL_Data_Anggota = "http://192.168.100.174:80/api/data_anggota/";
    private LinearLayoutManager layoutManager;

//    RecyclerView.Adapter adapter;
//    RecyclerView rvDataAnggota;
//    List<Anggota> listAnggota = null;
    SharedPreferences userPref;
    RequestQueue requestQueue;
    TextView Nama, Nim, JenisKelamin, NoHp, Email, Sosmed, Alamat, Keahlian, BidangMinat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_anggota);

        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//        rvDataAnggota.setLayoutManager(new LinearLayoutManager(this));
//        rvDataAnggota = findViewById(R.id.rvDataAnggota);

        tombol();
        tampilData();
    }

    private void tombol() {

        Nama = findViewById(R.id.txtJwbNama);
        Nim = findViewById(R.id.txtJwbNIM);
        JenisKelamin = findViewById(R.id.txtJwbJenisKelamin);
        NoHp = findViewById(R.id.txtJwbNoHp);
        Email = findViewById(R.id.txtJwbEmail);
        Sosmed = findViewById(R.id.txtJwbSosmed);
        Alamat = findViewById(R.id.txtJwbAlamat);
        Keahlian = findViewById(R.id.txtJwbKeahlian);
        BidangMinat = findViewById(R.id.txtJwbBidangMinat);


//        Button tambah = findViewById(R.id.btnTambah);
//        tambah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent tambah = new Intent(Data_anggota.this, Regist_anggota.class);
//                startActivity(tambah);
//            }
//        });
    }

    private void tampilData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Data_Anggota, response -> {

            try {
                JSONObject jsonobject = new JSONObject(response);
                Log.i("autolog", "tampilData ");

                JSONArray jsonArray = jsonobject.getJSONArray("data");
                JSONObject data = jsonArray.getJSONObject(0);

                String StrNama = data.getString("nama");
                String StrNIM = data.getString("nim");
                String StrJenisKelamin = data.getString("jenis_kelamin");
                String StrNoTelp = data.getString("no_hp");
                String StrEmail = data.getString("email_anggota");
                String StrSosmed = data.getString("sosmed");
                String StrAlamat = data.getString("alamat");
                String StrKeahlian = data.getString("keahlian");
//                String StrBidangMinat = data.getString("bidang_minat");
                Log.i("string", "tampilData: ");

                Nama.setText(StrNama);
                Nim.setText(StrNIM);
                JenisKelamin.setText(StrJenisKelamin);
                Alamat.setText(StrAlamat);
                NoHp.setText(StrNoTelp);
                Email.setText(StrEmail);
                Sosmed.setText(StrSosmed);
                Alamat.setText(StrAlamat);
                Keahlian.setText(StrKeahlian);
//                BidangMinat.setText(StrBidangMinat);
                Log.i("autolog", "tampilData: get data json");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Data_anggota.this, "Gagal", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer" + token);

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", Nama.getText().toString());
                params.put("nim", Nim.getText().toString());
                params.put("no_hp", NoHp.getText().toString());
                params.put("sosmed", Sosmed.getText().toString());
                params.put("jenis_kelamin", JenisKelamin.getText().toString());
                params.put("keahlian", Keahlian.getText().toString());
                params.put("email_anggota", Email.getText().toString());
                params.put("alamat", Alamat.getText().toString());
//                params.put("bidang_minat", BidangMinat.getText().toString());
                return params;
            }

        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
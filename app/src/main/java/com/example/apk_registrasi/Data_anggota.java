package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.Models.Anggota;
import com.example.apk_registrasi.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data_anggota extends AppCompatActivity{


    private RecyclerView.Adapter adapter;
    private RecyclerView rvDataAnggota;
    List<Anggota> Anggota_item;

    SharedPreferences userPref1;
    RequestQueue requestQueue;
    TextView Nama, Nim, JenisKelamin, NoHp, Email, Sosmed, Alamat, Keahlian, BidangMinat;
    Button Edit, Hapus;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        userPref1 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

//        Recycler View
        Anggota_item = new ArrayList<>();

        rvDataAnggota = findViewById(R.id.recycler_view);

        // use a linear layout manager
        final LinearLayoutManager layoutManager = new LinearLayoutManager(Data_anggota.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDataAnggota.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvDataAnggota.setHasFixedSize(true);

        // specify an adapter (see also next example)
        adapter = new MyAdapter(Data_anggota.this, (ArrayList<Anggota>) Anggota_item);
        rvDataAnggota.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.i(TAG, "onCreate: ");

        tombol();
        tampil_data();

    }

    private void tombol() {

        Nama               = findViewById(R.id.txtJwbNama);
        Nim                = findViewById(R.id.txtJwbNIM);
        JenisKelamin       = findViewById(R.id.txtJwbJenisKelamin);
        NoHp               = findViewById(R.id.txtJwbNoHp);
        Email              = findViewById(R.id.txtJwbEmail);
        Sosmed             = findViewById(R.id.txtJwbSosmed);
        Alamat             = findViewById(R.id.txtJwbAlamat);
        Keahlian           = findViewById(R.id.txtJwbKeahlian);
        BidangMinat        = findViewById(R.id.txtJwbBidangMinat);
        Edit               = findViewById(R.id.btnEdit);

        ImageButton tambah = findViewById(R.id.btnTambah);
        tambah.setOnClickListener(v -> {
            Intent tambah1 = new Intent(Data_anggota.this, Regist_anggota.class);
            startActivity(tambah1);
        });
//        Button edit = findViewById(R.id.btnEdit);
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////
////                String nama = userPref.getString("nama", "");
////                String nim = userPref.getString("nim", "");
////                String jenis_kelamin = userPref.getString("jenis_kelamin", "");
////                String no_hp = userPref.getString("no_hp", "");
////                String email_anggota = userPref.getString("email_anggota", "");
////                String sosmed = userPref.getString("sosmed", "");
////                String alamat = userPref.getString("alamat", "");
////                String keahlian = userPref.getString("keahlian", "");
////                String bidang_minat = userPref.getString("bidang_minat", "");
//
//            }
//        });
    }

    private void tampil_data() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_DATA_ANGGOTA, response -> {

            try {
                JSONObject jsonobject = new JSONObject(response);
                Log.i("autolog", "tampilData ");
                JSONArray jsonArray = jsonobject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject data = jsonArray.getJSONObject(i);

                    String nama = data.getString("nama");
                    String nim= data.getString("nim");
                    String jenis_kelamin = data.getString("jenis_kelamin");
                    String no_hp = data.getString("no_hp");
                    String email_anggota = data.getString("email_anggota");
                    String sosmed = data.getString("sosmed");
                    String alamat = data.getString("alamat");
                    String keahlian = data.getString("keahlian");
//                  String bidang_minat = data.getString("bidang_minat");
                    Log.i("string", "tampilData: "+nama);

                    Anggota anggota = new Anggota(nama, nim, jenis_kelamin, no_hp, email_anggota, sosmed, alamat, keahlian);
                    Anggota_item.add(anggota);
                }
                adapter = new MyAdapter(Data_anggota.this, (ArrayList<Anggota>) Anggota_item);
                rvDataAnggota.setAdapter(adapter);

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
                String token = userPref1.getString("token", "");
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer" + token);
                Log.i(TAG, "getHeaders: sukses");

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.i("autolog", Nama.getText().toString());
                params.put("nama", Nama.getText().toString());
                params.put("nim", Nim.getText().toString());
                params.put("no_hp", NoHp.getText().toString());
                params.put("sosmed", Sosmed.getText().toString());
                params.put("jenis_kelamin", JenisKelamin.getText().toString());
                params.put("keahlian", Keahlian.getText().toString());
                params.put("email_anggota", Email.getText().toString());
                params.put("alamat", Alamat.getText().toString());
//              params.put("bidang_minat", BidangMinat.getText().toString());
                Log.i(TAG, "getParams: "+params);
                return params;
            }

        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
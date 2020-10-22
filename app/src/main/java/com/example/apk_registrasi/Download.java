package com.example.apk_registrasi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.Models.Soal;
import com.example.apk_registrasi.Utils.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Download extends AppCompatActivity {


    private static final int PERMISSION_STORAGE_CODE = 1000;
    private RecyclerView.Adapter adapter;
    private RecyclerView rvSoal;
    List<Soal> Soal_item;


    SharedPreferences userPref;
    RequestQueue requestQueue;
    TextView Soal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view2);

        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        Soal_item = new ArrayList<>();
        rvSoal = findViewById(R.id.recycler_view_soal);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(Download.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSoal.setLayoutManager(layoutManager);
        rvSoal.setHasFixedSize(true);

        adapter = new MySoalAdapter(Download.this, (ArrayList<Soal>)Soal_item);
        rvSoal.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        tampil_data();

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_download);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_download:
                        return false;
                    case R.id.navigation_data:
                        startActivity(new Intent(getApplicationContext()
                                ,Data_kel.class));
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.navigation_upload:
                        startActivity(new Intent(getApplicationContext()
                                ,Upload.class));
                        overridePendingTransition(0,0);
                        return false;
                }
                return false;
            }
        });
    }

    private void tampil_data() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_DOWNLOAD, response -> {

            try {

                JSONObject object = new JSONObject(response);
                JSONArray jsonArray = object.getJSONArray("soal");

                for (int i = 0; i < jsonArray.length(); i++){

                 JSONObject data = jsonArray.getJSONObject(i);

                 String id = data.getString("id");
                 String item = data.getString("item");

                    Soal soal = new Soal(id, item);
                    Soal_item.add(soal);
                }
                adapter = new MySoalAdapter(Download.this, (ArrayList<Soal>) Soal_item);
                rvSoal.setAdapter(adapter);

            } catch (Exception e){
                e.printStackTrace();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Download.this, "Gagal", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer" +token);
                Log.i("Download", "getHeaders: ");

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("item", Soal.getText().toString());

                Log.i("Download", "getParams: "+params);
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    // Membuat Perizinan
    @Override
    public void onRequestPermissionsResult
    (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tampil_data();
                }
                else {
                    Toast.makeText(this, "Permintaan Ditolak ", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    // Membuat Method Logout
    private void logout() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_LOGOUT, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(Download.this, MainActivity.class));
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

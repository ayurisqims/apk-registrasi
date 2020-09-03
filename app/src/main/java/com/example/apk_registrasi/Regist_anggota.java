package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Regist_anggota extends AppCompatActivity {


    EditText txtNama, txtNIM, txtNO, txtEmail, txtSosmed, txtAlamat;
    Spinner jk, bm;
    SharedPreferences userPref;
    String jenisKelamin, keahlian;
    CheckBox ui, web, frontend, androidDev, database;

    private static String URL_Regist_Anggota = "http://192.168.43.248:80/api/RegisterAnggota/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_anggota);

        ArrayList<CheckBox> bdngMnt = new ArrayList<CheckBox>();
        for (CheckBox item : bdngMnt){
                if (ui.isChecked())
                    String text = ui.getText().toString();
            }


        initRegister();

        Spinner spinnerJK = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.JenisKelamin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJK.setAdapter(adapter);

        spinnerJK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              jenisKelamin = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                jenisKelamin = "";
            }
        });

        Spinner spinnerK = findViewById(R.id.spinnerKeahlian);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.keahlian, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerK.setAdapter(adapter1);

        spinnerK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                keahlian = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                keahlian = "";
            }
        });

//        CheckBox ui = (CheckBox) findViewById(R.id.cbUI);
//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean("checkBox1", false);
//        ui.setChecked(checked);

//      checked  ArrayList<CheckBox> bidangMinat=new ArrayList<CheckBox>();
//        for (CheckBox item : bidangMinat ){
//            if ()
//        }

    }

    private void initRegister() {

        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        txtNama = findViewById(R.id.editNama);
        txtNIM = findViewById(R.id.editNIM);
        txtNO = findViewById(R.id.editNO);
        txtEmail = findViewById(R.id.editEmail);
        txtAlamat = findViewById(R.id.editAlamat);
        txtSosmed = findViewById(R.id.editSosmed);
        jk = findViewById(R.id.spinner);
        bm = findViewById(R.id.spinnerKeahlian);
        ui = findViewById(R.id.cbUI);
        web = findViewById(R.id.cbWeb);
        frontend = findViewById(R.id.cbFrontend);
        database = findViewById(R.id.cbDatabase);
        androidDev = findViewById(R.id.cbAndroid);

        Button simpan = findViewById(R.id.btnSimpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    Registrasi();

                }
            }
        });
    }

    private boolean validate() {
        if (txtNama.getText().toString().isEmpty()) {
            txtNama.setError("Masukkan Nama");
            return false;
        }
        if (txtNIM.getText().toString().isEmpty()) {
            txtNIM.setError("Masukkan NIM");
            return false;
        }
        if (txtNO.getText().toString().isEmpty()) {
            txtNO.setError("Masukkan No Hp");
            return false;
        }
        if (txtEmail.getText().toString().isEmpty()) {
            txtEmail.setError("Masukkan Email");
            return false;
        }
        if (txtSosmed.getText().toString().isEmpty()) {
            txtSosmed.setError("Masukkan Sosial Media");
            return false;
        }
        if (txtAlamat.getText().toString().isEmpty()) {
            txtAlamat.setError("Masukkan Alamat");
            return false;
        }
        return true;
    }

    private void Registrasi() {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Regist_Anggota, response -> {

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("success")) {
                        Toast.makeText(Regist_anggota.this, "Registrasi Berhasil ",
                                Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(Regist_anggota.this, MainActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(Regist_anggota.this, "Data yang dimasukan salah",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Regist_anggota.this, e+"",
                            Toast.LENGTH_SHORT).show();
                }
            }, error -> Toast.makeText(Regist_anggota.this, "Error" +error.toString(),
                    Toast.LENGTH_SHORT).show()) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String token = userPref.getString("token", "");
                    HashMap<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer" +token);

                    return params;
                }


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();

                    if(ui.isChecked()){
                        params.put("bidang_minat", bdgMinat.toString());
                    }

                    params.put("nama", txtNama.getText().toString());
                    params.put("nim", txtNIM.getText().toString());
                    params.put("no_hp", txtNO.getText().toString());
                    params.put("sosmed", txtSosmed.getText().toString());
                    params.put("jenis_kelamin", jenisKelamin);
                    params.put("keahlian", keahlian);
                    params.put("email_anggota", txtEmail.getText().toString());
                    params.put("alamat", txtAlamat.getText().toString());
                    return params;
                }
            };

        try{
            Toast.makeText(Regist_anggota.this, "Registrasi Berhasil ",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Regist_anggota.this, Data_anggota.class);
            startActivity(intent);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (Exception e){
            Toast.makeText(Regist_anggota.this, "Registrasi Gagal!", Toast.LENGTH_SHORT).show();
        }

        }


}
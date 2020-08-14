package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Regist_anggota extends AppCompatActivity {


    EditText txtNama, txtNIM, txtNO, txtEmail, txtSosmed, txtAlamat, txtKeahlian;
    Spinner jk, bm;
    String[] jenis_kelamin={"Perempuan", "Laki-laki"};
    String[] bidang_minat={"UI/UX", "Android Developer", "Web Programming", "Frontend", "Database"};
    private static String URL_REGIST = "http://192.168.100.174:80/api/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_anggota);

        initRegister();

        Spinner spinnerJK = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.JenisKelamin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJK.setAdapter(adapter);

        spinnerJK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(getApplicationContext(),
                       jenis_kelamin[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerBM = findViewById(R.id.spinnerBM);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.BidangMinat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBM.setAdapter(adapter1);

        spinnerBM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                
                Toast.makeText(getApplicationContext(),
                        bidang_minat[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initRegister() {

        txtNama = findViewById(R.id.editNama);
        txtNIM = findViewById(R.id.editNIM);
        txtNO = findViewById(R.id.editNO);
        txtEmail = findViewById(R.id.editEmail);
        txtAlamat = findViewById(R.id.editAlamat);
        txtKeahlian = findViewById(R.id.editKeahlian);
        jk = findViewById(R.id.spinner);
        bm = findViewById(R.id.spinnerBM);

        Button plus = findViewById(R.id.btnPlus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    Registrasi();
                }
            }
        });
    }

    private void Registrasi() {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, response -> {

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("success")) {
                        JSONObject user = object.getJSONObject("user");

                        SharedPreferences userPref = getApplicationContext().getSharedPreferences
                                ("user", MainActivity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = userPref.edit();
                        editor.putString("token", object.getString("token"));
                        editor.putString("email", user.getString("email"));
                        editor.apply();

                        Toast.makeText(Regist_anggota.this, "Registrasi Berhasil ",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Regist_anggota.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Regist_anggota.this, "Data yang dimasukan salah",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(Regist_anggota.this, "Error" +error.toString(),
                    Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nama", txtNama.getText().toString().trim());
                    params.put("nim", txtNIM.getText().toString().trim());
                    params.put("no_hp", txtNO.getText().toString().trim());
                    params.put("sosmed", txtSosmed.getText().toString().trim());
                    params.put("email_anggota", txtEmail.getText().toString().trim());
                    params.put("alamat", txtAlamat.getText().toString().trim());
                    params.put("keahlian", txtKeahlian.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

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
        if (txtKeahlian.getText().toString().isEmpty()) {
            txtKeahlian.setError("Masukkan Keahlian");
            return false;
        }
        return true;
    }


}
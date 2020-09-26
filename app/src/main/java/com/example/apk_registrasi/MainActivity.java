package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.apk_registrasi.Utils.Constant;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextInputEditText Email, Password;
    TextInputLayout LayoutEmail, LayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        LayoutEmail     = findViewById(R.id.txtLayoutEmail);
        LayoutPassword  = findViewById(R.id.txtLayoutPassword);
        Email           = findViewById(R.id.txtInputEmail);
        Password        = findViewById(R.id.txtInputPassword);

        Button login    = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Login();
                    Log.i("login", "onClick: ");
                }
            }
        });

        Button register = findViewById(R.id.btnRegist);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Regist_kel.class);
                startActivity(intent);
            }
        });

//        Text Watcher digunakan untuk membaca atau mendeteksi text ketika user menginputkan
        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//          Awalan suatu text yang akan di baca
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//          Pemrosesan suatu text
                if (!Email.getText().toString().isEmpty()) {
                    LayoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//          akhiran ketikan text berhasil di baca
            }
        });

        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Password.getText().toString().length() > 7) {
                    LayoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean validate() {
        if (Email.getText().toString().isEmpty()) {
            Email.setError("Masukkan Email");
            return false;
        }
        if (Password.getText().toString().length() < 8) {
            Password.setError("Masukkan Password 8 Karakter");
            return false;
        }
        return true;
    }

    public void onBackPressed() {
        //do nothing
    }

    private void Login() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_LOGIN, response -> {
            Log.i("login", "Login: ");

            try {
                JSONObject object = new JSONObject(response);

                if (object.getBoolean("success")) {
                    JSONObject user = object.getJSONObject("user");
                    JSONObject kelompok = object.getJSONObject("kelompok");

//                  Menyimpan data menggunkaan SharedPreferences
                    SharedPreferences userPref = getApplicationContext().getSharedPreferences
                            ("user", MainActivity.MODE_PRIVATE);
//                   Deklarasi edit preferences dan mengubah data
                    SharedPreferences.Editor editor = userPref.edit();

//                   Mengambil data putString(database) dan menampilkan data getString
                    editor.putString("token", object.getString("token"));
                    editor.putString("email", user.getString("email"));
                    editor.putInt("id_kelompok", kelompok.getInt("id"));
                    editor.putString("universitas", kelompok.getString("universitas"));
                    editor.putString("fakultas", kelompok.getString("fakultas"));
                    editor.putString("prodi", kelompok.getString("prodi"));
                    editor.putString("alamat_univ", kelompok.getString("alamat_univ"));
                    editor.putString("kelompok", kelompok.getString("kelompok"));
                    editor.putString("jumlah_anggota", kelompok.getString("jumlah_anggota"));
                    editor.putString("periode_mulai", kelompok.getString("periode_mulai"));
                    editor.putString("periode_akhir", kelompok.getString("periode_akhir"));
                    editor.putString("nama_ketua", kelompok.getString("nama_ketua"));
                    editor.apply();
                    Log.i("login", "Login: putString");

                    Toast.makeText(MainActivity.this, "Login Berhasil ",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Data_kel.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Username atau password salah",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }, error -> Toast.makeText(MainActivity.this, "Error" +error.toString(),
                Toast.LENGTH_SHORT).show()) {

//            Mengambil nilai parameter yang dikirim dari client ke server
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", Email.getText().toString().trim());
                params.put("password", Password.getText().toString().trim());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        Log.i("login", "Login: RequestQueue");

    }
}
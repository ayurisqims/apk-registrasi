package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class MainActivity extends AppCompatActivity {

    EditText Email, Password;
    private String URL_LOGIN = "http://192.168.1.6/api/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (!email.isEmpty() || !password.isEmpty()) {
                    Login(email, password);
                } else {
                    Email.setError("Masukkan Email");
                    Password.setError("Masukkan Password");

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

    }

    private void Login(final String email, final String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject Object = new JSONObject(response);

                            String success = Object.getString("success");
                            JSONArray jsonArray = Object.getJSONArray("user");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    SharedPreferences userPref = getApplication(). getApplicationContext().getSharedPreferences("user", Email.getContext().MODE_PRIVATE);
                                    SharedPreferences.Editor editor = userPref.edit();
                                    editor.putString("token", object.getString("token"));
                                    editor.putString("email", object.getString("email"));
                                    editor.apply();

                                    Toast.makeText(MainActivity.this, "Login berhasil" + email, Toast.LENGTH_SHORT).show();

                                    Intent login = new Intent(MainActivity.this, Data_kel.class);
                                    startActivity(login);
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Email dan Password yang dimasukkan salah", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Email dan Password salah", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error" +error.toString(), Toast.LENGTH_SHORT).show();
                }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        }
    }
package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;


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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextInputEditText txtEmail, txtPassword;
    TextInputLayout layoutEmail, layoutPassword;
    private String URL_LOGIN = "http://192.168.1.6/api/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        layoutEmail = findViewById(R.id.txtLayoutEmail);
        layoutPassword = findViewById(R.id.txtLayoutPassword);
        txtEmail = findViewById(R.id.InputEmail);
        txtPassword = findViewById(R.id.InputPassword);
        Button login = findViewById(R.id.btnLogin);
        Button register = findViewById(R.id.btnRegist);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Regist_kel.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    login();
                }
            }
        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEmail.getText().toString().isEmpty()) {
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtPassword.getText().toString().length() > 7) {
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private boolean validate() {
        if (txtEmail.getText().toString().isEmpty()) {
            layoutEmail.setEnabled(true);
            layoutEmail.setError("Masukkan Email");
            return false;
        }
        if (txtPassword.getText().toString().length() < 8) {
            layoutPassword.setEnabled(true);
            layoutPassword.setError("Masukkan Password 8 Karakter");
            return false;
        }
        return true;
    }


    private void login() {

        StringRequest request = new StringRequest(Request.Method.POST, URL_LOGIN, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    JSONObject user = object.getJSONObject("user");
                    SharedPreferences userPref = this.getApplicationContext().getSharedPreferences("user", this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putString("email", object.getString("email"));
                    editor.apply();

                    Toast.makeText(this, "Login Berhasil ", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", txtEmail.getText().toString().trim());
                params.put("password", txtPassword.getText().toString().trim());
                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}



package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.Utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Regist_anggota extends AppCompatActivity {

    ArrayList<String> selection = new ArrayList<String>();

    EditText Nama, NIM, NO, Email, Sosmed, Alamat;
    Spinner JenisKelamin, Keahlian;
    SharedPreferences userPref;
    String jenis_kelamin, keahlian;
//    String bidang_minat[];
    CheckBox UI, Web, Frontend, AndroidDev, Database;

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

              jenis_kelamin = parent.getItemAtPosition(position).toString();
              jenis_kelamin = spinnerJK.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                jenis_kelamin = "";
            }
        });

        CharSequence spinnerJKSelectedData  = (CharSequence) spinnerJK.getSelectedItem();

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


    public void selectItem(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId())
        {
            case R.id.cbUI:
                if(checked)
                {selection.add("UI / U X");}
                else
                {selection.remove("UI / UX");}
                break;
            case R.id.cbWeb:
                if(checked)
                {selection.add("Web Programing");}
                else
                {selection.remove("Web Programing");}
                break;
            case R.id.cbFrontend:
                if(checked)
                {selection.add("Frontend");}
                else
                {selection.remove("Frontend");}
                break;
            case R.id.cbDatabase:
                if(checked)
                {selection.add("Database");}
                else
                {selection.remove("Database");}
                break;
            case R.id.cbAndroid:
                if(checked)
                {selection.add("Android Developer");}
                else
                {selection.remove("Android Developer");}
                break;
        }
    }

    private void initRegister() {

        userPref    = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        Nama        = findViewById(R.id.editNama);
        NIM         = findViewById(R.id.editNIM);
        NO          = findViewById(R.id.editNO);
        Email       = findViewById(R.id.editEmail);
        Alamat      = findViewById(R.id.editAlamat);
        Sosmed      = findViewById(R.id.editSosmed);
        JenisKelamin = findViewById(R.id.spinner);
        Keahlian    = findViewById(R.id.spinnerKeahlian);
        UI          = findViewById(R.id.cbUI);

        Web         = findViewById(R.id.cbWeb);
        Frontend    = findViewById(R.id.cbFrontend);
        Database    = findViewById(R.id.cbDatabase);
        AndroidDev  = findViewById(R.id.cbAndroid);


        Button simpan = findViewById(R.id.btnSimpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    registrasi();
                }
            }
        });
    }

    private boolean validate() {
        if (Nama.getText().toString().isEmpty()) {
            Nama.setError("Masukkan Nama");
            return false;
        }
        if (NIM.getText().toString().isEmpty()) {
            NIM.setError("Masukkan NIM");
            return false;
        }
        if (NO.getText().toString().isEmpty()) {
            NO.setError("Masukkan No Hp");
            return false;
        }
        if (Email.getText().toString().isEmpty()) {
            Email.setError("Masukkan Email");
            return false;
        }
        if (Sosmed.getText().toString().isEmpty()) {
            Sosmed.setError("Masukkan Sosial Media");
            return false;
        }
        if (Alamat.getText().toString().isEmpty()) {
            Alamat.setError("Masukkan Alamat");
            return false;
        }
        return true;
    }

    private void registrasi() {

        String final_checkbox = "";
        for(String Selections : selection)
        {
            final_checkbox = final_checkbox  + Selections + ",";
        }
        String final_cb = final_checkbox;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_REGIST_ANGGOTA, response -> {

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("success")) {
                        Toast.makeText(Regist_anggota.this, "Registrasi Berhasil ",
                                Toast.LENGTH_SHORT).show();
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

                    params.put("nama", Nama.getText().toString());
                    params.put("nim", NIM.getText().toString());
                    params.put("no_hp", NO.getText().toString());
                    params.put("sosmed", Sosmed.getText().toString());
                    params.put("jenis_kelamin", jenis_kelamin);
                    params.put("keahlian", keahlian);
                    params.put("email_anggota", Email.getText().toString());
                    params.put("alamat", Alamat.getText().toString());
                    params.put("bidang_minat", final_cb);
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
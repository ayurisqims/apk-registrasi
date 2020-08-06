package com.example.apk_registrasi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Regist_kel extends AppCompatActivity {

    private static final String TAG = "Regist_kel";
    private TextView mDisplayDate, mDisplayDate1;
    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener1;

    TextInputEditText txtUniv, txtFakultas, txtProdi, txtAlamat,
            txtJumlah, txtKelompok, txtNK, txtEmail, txtPassword;
    TextView periodeMulai, periodeAkhir;

    private static String URL_REGIST = "http://192.168.1.8:80/api/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_kel);

        initRegister();

        mDisplayDate = (TextView) findViewById(R.id.txtPeriodM);

        mDisplayDate.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    Regist_kel.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: yyyy/mm/dd: " + year + "/" + month + "/" + day);

            String date = year + "/" + month + "/" + day;
            mDisplayDate.setText(date);
        };

        mDisplayDate1 = (TextView) findViewById(R.id.txtPerioA);

        mDisplayDate1.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    Regist_kel.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener1,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener1 = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: yyyy/mm/dd: " + year + "/" + month + "/" + day);

            String date = year + "/" + month + "/" + day;
            mDisplayDate1.setText(date);
        };


    }

    private void initRegister() {


        txtUniv = findViewById(R.id.inputUniv);
        txtFakultas = findViewById(R.id.inputFakultas);
        txtProdi = findViewById(R.id.inputProdi);
        txtAlamat = findViewById(R.id.inputAlamat);
        txtJumlah = findViewById(R.id.inputJumlah);
        txtKelompok = findViewById(R.id.inputKelompok);
        txtNK = findViewById(R.id.inputNama);
        txtEmail = findViewById(R.id.inputEmail);
        txtPassword = findViewById(R.id.inputPassword);
        periodeMulai = findViewById(R.id.txtPeriodM);
        periodeAkhir = findViewById(R.id.txtPerioA);

        Button tambah = findViewById(R.id.btnTambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (validate()){
                   Registrasi();
               }
            }
        });

    }

    private boolean validate(){

        if (txtUniv.getText().toString().isEmpty()) {
            txtUniv.setError("Masukkan Universitas");
            return false;
        }
        if (txtFakultas.getText().toString().isEmpty()) {
            txtFakultas.setError("Masukkan Fakultas");
            return false;
        }
        if (txtProdi.getText().toString().isEmpty()) {
            txtProdi.setError("Masukkan Prodi");
            return false;
        }
        if (txtAlamat.getText().toString().isEmpty()) {
            txtAlamat.setError("Masukkan Alamat");
            return false;
        }
        if (txtJumlah.getText().toString().isEmpty()) {
            txtJumlah.setError("Masukkan Jumlah Anggota Kelompok");
            return false;
        }
        if (txtKelompok.getText().toString().isEmpty()) {
            txtKelompok.setError("Masukkan Urutan Kelompok");
            return false;
        }
        if (txtNK.getText().toString().isEmpty()) {
            txtNK.setError("Masukkan Nama Ketua");
            return false;
        }
        if (txtEmail.getText().toString().isEmpty()) {
            txtEmail.setError("Masukkan Email");
            return false;
        }
        if (txtPassword.getText().toString().length() < 8) {
            txtPassword.setError("Masukkan Password 8 Karakte");
            return false;
        }
//        if (mDisplayDate.getText().toString().isEmpty()) {
//            txtPeriode.setError("Masukkan Periode Magang");
//            return false;
//        }
//        if (mDisplayDate1.getText().toString().isEmpty()) {
//            txtPeriode.setError("Masukkan Periode Magang");
//            return false;
//
//        }
        return true;
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

                    Toast.makeText(Regist_kel.this, "Registrasi Berhasil ",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Regist_kel.this, Regist_anggota.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Regist_kel.this, "Data yang dimasukan salah",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(Regist_kel.this, "Error" +error.toString(),
                Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("universitas", txtUniv.getText().toString().trim());
                params.put("fakultas", txtFakultas.getText().toString().trim());
                params.put("prodi", txtProdi.getText().toString().trim());
                params.put("alamat_univ", txtAlamat.getText().toString().trim());
                params.put("kelompok", txtKelompok.getText().toString().trim());
                params.put("jumlah_anggota", txtJumlah.getText().toString().trim());
                params.put("periode_mulai", periodeMulai.getText().toString().trim());
                params.put("periode_akhir", periodeAkhir.getText().toString().trim());
                params.put("nama_ketua", txtNK.getText().toString().trim());
                params.put("email", txtEmail.getText().toString().trim());
                params.put("password", txtPassword.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}


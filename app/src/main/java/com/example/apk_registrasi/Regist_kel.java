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
    private static String URL_REGIST = "http://192.168.100.174:80/api/register/";

    TextView mDisplayDate, mDisplayDate1;
    DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener1;
    TextInputEditText Universitas, Fakultas, Prodi, Alamat, Jumlah, Kelompok, NamaKetua, Email, Password;
    TextView Hasil, periodeMulai,periodeAkhir;



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
                    year, month, day);
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
                    year, month, day);
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

        Universitas = findViewById(R.id.inputUniv);
        Fakultas = findViewById(R.id.inputFakultas);
        Prodi = findViewById(R.id.inputProdi);
        Alamat = findViewById(R.id.inputAlamat);
        Jumlah = findViewById(R.id.inputJumlah);
        Kelompok = findViewById(R.id.inputKelompok);
        NamaKetua = findViewById(R.id.inputNama);
        Email = findViewById(R.id.inputEmail);
        Password = findViewById(R.id.inputPassword);
        periodeMulai = findViewById(R.id.txtPeriodM);
        periodeAkhir = findViewById(R.id.txtPerioA);

        Button submit = findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (validate()){
                   Registrasi();
               }
            }
        });

    }

    private boolean validate(){

        if (Universitas.getText().toString().isEmpty()) {
            Universitas.setError("Masukkan Universitas");
            return false;
        }
        if (Fakultas.getText().toString().isEmpty()) {
            Fakultas.setError("Masukkan Fakultas");
            return false;
        }
        if (Prodi.getText().toString().isEmpty()) {
            Prodi.setError("Masukkan Prodi");
            return false;
        }
        if (Alamat.getText().toString().isEmpty()) {
            Alamat.setError("Masukkan Alamat");
            return false;
        }
        if (Jumlah.getText().toString().isEmpty()) {
            Jumlah.setError("Masukkan Jumlah Anggota Kelompok");
            return false;
        }
        if (Kelompok.getText().toString().isEmpty()) {
            Kelompok.setError("Masukkan Urutan Kelompok");
            return false;
        }
        if (NamaKetua.getText().toString().isEmpty()) {
            NamaKetua.setError("Masukkan Nama Ketua");
            return false;
        }
        if (Email.getText().toString().isEmpty()) {
            Email.setError("Masukkan Email");
            return false;
        }
        if (Password.getText().toString().length() < 8) {
            Password.setError("Masukkan Password 8 Karakte");
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

                    //Menyimpan nilai email dan token di shared preferences
                    SharedPreferences userPref = getApplicationContext().getSharedPreferences
                            ("user", Regist_kel.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putString("email", user.getString("email"));
                    editor.apply();

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
                params.put("universitas", Universitas.getText().toString().trim());
                params.put("fakultas", Fakultas.getText().toString().trim());
                params.put("prodi", Prodi.getText().toString().trim());
                params.put("alamat_univ", Alamat.getText().toString().trim());
                params.put("kelompok", Kelompok.getText().toString().trim());
                params.put("jumlah_anggota", Jumlah.getText().toString().trim());
                params.put("periode_mulai", periodeMulai.getText().toString().trim());
                params.put("periode_akhir", periodeAkhir.getText().toString().trim());
                params.put("nama_ketua", NamaKetua.getText().toString().trim());
                params.put("email", Email.getText().toString().trim());
                params.put("password", Password.getText().toString().trim());

                return params;
            }
        };
        try{

            Toast.makeText(Regist_kel.this, "Registrasi Berhasil ",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Regist_kel.this, MainActivity.class);
            startActivity(intent);

            // Volley
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (Exception e){
            Toast.makeText(Regist_kel.this, "Registrasi Gagal!", Toast.LENGTH_SHORT).show();
        }
    }

    }



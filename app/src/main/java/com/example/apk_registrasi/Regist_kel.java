package com.example.apk_registrasi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.Utils.Constant;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Regist_kel extends AppCompatActivity {

    private static final String TAG = "Regist_kel";

    DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener1;
    TextInputEditText Univ,Fakultas, Prodi, Alamat, Jumlah, Email, Password, NamaKetua;
    TextView PeriodeMulai,PeriodeAkhir;
    String Kelompok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_kel);

        init();
    }

    private void init() {

        Univ            = findViewById(R.id.inputUniv);
        Fakultas        = findViewById(R.id.inputFakultas);
        Prodi           = findViewById(R.id.inputProdi);
        Alamat          = findViewById(R.id.inputAlamat);
        Jumlah          = findViewById(R.id.inputJumlah);
        PeriodeMulai    = findViewById(R.id.txtPeriodM);
        PeriodeAkhir    = findViewById(R.id.txtPerioA);
        NamaKetua       = findViewById(R.id.inputNama);
        Email           = findViewById(R.id.inputEmail);
        Password        = findViewById(R.id.inputPassword);

        Button submit = findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (validate()){
                   registrasi();

               }
            }
        });

        TextView mDisplayDateMulai = findViewById(R.id.txtPeriodM);
        mDisplayDateMulai.setOnClickListener(view -> {
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
            mDisplayDateMulai.setText(date);
        };

        TextView mDisplayDateAkhir = findViewById(R.id.txtPerioA);
        mDisplayDateAkhir.setOnClickListener(view -> {
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
            mDisplayDateAkhir.setText(date);
        };

        Spinner spinnerKel = findViewById(R.id.spinnerKel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kelompok, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKel.setAdapter(adapter);

        spinnerKel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Kelompok = parent.getItemAtPosition(position).toString();
//                Intent i = new Intent(Regist_kel.this, Edit_data_kel.class);
//                Bundle bundleKel = new Bundle();
//                bundleKel.putString("SpinnerValue", spinnerKel.getSelectedItem().toString());
//                i.putExtras(bundleKel);
//
                Kelompok = spinnerKel.getSelectedItem().toString();
                String txtKelompok = spinnerKel.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Kelompok = "";
            }
        });

    }

    private boolean validate(){

        if (Univ.getText().toString().isEmpty()) {
            Univ.setError("Masukkan Universitas");
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
        if (NamaKetua.getText().toString().isEmpty()) {
            NamaKetua.setError("Masukkan Nama Ketua");
            return false;
        }

        return true;
    }

    private void registrasi() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_REGIST_KELOMPOK, response -> {

            try {
                JSONObject object = new JSONObject(response);

                if (object.getBoolean("success")) {
                    JSONObject user = object.getJSONObject("user");

//                  Menyimpan nilai email dan token di shared preferences
                    SharedPreferences userPref = getApplicationContext().getSharedPreferences
                            ("user", Regist_kel.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();

//                  Menambahkan nilai pada editor shared preferences (putString(database)) dan mengambil data getString
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

//          Mengambil nilai parameter yang dikirim dari client ke server
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("universitas", Univ.getText().toString().trim());
                params.put("fakultas", Fakultas.getText().toString().trim());
                params.put("prodi", Prodi.getText().toString().trim());
                params.put("alamat_univ", Alamat.getText().toString().trim());
                params.put("kelompok", Kelompok);
                params.put("jumlah_anggota", Jumlah.getText().toString().trim());
                params.put("periode_mulai", PeriodeMulai.getText().toString().trim());
                params.put("periode_akhir", PeriodeAkhir.getText().toString().trim());
                params.put("nama_ketua", NamaKetua.getText().toString().trim());
                params.put("email", Email.getText().toString().trim());
                params.put("password", Password.getText().toString().trim());
                Log.i("Regist_kel", "getParams: 8 "+params);

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



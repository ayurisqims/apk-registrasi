package com.example.apk_registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.auth.ApiService;
import com.example.apk_registrasi.auth.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Regist_kel extends AppCompatActivity {

    private static final String TAG = "Regist_kel";
    private TextView mDisplayDate, mDisplayDate1;
    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener1;

    EditText univ, fakultas, prodi, alamat, jumlah, kel, nama, email;
    private static String URL_REGIST = "http://192.168.1.6/api/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_kel);

        Button tambah = findViewById(R.id.btnTambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahAnggota();
            }
        });

        mDisplayDate = (TextView) findViewById(R.id.txtPeriodM);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: yyyy/mm/dd: " + year + "/" + month + "/" + day);

                String date = year + "/" + month + "/" + day;
                mDisplayDate.setText(date);
            }
        };

        mDisplayDate1 = (TextView) findViewById(R.id.txtPerioA);

        mDisplayDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: yyyy/mm/dd: " + year + "/" + month + "/" + day);

                String date = year + "/" + month + "/" + day;
                mDisplayDate1.setText(date);
            }
        };


    }

        private void tambahAnggota() {

            final String univ = this.univ.getText().toString().trim();
            final String fakultas = this.fakultas.getText().toString().trim();
            final String prodi = this.prodi.getText().toString().trim();
            final String alamat = this.alamat.getText().toString().trim();
            final String jumlah = this.jumlah.getText().toString().trim();
            final String kel = this.kel.getText().toString().trim();
            final String nama = this.nama.getText().toString().trim();
            final String email = this.email.getText().toString().trim();

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");

                                if (success.equals("1")) {
                                    Toast.makeText(Regist_kel.this, "Pendaftaran Kelompok Berhasil", Toast.LENGTH_SHORT).show();

                                    Intent tambah = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(tambah);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Regist_kel.this, "Pendaftaran Gagal" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Regist_kel.this, "Pendaftaran Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("univ", univ);
                    params.put("fakultas", fakultas);
                    params.put("prodi", prodi);
                    params.put("alamat", alamat);
                    params.put("jumlah", jumlah);
                    params.put("kel", kel);
                    params.put("nama", nama);
                    params.put("email", email);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
}


}

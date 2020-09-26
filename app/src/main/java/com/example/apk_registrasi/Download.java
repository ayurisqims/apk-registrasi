package com.example.apk_registrasi;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.strictmode.DiskWriteViolation;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.Utils.Constant;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Url;

public class Download extends AppCompatActivity {


    private static final int PERMISSION_STORAGE_CODE = 1000;

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        preferences = getApplicationContext().getSharedPreferences("user", Context. MODE_PRIVATE);

        TextView webProgram = findViewById(R.id.txtWebProgramming);
        webProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                        
                    } else {
                        webProgramming();
                    }
                } else {
                    webProgramming();
                }

            }
        });

        TextView ui = findViewById(R.id.txtUI);
        ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);

                    } else {
                        ui();
                    }
                } else {
                    ui();
                }

            }
        });

        TextView frontend = findViewById(R.id.txtFrontend);
        frontend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);

                    } else {
                        frontend();
                    }
                } else {
                    frontend();
                }
            }
        });

        TextView database = findViewById(R.id.txtDatabase);
        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);

                    } else {
                        database();
                    }
                } else {
                    database();
                }
            }
        });

        TextView webDev = findViewById(R.id.txtWebDev);
        webDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);

                    } else {
                        webDev();
                    }
                } else {
                    webDev();
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_download);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_download:
                        return false;
                    case R.id.navigation_data:
                        startActivity(new Intent(getApplicationContext()
                                ,Data_kel.class));
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.navigation_upload:
                        startActivity(new Intent(getApplicationContext()
                                ,Upload.class));
                        overridePendingTransition(0,0);
                        return false;
                }
                return false;
            }
        });
    }

    //Mendownload file melalui ip yang ada
    private void webProgramming() {

        String link = "http://192.168.43.248/data_soal/Web%20Programming.pdf";
        Uri uri = Uri.parse(link);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE)
                .setTitle("WebProgramming")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        try{
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"WebProgramming.pdf");

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(this, "Download Berhasil!", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(this, "Download Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void ui() {
        String link = "http://192.168.43.248/data_soal/UX.pdf";
        Uri uri = Uri.parse(link);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("UI/UX");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        try{
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"UI/UX.pdf");

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(this, "Download Berhasil!", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(this, "Download Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void frontend() {
        String link = "http://192.168.43.248data_soal/UX.pdf";
        Uri uri = Uri.parse(link);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Frontend");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        try{
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Frontend.pdf");

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(this, "Download Berhasil!", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(this, "Download Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void database() {
        String link = "http://192.168.43.248/data_soal/UX.pdf";
        Uri uri = Uri.parse(link);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Database");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        try{
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Database.pdf");

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(this, "Download Berhasil!", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(this, "Download Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void webDev() {
        String link = "http://192.168.43.248/data_soal/UX.pdf";
        Uri uri = Uri.parse(link);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Web Developer");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        try{
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Web Developer.pdf");

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(this, "Download Berhasil!", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(this, "Download Gagal", Toast.LENGTH_SHORT).show();
        }
    }


    // Membuat Perizinan
    @Override
    public void onRequestPermissionsResult
    (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    webProgramming();
                }
                else {
                    Toast.makeText(this, "Permintaan Ditolak ", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }


    // Membuat Method Logout
    private void logout() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_LOGOUT, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(Download.this, MainActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer" +token);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buttom_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.logout: {

                AlertDialog.Builder alertDialogBuilder = new  AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Apakah anda ingin keluar?");
                alertDialogBuilder.setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                });
                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.show();
            }

        }
        return super.onOptionsItemSelected(item);
    }

}

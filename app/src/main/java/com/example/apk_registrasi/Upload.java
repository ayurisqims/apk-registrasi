package com.example.apk_registrasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.Utils.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.acl.Permission;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class Upload extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_CODE = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    private int PICK_ZIP_REQUEST = 1;

    SharedPreferences userPref;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        userPref = getApplicationContext().getSharedPreferences("user", Context. MODE_PRIVATE);

        Button selectFile = findViewById(R.id.btnSelect);
        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=23) {
                    if(checkPermission()) {
                        showFileChooser();
                    } else {
                        requestPermission();
                    }
                }
            }
        });

        Button uploadFile = findViewById(R.id.btnUpload);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                upload_jawaban();
            }
        });

//        Membuat BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.button_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_upload);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_download:
                        startActivity(new Intent(getApplicationContext()
                                ,Download.class));
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.navigation_data:
                        startActivity(new Intent(getApplicationContext()
                                ,Data_kel.class));
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.navigation_upload:
                        return false;
                }
                return false;
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Upload.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("file/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File ZIP/RAR"), PICK_ZIP_REQUEST);
    }

    //  Membuat Method Perizinan

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Upload.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            Toast.makeText(Upload.this, "Berikan perizinan", Toast.LENGTH_SHORT).show();
            Log.i("Upload", "requestPermission: ");
        } else {
            ActivityCompat.requestPermissions(Upload.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView fileName = findViewById(R.id.txtFile);

        if(resultCode== Activity.RESULT_OK){
            String filePath = getRealPathUri(data.getData(), Upload.this);

            File file = new File(filePath);
            fileName.setText(file.getName());
            Log.i("Upload", "onActivityResult: "+fileName);
        }
    }

    public String getRealPathUri(Uri uri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if(cursor==null){
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int id=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }

    //    Upload File

//    private void upload_jawaban() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_UPLOAD, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        } {
//
//            @Override
//            public void onRespone (String response){
//                String s = response.trim();
//                if (!s.equalsIgnoreCase("Halo")) {
//                    Toast.makeText(Upload.this, "", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(Upload.this, "", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        } {
//
//        });
//    }


    //    Membuat method logout
    private void logout() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_LOGOUT, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(Upload.this, MainActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
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

                AlertDialog.Builder alertDialogBuilder = new  AlertDialog.Builder(this)
                        .setMessage("Apakah anda ingin keluar?")
                        .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {

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

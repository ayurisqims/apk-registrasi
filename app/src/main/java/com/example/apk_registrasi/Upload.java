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
import java.util.Objects;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Upload extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_CODE = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    private int PICK_ZIP_REQUEST = 1;

    TextView NameFile;
    Button Select, Upload;

    SharedPreferences preferences;
    Uri filePath = null;
    Intent myFileIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        preferences = getApplicationContext().getSharedPreferences("user", Context. MODE_PRIVATE);

        Button selectFile = findViewById(R.id.btnSelect);
        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT>=23) {
//                    if(checkPermission()) {
////                        filePicker();
//                    } else {
//                        requestPermission();
//                    }
//                } else {
//                    filePicker();
//                }
            }
        });

        Button uploadFile = findViewById(R.id.btnUpload);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(filePath!=null){
//                    fileUpload();
//                } else {
//                    Toast.makeText(Upload.this, "Tolong harap di isi", Toast.LENGTH_SHORT).show();
//                }
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

    //  Membuat Method Perizinan
//    private void requestPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(Upload.this, Manifest.permission.READ_EXTERNAL_STORAGE))
//        {
//            Toast.makeText(Upload.this, "Berikan perizinan", Toast.LENGTH_SHORT).show();
//        } else {
//            ActivityCompat.requestPermissions(Upload.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        }
//    }

//    private boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(Upload.this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        if (result == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        } else {
//            return false;
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult
//            (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(Upload.this, "Perizinan Sukses", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(Upload.this, "Perizinan Gagal", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        TextView fileName = findViewById(R.id.txtFile);
//
//        if(resultCode== Activity.RESULT_OK){
//            String filePath = getRealPathUri(data.getData(), Upload.this);
//            Log.d("File path : ", " " + filePath);
//
//            this.filePath=filePath;
//
//            File file = new File(filePath);
//            fileName.setText(file.getName());
//        }
//
//    }


    //    Membuat Method Upload File
//    private void fileUpload() {
//        UploadTask uploadTask = new UploadTask();
//        uploadTask.execute(new String[]{filePath});
//    }

//    private void filePicker() {
//
//        Toast.makeText(Upload.this, "File Dipilih", Toast.LENGTH_SHORT).show();
//        myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        myFileIntent.setType("*/*");
//        startActivityForResult(myFileIntent, 10);
//
//    }

//    public String getRealPathUri(Uri uri, Activity activity){
//        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
//        if(cursor==null){
//            return uri.getPath();
//        } else {
//            cursor.moveToFirst();
//            int id=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            return cursor.getString(id);
//        }
//    }

//    private class UploadTask extends AsyncTask<String, String, String>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected void onPostExecute(String o) {
//            super.onPostExecute(o);
//            if (o.equalsIgnoreCase("true")){
//                Toast.makeText(Upload.this, "File upload", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(Upload.this, "Gagal Upload File", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            if(uploadFiles(strings[0])){
//                return "true";
//            } else {
//                return "failed";
//            }
//        }
//
//        private boolean uploadFiles(String path ){
//            File file = new File(path);
//            try {
//                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                        .addFormDataPart("Files", file.getName(), RequestBody.create(MediaType.parse("image/*"),file))
//                        .addFormDataPart("some_key", "some_value")
//                        .addFormDataPart("submit", "submit")
//                        .build();
//
//                okhttp3.Request request = new okhttp3.Request.Builder().url("http://192.168.43.209:80/api/upload").post(requestBody).build();
//
//                OkHttpClient client = new OkHttpClient();
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//                    }
//                });
//                return true;
//            } catch (Exception e){
//                e.printStackTrace();
//                return false;
//            }
//        }
//    }

    //    Membuat method logout
    private void logout() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_LOGOUT, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    SharedPreferences.Editor editor = preferences.edit();
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

//    public void uploadMultipart() {
//
//
//        //getting name for the image
//        String name = NameFile.getText().toString().trim();
//
//        //getting the actual path of the image
//        String path = FilePath.getPath(this, filePath);
//
//        if (path == null) {
//
//            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
//        } else {
//            //Uploading code
//            try {
//                String uploadId = UUID.randomUUID().toString();
//
//                //Creating a multi part request
//                new MultipartUploadRequest(this, uploadId, URL_UPLOAD)
//                        .addFileToUpload(path, "pdf") //Adding file
//                        .addParameter("name", name) //Adding text parameter to the request
//                        .setNotificationConfig(new UploadNotificationConfig())
//                        .setMaxRetries(2)
//                        .startUpload(); //Starting the upload
//
//            } catch (Exception exc) {
//                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/zip");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File ZIP"), PICK_ZIP_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_ZIP_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


//    @Override
//    public void onClick(View v) {
//        if (v == Select) {
//            showFileChooser();
//        }
//        if (v == Upload) {
////            uploadMultipart();
//        }
//    }


}

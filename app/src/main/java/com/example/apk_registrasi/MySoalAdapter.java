package com.example.apk_registrasi;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.apk_registrasi.Models.Soal;

import java.io.File;
import java.util.ArrayList;

public class MySoalAdapter extends RecyclerView.Adapter<MySoalAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    ArrayList<Soal> Soal_item;

    SharedPreferences userPref;


    public MySoalAdapter(Context context, ArrayList<Soal> soal_item) {
        this.context = context;
        this.Soal_item = soal_item;

        userPref = context.getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {
        TextView clickSoal = v.findViewById(R.id.txtSoal);
        Toast.makeText(v.getContext(), clickSoal.getText().toString(), Toast.LENGTH_SHORT);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        String id;
        TextView Soal;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Soal = itemView.findViewById(R.id.txtSoal);
            relativeLayout = itemView.findViewById(R.id.rl_download);

        }
    }

    private void download() {


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_download, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Soal list = Soal_item.get(position);

        holder.id = list.getId();
        holder.Soal.setText(list.getItem());

        holder.Soal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "http://192.168.100.174/data_soal/Android.pdf";
                Uri uri = Uri.parse(link);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                        DownloadManager.Request.NETWORK_MOBILE);
                request.setTitle("Frontend");

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                try{
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Frontend.pdf");

                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                    Toast.makeText(context, "Download Berhasil!", Toast.LENGTH_SHORT).show();
                } catch(Exception e) {
                    Toast.makeText(context, "Download Gagal", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Log.i("MySoalAdapter", "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return Soal_item.size();
    }

}
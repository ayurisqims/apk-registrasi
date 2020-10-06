package com.example.apk_registrasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apk_registrasi.Models.Soal;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {

    private Context context;
    ArrayList<Soal> Soal_item;
    SharedPreferences userPref;

    public MyAdapter2(Context context, ArrayList<Soal> soal_item) {
        this.context = context;
        Soal_item = soal_item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_download, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
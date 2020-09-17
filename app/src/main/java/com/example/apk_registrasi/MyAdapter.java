package com.example.apk_registrasi;

/* Adapter menghubungkan data dengan RecyclerView.
   Adapter menyiapkan data dan cara menampilkan data dalam view holder
*/

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apk_registrasi.Models.Anggota;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    ArrayList<Anggota> Anggota_item;

    public MyAdapter(Context context, ArrayList<Anggota> item_anggota) {
        this.context = context;
        this.Anggota_item = item_anggota;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         Object view digunakan untuk membuat object viewHolder sedangkan LayoutInflate
         digunakan untuk membuat object dari context yang merupakan
         hasil inflatelayout (menghubungkan adapter dengan layout)
        */

        View view = LayoutInflater.from(context).inflate(R.layout.activity_data_anggota, parent, false);

        return new ViewHolder(view);
    }

    @Override
         //Menghubungkan data dari list dengan item di layout (row
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        Anggota list = Anggota_item.get(position);
        //Set text berdasarkan data dari model Anggota

        holder.nama.setText(list.getNama());
        holder.nim.setText(list.getNim());
        holder.jenis_kelamin.setText(list.getJenis_kelamin());
        holder.noHp.setText(list.getNo_hp());
        holder.email.setText(list.getEmail_anggota());
        holder.sosmed.setText(list.getSosmed());
        holder.alamat.setText(list.getAlamat());
        holder.keahlian.setText(list.getKeahlian());
        //holder.BidangMinat.setText(list.getBidang_minat());
    }

    @Override
    //Berfungsi untuk mengembalikan jumlah data yang ingin ditampilkan pada RecyclerView
    public int getItemCount() {
        return Anggota_item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama, nim, jenis_kelamin, noHp, email, sosmed, alamat, keahlian, bidangMinat;
        public LinearLayout linearLayout;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
/*
            RecyclerView.ViewHolder menjelaskan tampilan data dan metadata tentnag tempatnya dalam RecyclerView.
            Setiap view holder menampung satu rangkaian data.
            View holder berisi tampilan informasi untuk menampilkan satu item dari layout item.
*/
            super(itemView);

            nama = itemView.findViewById(R.id.txtJwbNama);
            nim = itemView.findViewById(R.id.txtJwbNIM);
            jenis_kelamin = itemView.findViewById(R.id.txtJwbJenisKelamin);
            noHp= itemView.findViewById(R.id.txtJwbNoHp);
            email = itemView.findViewById(R.id.txtJwbEmail);
            sosmed = itemView.findViewById(R.id.txtJwbSosmed);
            alamat = itemView.findViewById(R.id.txtJwbAlamat);
            keahlian = itemView.findViewById(R.id.txtJwbKeahlian);
            //bidangMinat = itemView.findViewById(R.id.txtJwbBidangMinat);
            relativeLayout = itemView.findViewById(R.id.rl);

        }
    }
}
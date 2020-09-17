package com.example.apk_registrasi;

/* Adapter menghubungkan data dengan RecyclerView.
   Adapter menyiapkan data dan cara menampilkan data dalam view holder
*/

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apk_registrasi.Models.Anggota;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    List<Anggota> list_anggota;

    public MyAdapter(Context context, ArrayList<Anggota> list_anggota) {
        this.context = context;
        this.list_anggota = list_anggota;
        Log.i("autolog", "MyAdapter");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         Object view digunakan untuk membuat object viewHolder sedangkan LayoutInflate digunakan untuk membuat object dari context
         yang merupakan hasil inflatelayout (menghubungkan adapter dengan layout)
        */

        View view = LayoutInflater.from(context).inflate(R.layout.data_anggota_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
//    Menghubungkan data dari list dengan item di layout (row)
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Anggota list = list_anggota.get(position);
        //Set text berdasarkan data dari model Anggota
        holder.Nama.setText(list.getNama());
        holder.Nim.setText(list.getNim());
        holder.JenisKelamin.setText(list.getJenis_kelamin());
        holder.NoHp.setText(list.getNo_hp());
        holder.Email.setText(list.getEmail_anggota());
        holder.Sosmed.setText(list.getSosmed());
        holder.Alamat.setText(list.getAlamat());
        holder.Keahlian.setText(list.getKeahlian());
//      holder.BidangMinat.setText(list.getBidang_minat());

    }

    @Override
//    Berfungsi untuk mengembalikan jumlah data yang ingin ditampilkan pada RecyclerView
    public int getItemCount() {
        return list_anggota.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Nama, Nim, JenisKelamin, NoHp, Email, Sosmed, Alamat, Keahlian, BidangMinat;
        public LinearLayout linearLayout;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            Log.i(TAG, "ViewHolder");

            Nama = itemView.findViewById(R.id.txtNama);
            Nim = itemView.findViewById(R.id.txtNIM);
            JenisKelamin = itemView.findViewById(R.id.txtJenisKelamin);
            NoHp = itemView.findViewById(R.id.txtNoHp);
            Email = itemView.findViewById(R.id.txtEmail);
            Sosmed = itemView.findViewById(R.id.txtSosmed);
            Alamat = itemView.findViewById(R.id.txtAlamat);
            Keahlian = itemView.findViewById(R.id.txtKeahlian);
            BidangMinat = itemView.findViewById(R.id.txtBidangMinat);
            relativeLayout = itemView.findViewById(R.id.rl);
        }
    }
}

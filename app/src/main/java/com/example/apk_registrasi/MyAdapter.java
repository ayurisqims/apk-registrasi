package com.example.apk_registrasi;

/* Adapter menghubungkan data dengan RecyclerView.
   Adapter menyiapkan data dan cara menampilkan data dalam view holder
*/

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apk_registrasi.Models.Anggota;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context mcontext;
    ArrayList<Anggota> Anggota_item;
//    SharedPreferences sharedPreferences;


    public MyAdapter(Context context, ArrayList<Anggota> item_anggota) {
        this.mcontext = context;
        this.Anggota_item = item_anggota;
//      sharedPreferences = mcontext.getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /*
            Setiap view holder menampung satu rangkaian data.
            View holder berisi tampilan informasi untuk menampilkan satu item dari layout item.
        */
        String id;
        TextView Nama, Nim, JenisKelamin, NoHp, Email, Sosmed, Alamat, Keahlian, BidangMinat;
        Button Edit, Hapus;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View ItemView) {

            super(ItemView);

            Nama = ItemView.findViewById(R.id.txtJwbNama);
            Nim = ItemView.findViewById(R.id.txtJwbNIM);
            JenisKelamin = ItemView.findViewById(R.id.txtJwbJenisKelamin);
            NoHp= ItemView.findViewById(R.id.txtJwbNoHp);
            Email = ItemView.findViewById(R.id.txtJwbEmail);
            Sosmed = ItemView.findViewById(R.id.txtJwbSosmed);
            Alamat = ItemView.findViewById(R.id.txtJwbAlamat);
            Keahlian = ItemView.findViewById(R.id.txtJwbKeahlian);
            //bidangMinat = ItemView.findViewById(R.id.txtJwbBidangMinat);
            relativeLayout = ItemView.findViewById(R.id.rl);
            Edit = ItemView.findViewById(R.id.btnEdit);
            Hapus = ItemView.findViewById(R.id.btnHapus);
            this.Edit = itemView.findViewById(R.id.btnEditAnggota);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         Object view digunakan untuk membuat object viewHolder sedangkan LayoutInflate
         digunakan untuk membuat object dari context yang merupakan
         hasil inflatelayout (menghubungkan adapter dengan layout)
        */

        View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_data_anggota, parent, false);
        return new ViewHolder(view);

    }

    @Override
//         method ini dipanggil ketika RecyclerView ingin mengisi data ke view
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {


        Anggota list = Anggota_item.get(position);
        //Set text berdasarkan data dari model Anggota

        holder.Nama.setText(Anggota_item.get(position).getNama());
        Log.i("MyAdapter", "onBindViewHolder:nama ");

        holder.id = list.getId();
//        holder.Nama.setText(list.getNama());
        holder.Nim.setText(list.getNim());
        holder.JenisKelamin.setText(list.getJenis_kelamin());
        holder.NoHp.setText(list.getNo_hp());
        holder.Email.setText(list.getEmail_anggota());
        holder.Sosmed.setText(list.getSosmed());
        holder.Alamat.setText(list.getAlamat());
        holder.Keahlian.setText(list.getKeahlian());
        Log.i("MyAdapter", "Intent ");

        //holder.BidangMinat.setText(list.getBidang_minat());

//        if (list.getNama().getId()==userPref.getInt("id", 0)){
//            holder.Edit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//        bidangminat = "";
//        if(btnAndroid.isChecked()) {
//            bidangminat = bidangminat+",android";
//        }
//        if(btnWeb.isChecked()) {
//            bidangminat = bidangminat+",web"
//        }

//                }
//            });
//        }

        holder.Edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mcontext, Edit_data_anggota.class);


            intent.putExtra("id", list.getId());
            intent.putExtra("position", position);
            intent.putExtra("nama", list.getNama());
            intent.putExtra("nim", list.getNim());
            intent.putExtra("jenis_kelamin", list.getJenis_kelamin());
            intent.putExtra("no_hp", list.getNo_hp());
            intent.putExtra("email_anggota", list.getEmail_anggota());
            intent.putExtra("sosmed", list.getSosmed());
            intent.putExtra("alamat", list.getAlamat());
            intent.putExtra("keahlian", list.getKeahlian());
//          intent.putExtra("bidang_minat", bidang_minat);
            Log.i("MyAdapter", "onClick: intent edit ");
            mcontext.startActivity(intent);
        }
    });
    }

    @Override
//  Berfungsi untuk mengembalikan jumlah data yang ingin ditampilkan pada RecyclerView
    public int getItemCount() {
        return Anggota_item.size();
    }


}
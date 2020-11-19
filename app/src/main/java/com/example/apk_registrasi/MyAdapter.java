package com.example.apk_registrasi;

/* Adapter menghubungkan data dengan RecyclerView.
   Adapter menyiapkan data dan cara menampilkan data dalam view holder
*/

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_registrasi.Models.Anggota;
import com.example.apk_registrasi.Utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context mcontext;
    ArrayList<Anggota> Anggota_item;

    public MyAdapter(Context context, ArrayList<Anggota> item_anggota) {
        this.mcontext = context;
        this.Anggota_item = item_anggota;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /*
            Setiap vie w holder menampung satu rangkaian data.
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
            BidangMinat = ItemView.findViewById(R.id.txtJwbBidangMinat);
            relativeLayout = ItemView.findViewById(R.id.rl);
            Edit = ItemView.findViewById(R.id.btnEdit);
            Hapus = ItemView.findViewById(R.id.btnHapusAnggota);
            this.Edit = itemView.findViewById(R.id.btnEditAnggota);
            this.Hapus = itemView.findViewById(R.id.btnHapusAnggota);


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

        holder.id = list.getId();
        holder.Nama.setText(list.getNama());
        holder.Nim.setText(list.getNim());
        holder.JenisKelamin.setText(list.getJenis_kelamin());
        holder.NoHp.setText(list.getNo_hp());
        holder.Email.setText(list.getEmail_anggota());
        holder.Sosmed.setText(list.getSosmed());
        holder.Alamat.setText(list.getAlamat());
        holder.Keahlian.setText(list.getKeahlian());
        holder.BidangMinat.setText(list.getBidang_minat());
        Log.i("MyAdapter", "Holder : "+list.getBidang_minat());

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
            intent.putExtra("bidang_minat", list.getBidang_minat());
            Log.i("MyAdapter", "onClick: intent edit "+ list.getId());
            mcontext.startActivity(intent);
        }});

        holder.Hapus.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                deletePost(list.getId(), position);

            }
        });

    }

    private void deletePost(String id, int position) {

        SharedPreferences userPref = mcontext.getSharedPreferences("user", Context.MODE_PRIVATE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_DELETE_ANGGOTA, response -> {

            try{
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){

                    Anggota_item.remove(position);
                    notifyItemChanged(position);
                    notifyDataSetChanged();
//                                Intent intent = new Intent(mcontext, Data_anggota.class);
//                                intent.setType(Settings.ACTION_SYNC_SETTINGS);
//                                mcontext.startActivity(intent);
                                Log.i("MyAdpter", "position");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> {

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String,String> params = new HashMap<>();
                params.put("Authorization", "Bearer"+token);
                Log.i("MyAdapter", "getHeader: ");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                Anggota list = Anggota_item.get(position);
                params.put("id", list.getId());
                Log.i("MyAdapter","onClick: "+params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);
        Log.i("MyAdapter", "Volley: ");

    }


    @Override
//  Berfungsi untuk mengembalikan jumlah data yang ingin ditampilkan pada RecyclerView
    public int getItemCount() {
        return Anggota_item.size();
    }


}
package com.example.apk_registrasi.auth;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {


    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginRequest(
            @Field("email") String email,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registerRequest(
            @Field("universitas") String universitas,
            @Field("fakultas") String fakultas,
            @Field("prodi") String prodi,
            @Field("alamat_univ") String alamat_univ,
            @Field("kelompok") String kelompok,
            @Field("jumlah_anggota") String jumlah_anggota,
            @Field("periode_mulai") String periode_mulai);
}

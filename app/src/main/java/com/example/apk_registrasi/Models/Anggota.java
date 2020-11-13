package com.example.apk_registrasi.Models;

import android.widget.TextView;

public class Anggota {

    String id;
    String nama, nim, jenis_kelamin, no_hp, email_anggota, sosmed, alamat, keahlian, bidang_minat;

    public Anggota(String id, String nama, String nim, String jenis_kelamin, String no_hp,
                   String email_anggota, String sosmed, String alamat, String keahlian, String bidang_minat) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.jenis_kelamin = jenis_kelamin;
        this.no_hp = no_hp;
        this.email_anggota = email_anggota;
        this.sosmed = sosmed;
        this.alamat = alamat;
        this.keahlian = keahlian;
        this.bidang_minat = bidang_minat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail_anggota() {
        return email_anggota;
    }

    public void setEmail_anggota(String email_anggota) {
        this.email_anggota = email_anggota;
    }

    public String getSosmed() {
        return sosmed;
    }

    public void setSosmed(String sosmed) {
        this.sosmed = sosmed;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    public String getBidang_minat() {
        return bidang_minat;
    }

    public void setBidang_minat(String bidang_minat) {
        this.bidang_minat = bidang_minat;
    }
}

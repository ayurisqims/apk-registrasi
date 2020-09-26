package com.example.apk_registrasi.Models;

public class Kelompok {

    String id;
    String universitas, fakultas, prodi, alamat_univ, kelompok, jumlah_anggota,
            periode_mulai, periode_akhir, nama_ketua;

    public Kelompok(String id, String universitas, String fakultas, String prodi, String alamat_univ,
                    String kelompok, String jumlah_anggota, String periode_mulai, String periode_akhir, String nama_ketua) {
        this.id = id;
        this.universitas = universitas;
        this.fakultas = fakultas;
        this.prodi = prodi;
        this.alamat_univ = alamat_univ;
        this.kelompok = kelompok;
        this.jumlah_anggota = jumlah_anggota;
        this.periode_mulai = periode_mulai;
        this.periode_akhir = periode_akhir;
        this.nama_ketua = nama_ketua;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniversitas() {
        return universitas;
    }

    public void setUniversitas(String universitas) {
        this.universitas = universitas;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getAlamat_univ() {
        return alamat_univ;
    }

    public void setAlamat_univ(String alamat_univ) {
        this.alamat_univ = alamat_univ;
    }

    public String getKelompok() {
        return kelompok;
    }

    public void setKelompok(String kelompok) {
        this.kelompok = kelompok;
    }

    public String getJumlah_anggota() {
        return jumlah_anggota;
    }

    public void setJumlah_anggota(String jumlah_anggota) {
        this.jumlah_anggota = jumlah_anggota;
    }

    public String getPeriode_mulai() {
        return periode_mulai;
    }

    public void setPeriode_mulai(String periode_mulai) {
        this.periode_mulai = periode_mulai;
    }

    public String getPeriode_akhir() {
        return periode_akhir;
    }

    public void setPeriode_akhir(String periode_akhir) {
        this.periode_akhir = periode_akhir;
    }

    public String getNama_ketua() {
        return nama_ketua;
    }

    public void setNama_ketua(String nama_ketua) {
        this.nama_ketua = nama_ketua;
    }
}

package com.example.apk_registrasi.Models;

public class Soal {

    String id, item, keterangan;

    public Soal(String id, String item, String keterangan) {
        this.id = id;
        this.item = item;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}

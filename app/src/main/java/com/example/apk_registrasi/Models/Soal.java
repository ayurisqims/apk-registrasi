package com.example.apk_registrasi.Models;

public class Soal {

    String id, item;

    public Soal(String id, String item) {
        this.id = id;
        this.item = item;
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

}

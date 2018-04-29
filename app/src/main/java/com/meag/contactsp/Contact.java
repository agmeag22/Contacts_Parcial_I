package com.meag.contactsp;

import android.net.Uri;

public class Contact {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private boolean favmarker;
    private Uri img;

    public Contact(String id, String name, String email, String phone, String address, boolean favmarker, Uri img) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.favmarker = favmarker;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFavmarker() {
        return favmarker;
    }

    public void setFavmarker(boolean favmarker) {
        this.favmarker = favmarker;
    }

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }
}
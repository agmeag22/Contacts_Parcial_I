package com.meag.contactsp.Objects;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

public class Contact implements Serializable {
    private String id;
    private ArrayList<String> name;
    private ArrayList<String> email;
    private LinkedHashMap<String,String> phone;
    private String address;
    private boolean favmarker;
    private Date birthdate;
    private String img;

    public Contact(String id, ArrayList<String> name, ArrayList<String> email, LinkedHashMap<String, String> phone, String address, boolean favmarker, Date birthdate, String img) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.favmarker = favmarker;
        this.birthdate = birthdate;
        this.img = img;
    }

    public String getId() {
        return id;    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    public LinkedHashMap<String, String> getPhone() {
        return phone;
    }

    public void setPhone(LinkedHashMap<String, String> phone) {
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
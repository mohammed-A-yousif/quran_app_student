package com.example.quranappstudent;

/**
 * Created by ravi on 16/11/17.
 */

public class Contact {
    String name;
    String phone;
    String date;


    public Contact(String name, String phone, String date) {
        this.name = name;
        this.phone = phone;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }
}

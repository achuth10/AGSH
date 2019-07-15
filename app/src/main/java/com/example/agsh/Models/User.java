package com.example.agsh.Models;

import android.widget.EditText;

public class User {

    private String id,name,phonenumber,email;
User()
{

}
    public User(String emailtxt, String name, String number) {
        this.name=name;
        this.phonenumber=number;
        this.email=emailtxt;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

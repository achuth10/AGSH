package com.example.agsh.Models;

import android.widget.EditText;

public class User {

    private String id;
    private String name;
    private String phonenumber;
    private String email;

    public String getAccbal() {
        return accbal;
    }

    public void setAccbal(String accbal) {
        this.accbal = accbal;
    }

    private String accbal;
User()
{

}
    public User(String emailtxt, String name, String number,String accbal,String id ) {
        this.name=name;
        this.phonenumber=number;
        this.email=emailtxt;
        this.accbal = accbal;
        this.id=id;
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

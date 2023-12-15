package com.example.chathub;

import androidx.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

public class User {
    String uid;
    String email;
    String name;

    private User(){

    }

    public User(String uid,String email ,String name  ){
        this.uid = uid;
        this.email = email;
        this.name = name;
    }

    @PropertyName("uid") // Exclude UID from serialization
    public String getUid() {
        return uid;
    }

    @PropertyName("uid")// Exclude UID from serialization
    public void setUid(String uid) {
        this.uid = uid;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

}

package com.example.lab;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null && firebaseUser.isEmailVerified() && Login.FLAG==true )
        {
            Intent in = new Intent(getApplicationContext(),mainDrawrer.class);
            //Constants.FLAG_LOGOUT=false;
            startActivity(in);
        }
    }
}

package com.example.lab;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Constants {
    public static final String DATABASE_PATH_UPLOADS = "TestDetails/TestDetails";
    public static String getCurrentUrl()
    {
        String url ="";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            url = firebaseUser.getEmail();
            url = url.substring(0,url.indexOf("@"));
        }
        return url;
    }
}

package com.example.lab;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Constants {
    public static final String DATABASE_PATH_UPLOADS = "TestDetails/TestDetails";
    public static final String DATABASE_LOGIN="LoginDetails";
    public static final String DATABASE_ORDER="OrderDetails";
    public static final String DATABASE_TIMESLOT="OrderOccupied";
    public static final String DATABASE_ORDER_ITEM_DETAILS="OrderItemDetails";
    public static final String DATABASE_ORDER_HISTORY ="OrderHistory";
    public static final String DATABASE_RATINGS="Ratings";
    public static final String DATABASE_FEEDBACK="Feedback";
    public static final String TIME_SLOT_FIRST ="9:00am - 11:00am";
    public static final String TIME_SLOT_SECOND ="12:00pm - 1:00pm";
    public static final String TIME_SLOT_THIRD ="2:00pm - 4:00pm";
    public static final String TIME_SLOT_FOURTH ="5:00pm - 7:00pm";


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
    public  static  String completeUrl()
    {
        String url ="";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            url = firebaseUser.getEmail();
        }
        return url;
    }
}

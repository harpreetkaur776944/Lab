package com.example.lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Cart extends SQLiteOpenHelper {

    public Cart(Context context) {
        super(context,"LabCartItems",null,11);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table cartItems(itemCode text,itemPrice text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addData(String itemName, String itemPrice)
    {
        SQLiteDatabase database =  getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("itemName",itemName);
        cv.put("itemPrice",itemPrice);

        database.insert("cartItems",null,cv);
        return  true;
    }


    public Cursor viewData()
    {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("cartItems",null,null,null,null,null,null);
        return  cursor;

    }

    public boolean updateData(String UserName, String Password)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Password",Password);
        String[] ar = { UserName };
        database.update("register",cv,"UserName=?",ar);

        return true;
    }

    public boolean deleteData(String itemCode)
    {
        SQLiteDatabase database = getWritableDatabase();
        String[] ar = { itemCode };
        database.delete("cartItems","UserName=?",ar);
        return  true;
    }
}

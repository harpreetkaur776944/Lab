package com.example.lab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Temp extends AppCompatActivity {

    TextView tv1,tv2;

    Button b;
    EditText name,details,preTestInfo,reportAvailable,testUsuage,category,price,testCode,offer,type;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        b= findViewById(R.id.button5);
        tv1 = findViewById(R.id.textView17);
        tv2= findViewById(R.id.textView18);



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = findViewById(R.id.editText2);
                details = findViewById(R.id.editText3);
                preTestInfo = findViewById(R.id.editText4);
                reportAvailable = findViewById(R.id.editText5);
                testUsuage = findViewById(R.id.editText6);
                category = findViewById(R.id.editText7);
                price = findViewById(R.id.editText8);
                testCode = findViewById(R.id.editTextFeedName);
                offer = findViewById(R.id.editTextOffEr);
                type = findViewById(R.id.editTextTyPE);

               databaseReference = FirebaseDatabase.getInstance().getReference("TestDetails");

               // databaseReference = firebaseDatabase.getReference();
                String Name = name.getText().toString().trim();
                String Details = details.getText().toString().trim();
                String PreTestInfo = preTestInfo.getText().toString().trim();
                String ReportAvailable = reportAvailable.getText().toString().trim();
                String TestUsuage = testUsuage.getText().toString().trim();
                String Category = category.getText().toString().trim();
                String Price = price.getText().toString().trim();
                String TestCode = testCode.getText().toString().trim();
                String Offer = offer.getText().toString().trim();
                String Type = type.getText().toString().trim();

                Test test = new Test(TestCode,Name,Details,PreTestInfo,ReportAvailable,TestUsuage,Category,Price,Type,Offer);
                databaseReference.child("TestDetails").push().setValue(test);
                name.setText("");
                testCode.setText("");
                details.setText("");
                preTestInfo.setText("");
                reportAvailable.setText("");
                testUsuage.setText("");
                category.setText("");
                price.setText("");
                offer.setText("");
                type.setText("");
                Toast.makeText(getApplicationContext(),"Data added ",Toast.LENGTH_LONG).show();



            }
        });

    }
}

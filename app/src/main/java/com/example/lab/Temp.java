package com.example.lab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Temp extends AppCompatActivity {

    TextView tv1,tv2;

    Button b;
    EditText name,details,preTestInfo,reportAvailable,testUsuage,category,price,testCode;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        b= findViewById(R.id.button5);
        tv1 = findViewById(R.id.textView17);
        tv2= findViewById(R.id.textView18);
        databaseReference = FirebaseDatabase.getInstance().getReference("TestDetails");
        Query query = databaseReference.child("TestDetails");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Map map = (Map) ds.getValue();
                    String n = map.get("Name").toString();
                    String p = map.get("Details").toString();
                    tv1.setText(n);
                    tv2.setText(p);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = findViewById(R.id.editText);
                details = findViewById(R.id.editText2);
                preTestInfo = findViewById(R.id.editText3);
                reportAvailable = findViewById(R.id.editText3);
                testUsuage = findViewById(R.id.editText4);
                category = findViewById(R.id.editText5);
                price = findViewById(R.id.editText6);
                testCode = findViewById(R.id.editText7);
                databaseReference = firebaseDatabase.getReference();

                String Name = name.getText().toString().trim();
                String Details = details.getText().toString().trim();
                String PreTestInfo = preTestInfo.getText().toString().trim();
                String ReportAvailable = reportAvailable.getText().toString().trim();
                String TestUsuage = testUsuage.getText().toString().trim();
                String Category = category.getText().toString().trim();
                String Price = price.getText().toString().trim();
                String TestCode = testCode.getText().toString().trim();

                Test test = new Test(TestCode,Name,Details,PreTestInfo,ReportAvailable,TestUsuage,Category,Price);
                databaseReference.child("TestDetails").push().setValue(test);
                name.setText("");
                testCode.setText("");
                details.setText("");
                preTestInfo.setText("");
                reportAvailable.setText("");
                testUsuage.setText("");
                category.setText("");
                price.setText("");
                Toast.makeText(getApplicationContext(),"Data added ",Toast.LENGTH_LONG).show();


                //Intent in = new Intent(getApplicationContext(),TestsDetails.class);
                //startActivity(in);
            }
        });

    }
}

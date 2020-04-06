package com.example.lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewCartSummary extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DatabaseReference databaseReference;
    List<Test> testList ;
    ProgressDialog progressDialog;

    TextView charges,extraCharges,totalCharges;
    Button addMoreItems,checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart_summary);

        charges = findViewById(R.id.textView24);
        extraCharges = findViewById(R.id.textView26);
        totalCharges = findViewById(R.id.textView30);
        addMoreItems = findViewById(R.id.button2);
        checkout = findViewById(R.id.button3);

        charges.setText(charges.getText()+" 0.0");
        extraCharges.setText(extraCharges.getText()+" 0.0");
        totalCharges.setText(totalCharges.getText()+" 0.0");

        recyclerView = findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        testList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart List");
        databaseReference.child("User View").child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Test test = ds.getValue(Test.class);
                    testList.add(test);
                }
                adapter = new TestsAdapter(getApplicationContext(), testList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
}

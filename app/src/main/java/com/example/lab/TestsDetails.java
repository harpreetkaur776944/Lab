package com.example.lab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TestsDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DatabaseReference databaseReference;
    List<Test> testList ;
    List<CartItems> cartItemsList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_details);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

       testList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("TestDetails/TestDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {
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
    private void getPriceOfCartImtems()
    {
        cartItemsList = new ArrayList<>();
        String url ="";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null) {
            url = firebaseUser.getEmail();
            url = url.substring(0, url.indexOf("@"));

            DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(url).child("Products");
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        CartItems cartItems = ds.getValue(CartItems.class);
                        cartItemsList.add(cartItems);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}

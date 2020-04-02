package com.example.lab;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TestsDetails extends AppCompatActivity {

    private static final String DATABASE_PATH_UPLOADS = "";
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DatabaseReference databaseReference;
    List<Test> testList ;
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

        databaseReference = FirebaseDatabase.getInstance().getReference("TestDetails");

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

}

package com.example.lab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class SelectTestCart extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DatabaseReference databaseReference;
    List<Test> testList1 ;
    ProgressDialog progressDialog;
    String code;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_test_cart);

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.toolbar4);

        code = getIntent().getStringExtra("testcode");

        progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
       // progressDialog.show();

        testList1 = new ArrayList<>();

        for (Test test:TestsDetails.testList) {
            if(test.getTestCode().equals(code))
                testList1.add(test);
        }

        adapter = new SelectTestCartAdapter(getApplicationContext(), testList1);
        recyclerView.setAdapter(adapter);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),TestsDetails.class);
                startActivity(in);
            }
        });

/*
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Test test = ds.getValue(Test.class);
                    if(test.getTestCode().equals(code)) {
                        testList.add(test);
                    }
                }
                adapter = new SelectTestCartAdapter(getApplicationContext(), testList);
                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
*/
    }

}

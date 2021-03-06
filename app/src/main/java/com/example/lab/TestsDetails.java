package com.example.lab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DatabaseReference databaseReference;
    TestsAdapter myAdapter;

    ProgressDialog progressDialog;
    TextView viewCartSummary;
    String url = Constants.getCurrentUrl();
    EditText search;

    public  static  List<CartItems> cartItemsList= new ArrayList<>();
    public static List<Test> testList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_details);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewCartSummary = findViewById(R.id.textView20);
        search = findViewById(R.id.editTextSearch);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
               // filter(s.toString());
            }
        });

        cartItemsList.clear();
        testList.clear();

        DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("CartList").child(url).child("Products");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CartItems cartItems = ds.getValue(CartItems.class);
                    cartItemsList.add(cartItems);
                  //  Log.d("CHECK",cartItems.getItemCode());
                }
                viewCartSummary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cartItemsList.isEmpty())
                        {
                            Intent in = new Intent(getApplicationContext(),CartIsEmpty.class);
                            startActivity(in);
                        }
                        else
                        {
                            Intent in = new Intent(getApplicationContext(),ViewCartSummary.class);
                            startActivity(in);
                        }

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

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
    private void filter(String text)
    {
        List<Test> filteredList = new ArrayList<>();
        for(Test test : testList)
        {
            if(test.getName().toLowerCase().trim().contains(text.toLowerCase().trim()))
            {
                filteredList.add(test);
            }
        }
        adapter = new TestsAdapter(getApplicationContext(),filteredList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(getApplicationContext(),mainDrawrer.class);
        startActivity(in);
    }
}

package com.example.lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.lab.TestsDetails.cartItemsList;

public class ViewCartSummary extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    TextView charges,extraCharges,totalCharges;
    Button addMoreItems,checkout;
    List<CartItems> cartList;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart_summary);

        charges = findViewById(R.id.textViewCharges);
        extraCharges = findViewById(R.id.textViewExtraCharges);
        totalCharges = findViewById(R.id.textViewTotal);
        addMoreItems = findViewById(R.id.button2);
        checkout = findViewById(R.id.button3);
        cartList= new ArrayList<>();;

        recyclerView = findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference= FirebaseDatabase.getInstance().getReference().child("CartList").child(Constants.getCurrentUrl()).child("Products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CartItems cartItems = ds.getValue(CartItems.class);
                    cartList.add(cartItems);
                    Log.d("TEST CHECK",cartItems.getItemCode());
                }

                if(!cartList.isEmpty()) {
                    Log.d("TEST CHECK","NotEmpty");
                    charges.setText(getTotalCharges(cartList) + "");
                    extraCharges.setText("50");
                    int temp = getTotalCharges(cartList) + 50;
                    totalCharges.setText(temp + "");
                }
                else
                {
                    Log.d("TEST CHECK","Empty");
                    Intent in = new Intent(getApplicationContext(),CartIsEmpty.class);
                    startActivity(in);
                }

                adapter = new ViewCartSummaryAdapter(getApplicationContext(), cartList);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private int getTotalCharges(List<CartItems> cartItemsList)
    {
        int price =0;
        for (CartItems cart:cartItemsList) {
            price += Integer.parseInt(cart.getItemPrice());
        }
        return  price;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(getApplicationContext(), TestsDetails.class);
        startActivity(in);
    }
}

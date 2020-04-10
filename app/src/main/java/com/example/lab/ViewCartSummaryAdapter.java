package com.example.lab;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class ViewCartSummaryAdapter extends RecyclerView.Adapter<ViewCartSummaryAdapter.ViewHolder> {

    Context context;
    List<CartItems> cartItemsList;
    String url = Constants.getCurrentUrl();


    public ViewCartSummaryAdapter(Context context, List<CartItems> cartItemsList) {
        this.context = context;
        this.cartItemsList = cartItemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_summary,parent,false);
        ViewCartSummaryAdapter.ViewHolder viewHolder = new ViewCartSummaryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CartItems cartItems = cartItemsList.get(position);
        holder.name.setText(cartItems.getItemName());
        holder.price.setText(cartItems.getItemPrice());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Do you really want to delete? ");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       deleteCartItem(cartItems,position);
                       notifyDataSetChanged();
                    }
                });


                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price;
        ImageView delete;
        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.textView21);
            price = view.findViewById(R.id.textView22);
            delete = view.findViewById(R.id.imageView5);
        }
    }
    private void deleteCartItem(final CartItems cartItems, final int position)
    {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        Query query = databaseReference.child("CartList").child(url).child("Products");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CartItems cartItems1 = ds.getValue(CartItems.class);
                    if(cartItems.getItemCode().equals(cartItems1.getItemCode()))
                    {
                        ds.getRef().removeValue();
                        cartItemsList.remove(position);
                        notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

package com.example.lab;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SelectTestCartAdapter extends RecyclerView.Adapter<SelectTestCartAdapter.ViewHolder>{

    Context context;
    List<Test> testList;
    String code;
    String price;
    String url;
  static  List<CartItems> cartItemsList = new ArrayList<>();
    public SelectTestCartAdapter(Context context, List<Test> testList) {
        this.context = context;
        this.testList = testList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_test_layout,parent,false);
        SelectTestCartAdapter.ViewHolder viewHolder = new SelectTestCartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Test test = testList.get(position);
        code = test.getTestCode();
        price = test.getPrice();
        holder.testName.setText(test.getName());
        holder.preTest.setText(test.getPreTestInformation());
        holder.report.setText(test.getReportAvailability());
        holder.category.setText(test.getCategory());
        holder.testUsage.setText(test.getTestUsuage());
        holder.price.setText(holder.price.getText()+" "+test.getPrice());
        holder.addedToCart.setText("Added to cart");


        getCurrentUrl();

        DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("CartList").child(url).child("Products");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CartItems cartItems = ds.getValue(CartItems.class);
                    cartItemsList.add(cartItems);
                }
                if (cartItemsList.isEmpty()) {
                    holder.addedToCart.setText("");
                    holder.addToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addingToCartList();
                            holder.addedToCart.setText("Added To Cart");
                            holder.addToCart.setVisibility(View.GONE);
                        }
                    });
                } else {
                    for (CartItems cart : cartItemsList) {
                        if (cart.getItemCode().equals(code)) {
                            holder.addedToCart.setText("Added To Cart");
                            holder.addToCart.setVisibility(View.GONE);
                            break;
                        } else {
                            holder.addedToCart.setText("");
                            holder.addToCart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addingToCartList();
                                    holder.addedToCart.setText("Added To Cart");
                                    holder.addToCart.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    @Override
    public int getItemCount() {
        return testList.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView preTest,report,testUsage,category,price,addedToCart,testName;
        ImageView addToCart;

        public ViewHolder(@NonNull View view) {
            super(view);
            testName = view.findViewById(R.id.textView5);
            preTest = view.findViewById(R.id.textView7);
            report = view.findViewById(R.id.textView9);
            testUsage = view.findViewById(R.id.textView11);
            category = view.findViewById(R.id.textView13);
            price = view.findViewById(R.id.textView15);
            addedToCart = view.findViewById(R.id.textView16);
            addToCart = view.findViewById(R.id.imageView4);

        }

    }

    private boolean checkItemsInCart(final String code)
    {
        final List<CartItems> cartItemsList = new ArrayList<>();
        getCurrentUrl();
        final boolean[] flag = {false};

        DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("CartList").child(url).child("Products");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CartItems cartItems = ds.getValue(CartItems.class);
                    cartItemsList.add(cartItems);
                }
                for (CartItems cart:cartItemsList) {
                    if(cart.getItemCode().equals(code))
                    {
                        flag[0] = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("CHECKING",flag[0]+"");
        return flag[0];
    }
    private void getCurrentUrl()
    {
        url ="";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            url = firebaseUser.getEmail();
            url = url.substring(0,url.indexOf("@"));
        }
    }
    private void addingToCartList()
    {
        String saveCurrentDate, saveCurrentTime;
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            CartItems cart = new CartItems(code, saveCurrentTime, saveCurrentDate, price);

            DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("CartList");
                    dref.child(url).child("Products").push().setValue(cart);
    }

    private boolean addItemsInCart(Context context)
    {
        Cart cart = new Cart(context);
        List<String> cartList = new ArrayList<>();
        Cursor cursor =cart.viewData();
        if(cursor.moveToFirst())
        {
            do {
                String itemCode=cursor.getString(cursor.getColumnIndex("itemCode"));
                cartList.add(itemCode);
            }while (cursor.moveToNext());
        }

        if(!cartList.contains(code))
        {
            boolean flag =cart.addData(code,price);
            {
                if(flag) {
                    Toast.makeText(context,"added",Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }
       return false;
    }
}

package com.example.lab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    TextView charges,extraCharges,totalCharges;
    Button addMoreItems,checkout;
    List<CartItems> cartList;
    String items;
    DatabaseReference databaseReference;

    public ViewCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewCartFragment newInstance(String param1, String param2) {
        ViewCartFragment fragment = new ViewCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      // List<CartItems> cartItems = TestsDetails.cartItemsList;
        View view = inflater.inflate(R.layout.fragment_view_cart, container, false);

        charges = view.findViewById(R.id.textViewCharges);
        extraCharges = view.findViewById(R.id.textViewExtraCharges);
        totalCharges = view .findViewById(R.id.textViewTotal);
        addMoreItems = view.findViewById(R.id.button2);
        checkout = view.findViewById(R.id.button3);
        cartList= new ArrayList<>();


        recyclerView = view.findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        databaseReference= FirebaseDatabase.getInstance().getReference().child("CartList").child(Constants.getCurrentUrl()).child("Products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CartItems cartItems = ds.getValue(CartItems.class);
                    cartList.add(cartItems);
                    items += cartItems.itemCode+" ";
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
                    Intent in = new Intent(getActivity(),CartIsEmpty.class);
                    startActivity(in);
                }

                adapter = new ViewCartSummaryAdapter(getActivity(), cartList);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),OrderDetails.class);
                String amt = totalCharges.getText().toString().trim();
                in.putExtra("Items",items+"");
                Log.d("it",items);
                in.putExtra("Amount",amt);
                startActivity(in);
            }
        });

        addMoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),TestsDetails.class);
                startActivity(in);
            }
        });
        return view;
    }



    private int getTotalCharges(List<CartItems> cartItemsList)
    {
        int price =0;
        for (CartItems cart:cartItemsList) {
            price += Integer.parseInt(cart.getItemPrice());
        }
        return  price;
    }

}

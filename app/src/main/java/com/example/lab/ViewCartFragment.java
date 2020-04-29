package com.example.lab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    DatabaseReference databaseReference;
    List<CartItems> cartItems;

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
        cartItems = TestsDetails.cartItemsList;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      // List<CartItems> cartItems = TestsDetails.cartItemsList;

        if(cartItems.size()==0)
        {
            View view = inflater.inflate(R.layout.activity_cart_is_empty, container, false);
            return view;
        }
        else
        {
            View view = inflater.inflate(R.layout.activity_view_cart_summary, container, false);
            Intent intent = new Intent(getActivity(),ViewCartSummary.class);
            startActivity(intent);
            return view;
        }
    }

}

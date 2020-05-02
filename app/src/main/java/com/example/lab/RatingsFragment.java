package com.example.lab;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.SmileRating;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseReference databaseReference;

    public RatingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RatingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RatingsFragment newInstance(String param1, String param2) {
        RatingsFragment fragment = new RatingsFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ratings, container, false);
        TextView textView = view.findViewById(R.id.textViewRate);
        SmileRating smileRating = view.findViewById(R.id.smile_rating);
        Button cancel,submit;
        cancel = view.findViewById(R.id.buttonRateCancel);
        submit= view.findViewById(R.id.buttonRateSubmit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),mainDrawrer.class));
            }
        });
        final String[] rate = {"1"};
        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                rate[0] = level+"";
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_RATINGS);
                Toast.makeText(getActivity(),"Thanks for ratings",Toast.LENGTH_LONG).show();
                Log.d("Rating",rate[0]);
                RatingsDetails ratingsDetails = new RatingsDetails(Constants.completeUrl(),rate[0]);
                databaseReference.push().setValue(ratingsDetails);
                startActivity(new Intent(v.getContext(),mainDrawrer.class));
            }
        });

        return view;
    }

}

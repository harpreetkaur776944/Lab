package com.example.lab.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lab.MapsActivity;
import com.example.lab.R;
import com.example.lab.TestsDetails;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ViewFlipper viewFlipper;
    Button locateUs,bookNow;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewFlipper = view.findViewById(R.id.image_flipper);
        locateUs = view.findViewById(R.id.buttonLocateUs);
        bookNow =view.findViewById(R.id.buttonBookNow);
        int images[] ={R.drawable.blue_one,R.drawable.blue_two,R.drawable.blue_three,R.drawable.blue_four,R.drawable.thank_docs};
        for(int image:images)
        {
            flipperImages(image);
        }

        locateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MapsActivity.class);
                startActivity(intent);
            }
        });
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TestsDetails.class);
                startActivity(intent);
            }
        });


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return view;
    }
    public void flipperImages(int image) {
        ImageView imageView = new ImageView(this.getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this.getContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this.getContext(), android.R.anim.slide_out_right);
    }

}

package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

public class Payment extends AppCompatActivity implements PaymentResultListener {

    ImageView razorpay;
    public static final String TAG="ERRORPAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Checkout.preload(getApplicationContext());

        razorpay = findViewById(R.id.imageViewRazorpay);
        razorpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }


    public void startPayment() {

        final   Checkout checkout = new Checkout();

        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();
            options.put("name", "Laboratory");
            options.put("description", "Test Order");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //   options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "INR");
            options.put("amount", "100");

            /*
            JSONObject preFill = new JSONObject();
            preFill.put("email", "harpreet17071202@gmail.com");
            preFill.put("contact", "7973718410");

            options.put("prefill", preFill);

             */

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("PAYMENT", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        try {
            TastyToast.makeText(getApplicationContext(),"Payment Successful!!",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            TastyToast.makeText(getApplicationContext(),"Error in Payment ",TastyToast.LENGTH_LONG,TastyToast.ERROR);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

}

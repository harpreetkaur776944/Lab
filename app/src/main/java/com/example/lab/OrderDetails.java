package com.example.lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderDetails extends AppCompatActivity implements PaymentResultListener {

    EditText name,phone,pincode,houseNo,street,landmark;
    TextView date,time,error;
    Spinner selectcity,selectState;
    Button selectDate,confirm;
    Calendar calendar;
    String[] city = {"Select City","Patiala","Nabha","Rajpura","Sanaur","Nabha"};
    String[] state= {"Select State","Punjab"};
    RadioGroup radioGroup;
    RadioButton r1,r2,r3,r4;
    DatabaseReference databaseReference;
    public static final String TAG="ERRORPAY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Checkout.preload(getApplicationContext());

        name = findViewById(R.id.editTextName);
        phone = findViewById(R.id.editTextPhone);
        pincode = findViewById(R.id.editTextPincode);
        houseNo = findViewById(R.id.editTextHouseNo);
        street = findViewById(R.id.editTextStreet);
        landmark = findViewById(R.id.editTextLandmark);
        date = findViewById(R.id.textViewDate);
        time = findViewById(R.id.textViewTime);
        selectcity = findViewById(R.id.spinnerCity);
        selectState = findViewById(R.id.spinnerState);
        selectDate = findViewById(R.id.buttonDate);
        confirm = findViewById(R.id.buttonConfirm);
        radioGroup = findViewById(R.id.radioGroup);
        r1 = findViewById(R.id.radioButton);
        r2= findViewById(R.id.radioButton2);
        r3= findViewById(R.id.radioButton3);
        r4 = findViewById(R.id.radioButton4);
        error = findViewById(R.id.textViewError);


        radioGroup.clearCheck();
        radioGroup.setVisibility(View.GONE);
        r1.setEnabled(true);
        r2.setEnabled(true);
        r3.setEnabled(true);
        r4.setEnabled(true);
        time.setText("");
        error.setText("");

        ArrayAdapter adp= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,city);
        selectcity.setAdapter(adp);

        ArrayAdapter adp2 = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,state);
        selectState.setAdapter(adp2);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int min = calendar.get(Calendar.MINUTE);
                Log.d("TIME",hour+ " "+min);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String DateOrder = day + "/"+month+"/"+year;
                        checkIfDateAvilable(DateOrder,hour,min);
                        date.setText(DateOrder);
                        radioGroup.setVisibility(View.VISIBLE);
                    }
                },year,month,dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Name = name.getText().toString().trim();
                final String Phone = phone.getText().toString().trim();
                final String Pincode = pincode.getText().toString().trim();
                final String HouseNo = houseNo.getText().toString().trim();
                final String Street = street.getText().toString().trim();
                final String Landmark = landmark.getText().toString().trim();
                final String City = selectcity.getSelectedItem().toString();
                final String State = selectState.getSelectedItem().toString();

                error.setText("");

                if(Name.isEmpty())
                {
                    name.setError("Name is required");
                    name.requestFocus();
                    return;
                }
                if(Phone.isEmpty())
                {
                    phone.setError("PhoneNumber is required");
                    phone.requestFocus();
                    return;
                }
                if(Pincode.isEmpty())
                {
                    pincode.setError("Pincode is required");
                    pincode.requestFocus();
                    return;
                }
                if(Landmark.isEmpty())
                {
                    landmark.setError("Landmark is required");
                    landmark.requestFocus();
                    return;
                }

                if(HouseNo.isEmpty())
                {
                    houseNo.setError("House Number is required");
                    houseNo.requestFocus();
                    return;
                }
                if(Street.isEmpty())
                {
                    street.setError("Street is required");
                    street.requestFocus();
                    return;
                }


                String OrderId =Phone+System.currentTimeMillis();
                String dateOrder = date.getText().toString().trim();

                boolean flag = false,flag2 = false,flag3 = false;
                if(City.equals("Select City"))
                {
                    error.setText("City must be selected ");
                    flag = true;
                }
                if(State.equals("Select State"))
                {
                    error.setText(error.getText().toString().trim()+"State must be selected ");
                    flag2= true;
                }

                if(date.getText().toString().equals("<-- Select the Date"))
                {
                    error.setText(error.getText().toString()+"Please select the date");
                    flag3= true;
                }

                String timeSlot="";
                if(r1.isChecked())
                    timeSlot=Constants.TIME_SLOT_FIRST;
                else if(r2.isChecked())
                    timeSlot=Constants.TIME_SLOT_SECOND;
                else if(r3.isChecked())
                    timeSlot=Constants.TIME_SLOT_THIRD;
                else if(r4.isChecked())
                    timeSlot=Constants.TIME_SLOT_FOURTH;

                Log.d("HERE","HELLO"+timeSlot);
//
                if(!flag && !flag2 && !flag3 && !timeSlot.equals("")) {

                    databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_ORDER);
                    Order order = new Order(OrderId, Name, Phone, Pincode, HouseNo, Street, Landmark, City, State, dateOrder,timeSlot);
                    databaseReference.push().setValue(order);
                    databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_TIMESLOT);
                    TimeSlot timeSlot1 = new TimeSlot(OrderId,dateOrder,timeSlot);
                    databaseReference.push().setValue(timeSlot1);
                    startPayment();
                    Intent in = new Intent(getApplicationContext(),Temp.class);
                    startActivity(in);
                    Toast.makeText(getApplicationContext(),"Order placed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void startPayment() {

        final Checkout checkout = new Checkout();

        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();
            options.put("name", "Laboratory");
            options.put("description", "Test Order");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //   options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "INR");
            options.put("amount", "100");

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("PAYMENT", "Error in starting Razorpay Checkout", e);
        }
    }

    private void checkIfDateAvilable(final String date, final int hour, final int min)
    {
        final List<TimeSlot> checkList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_TIMESLOT);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   TimeSlot timeSlot = ds.getValue(TimeSlot.class);
                   if(timeSlot.date.equals(date))
                   {
                       checkList.add(timeSlot);
                       Log.d("CHECK DATE",timeSlot.getDate());
                   }
                }


                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                String tempDate = dayOfMonth+"/"+month+"/"+year;
                Log.d("Date",tempDate);
                Log.d("Date Here",date+"u");

                if(checkList.size()==4 || (tempDate.equals(date) && hour>=19) )
                {
                    time.setText("No Booking Avaliable for this date select another");
                    radioGroup.setVisibility(View.GONE);
                }
                else {
                    radioGroup.setVisibility(View.VISIBLE);
                    time.setText("Select the Time Slot");
                    for (TimeSlot timeslot : checkList) {

                        if (timeslot.getTime().equals(Constants.TIME_SLOT_FOURTH) || (tempDate.equals(date)&&hour>=19))
                            r4.setEnabled(false);
                        if (timeslot.getTime().equals(Constants.TIME_SLOT_THIRD) || (tempDate.equals(date)&&hour>16))
                            r3.setEnabled(false);
                        if (timeslot.getTime().equals(Constants.TIME_SLOT_SECOND) || (tempDate.equals(date)&&hour>14))
                            r2.setEnabled(false);
                        if (timeslot.getTime().equals(Constants.TIME_SLOT_FIRST) || (tempDate.equals(date)&&hour>12 ))
                            r1.setEnabled(false);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onPaymentSuccess(String s) {
        try {
            Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(this, "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}

package com.example.lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderDetails extends AppCompatActivity implements PaymentResultListener {

    EditText name,phone,pincode,houseNo,street,landmark;
    TextView date,time,error;
    Spinner selectcity,selectState;
    Button confirm;
    ImageView selectDate;
    Calendar calendar;
    String[] city = {"Select City","Patiala","Nabha","Rajpura","Sanaur","Nabha"};
    String[] state= {"Select State","Punjab"};
    RadioGroup radioGroup;
    RadioButton r1,r2,r3,r4;
    DatabaseReference databaseReference;
    public static final String TAG="ERRORPAY";
    String Name,Phone,Pincode,HouseNo,Street,Landmark,City,State,OrderId,dateOrder,timeSlot2;
    String amount;
    List<CartItems> cartList;
    public static String products="" ;
    public static String[] mon ={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
    //private static List<TimeSlot> checkList = new ArrayList<>();


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

        phone.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});
        pincode.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});

        radioGroup.clearCheck();
        radioGroup.setVisibility(View.GONE);
        r1.setEnabled(true);
        r2.setEnabled(true);
        r3.setEnabled(true);
        r4.setEnabled(true);
        time.setText("");
        error.setText("");

        Intent intent = getIntent();
        final  List<String> pro = new ArrayList<>();
        String item = intent.getStringExtra("Items");
        final String[] items = item.split(" ");
        for (String str:items)
        {
            pro.add(str);
        }
        Log.d("ITEMS",item);
        databaseReference =  FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Test test = ds.getValue(Test.class);
                    if(pro.contains(test.getTestCode()))
                    {
                       products += test.getName()+"@";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
                        String DateOrder = day+"/"+mon[month]+"/"+year;
                        checkIfDateAvilable(DateOrder);
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
                check();
            }
        });

    }

    public void check()
    {
        Name = name.getText().toString().trim();
        Phone = phone.getText().toString().trim();
        Pincode = pincode.getText().toString().trim();
        HouseNo = houseNo.getText().toString().trim();
        Street = street.getText().toString().trim();
        Landmark = landmark.getText().toString().trim();
        City = selectcity.getSelectedItem().toString();
        State = selectState.getSelectedItem().toString();

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
        else if(Phone.length()!=10)
        {
            phone.setError("Invalid Phone Number");
            phone.requestFocus();
            return;
        }
        if(Pincode.isEmpty())
        {
            pincode.setError("Pincode is required");
            pincode.requestFocus();
            return;
        }
        else if(Pincode.length()!=6)
        {
            pincode.setError("Invalid pincode");
            pincode.requestFocus();
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


        OrderId =Phone+System.currentTimeMillis();
        dateOrder = date.getText().toString().trim();

        boolean flag = false,flag2 = false,flag3 = false;
        if(City.equals("Select City"))
        {
            Toast.makeText(getApplicationContext(),"City must be selected ",Toast.LENGTH_SHORT).show();
           // error.setText("City must be selected ");
            flag = true;
        }
        if(State.equals("Select State"))
        {
            Toast.makeText(getApplicationContext(),"State must be selected ",Toast.LENGTH_SHORT).show();
           // error.setText(error.getText().toString().trim()+"State must be selected ");
            flag2= true;
        }

        if(date.getText().toString().equals("<-- Select the Date"))
        {
            Toast.makeText(getApplicationContext(),"Please select the date ",Toast.LENGTH_SHORT).show();
            //error.setText(error.getText().toString()+"Please select the date");
            flag3= true;
        }

        timeSlot2="";
        if(r1.isChecked())
            timeSlot2=Constants.TIME_SLOT_FIRST;
        else if(r2.isChecked())
            timeSlot2=Constants.TIME_SLOT_SECOND;
        else if(r3.isChecked())
            timeSlot2=Constants.TIME_SLOT_THIRD;
        else if(r4.isChecked())
            timeSlot2=Constants.TIME_SLOT_FOURTH;

        Log.d("HERE","HELLO"+timeSlot2);
//
        if(!flag && !flag2 && !flag3 && !timeSlot2.equals("")) {
            Intent intent = getIntent();
            amount = intent.getStringExtra("Amount");
           startPayment(amount);
        }

    }

    public void placingOrder()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_ORDER);
        Order order = new Order(OrderId, Name, Phone, Pincode, HouseNo, Street, Landmark, City, State, dateOrder,timeSlot2);
        databaseReference.push().setValue(order);
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_TIMESLOT);
        TimeSlot timeSlot1 = new TimeSlot(OrderId,dateOrder,timeSlot2);
        databaseReference.push().setValue(timeSlot1);

        Intent intent = getIntent();
        String item = intent.getStringExtra("Items");
        String[] items = item.split(" ");
        Log.d("ITEMS",item);
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_ORDER_ITEM_DETAILS);
        for(int i=0;i<items.length;i++)
        {
            OrderItemsDetails orderItemsDetails= new OrderItemsDetails(OrderId,items[i]);
            databaseReference.child(OrderId).push().setValue(orderItemsDetails);
        }



        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_ORDER_HISTORY).child(Constants.getCurrentUrl());
        OrderHistoryDetails orderHistoryDetails = new OrderHistoryDetails(OrderId,dateOrder,timeSlot2,products,amount);
        databaseReference.push().setValue(orderHistoryDetails);


        Intent in = new Intent(getApplicationContext(),mainDrawrer.class);
        startActivity(in);
        Toast.makeText(getApplicationContext(),"Order placed",Toast.LENGTH_LONG).show();
    }

    public void startPayment(String amount) {

        final Checkout checkout = new Checkout();

        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();
            options.put("name", "Laboratory");
            options.put("description", "Test Order");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //Order order = checkout.Orders.create(options);
            //   options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "INR");
            int amt = Integer.parseInt(amount);
            amt = amt*100;
            options.put("amount",amt+"");

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("PAYMENT", "Error in starting Razorpay Checkout", e);
        }
    }

    private void checkIfDateAvilable(final String date)
    {

       DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_TIMESLOT);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TimeSlot> checkList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    TimeSlot timeSlot = ds.getValue(TimeSlot.class);
                    if (timeSlot.getDate().equals(date)) {
                        Log.d("CHECK DATE", timeSlot.getDate() + "Heredate is");
                        checkList.add(timeSlot);
                    }
                }

                    calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);


                    String tempDate = dayOfMonth + "/" + mon[month] + "/" + year;
                    Log.d("Date", tempDate);
                    Log.d("Hour", hour + "");
                    Log.d("Date Here", date);
                    String c;
                    if (tempDate.trim().equals(date.trim())) {
                        c = "true";
                    } else {
                        c = "false";
                    }
                    Log.d("DT", c + "hello");
                    radioGroup.clearCheck();
                    r1.setEnabled(true);
                    r2.setEnabled(true);
                    r3.setEnabled(true);
                    r4.setEnabled(true);

                    if (checkList.size() == 4 || (tempDate.equals(date) && hour >= 16)) {
                        time.setText("No Booking Avaliable for this date select another");
                        radioGroup.setVisibility(View.GONE);
                    } else {
                        radioGroup.setVisibility(View.VISIBLE);
                        time.setText("Select the Time Slot");

                        if (tempDate.equals(date) && hour >= 16)
                            r4.setEnabled(false);
                        if (tempDate.equals(date) && hour >= 14)
                            r3.setEnabled(false);
                        if (tempDate.equals(date) && hour >= 12)
                            r2.setEnabled(false);
                        if (tempDate.equals(date) && hour >= 9)
                            r1.setEnabled(false);


                        for (TimeSlot timeslot : checkList) {

                            if (timeslot.getTime().equals(Constants.TIME_SLOT_FOURTH))
                                r4.setEnabled(false);
                            if (timeslot.getTime().equals(Constants.TIME_SLOT_THIRD))
                                r3.setEnabled(false);
                            if (timeslot.getTime().equals(Constants.TIME_SLOT_SECOND))
                                r2.setEnabled(false);
                            if (timeslot.getTime().equals(Constants.TIME_SLOT_FIRST))
                                r1.setEnabled(false);

                        }
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public  void removeOrderFromCheckList()
    {
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("CartList");
        Query query = dref.orderByChild(Constants.getCurrentUrl());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.getRef().removeValue();
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
            placingOrder();
            removeOrderFromCheckList();

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

package com.example.lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderDetails extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
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



        final String Name = name.getText().toString().trim();
        final String Phone = phone.getText().toString().trim();
        final String Pincode = pincode.getText().toString().trim();
        final String HouseNo = houseNo.getText().toString().trim();
        final String Street = street.getText().toString().trim();
        final String Landmark = landmark.getText().toString().trim();
        final String City = selectcity.getSelectedItem().toString();
        final String State = selectState.getSelectedItem().toString();

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String DateOrder = day + "/"+month+"/"+year;
                        date.setText(DateOrder);
                    }
                },year,month,dayOfMonth);
                datePickerDialog.show();

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                boolean flag = false,flag2 = false;
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

                checkIfDateAvilable(dateOrder);

                String timeSlot="";
                if(r1.isSelected())
                    timeSlot=Constants.TIME_SLOT_FIRST;
                else if(r2.isSelected())
                    timeSlot=Constants.TIME_SLOT_SECOND;
                else if(r3.isSelected())
                    timeSlot=Constants.TIME_SLOT_THIRD;
                else if(r4.isSelected())
                    timeSlot=Constants.TIME_SLOT_FOURTH;

                if(flag && flag2 && !timeSlot.equals("")) {
                    Order order = new Order(OrderId, Name, Phone, Pincode, HouseNo, Street, Landmark, City, State, dateOrder,timeSlot);
                    databaseReference.child(Constants.DATABASE_ORDER).push().setValue(order);
                    TimeSlot timeSlot1 = new TimeSlot(OrderId,dateOrder,timeSlot);
                    databaseReference.child(Constants.DATABASE_TIMESLOT).push().setValue(timeSlot1);
                }
            }
        });

    }
    private void checkIfDateAvilable(final String date)
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
                   }
                }
                if(checkList.size()==4)
                {
                    time.setText("No Booking Avaliable for this date select another");
                }
                else {
                    time.setText("Select the Time Slot");
                    for (TimeSlot timeslot : checkList) {
                        if (timeslot.getDate().equals(Constants.TIME_SLOT_FIRST))
                            r1.setEnabled(false);
                        else if (timeslot.getDate().equals(Constants.TIME_SLOT_SECOND))
                            r2.setEnabled(false);
                        else if (timeslot.getDate().equals(Constants.TIME_SLOT_THIRD))
                            r3.setEnabled(false);
                        else if (timeslot.getDate().equals(Constants.TIME_SLOT_FOURTH))
                            r4.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}

package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class OrderDetails extends AppCompatActivity {

    EditText name,phone,pincode,houseNo,street,landmark;
    TextView date,time;
    Spinner selectcity,selectState;
    Button selectDate,selectTime,confirm;
    Calendar calendar;
    String[] city = {"Select City","Patiala","Nabha","Rajpura","Sanaur","Nabha"};
    String[] state= {"Select State","Punjab"};

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
        selectTime = findViewById(R.id.buttonTime);
        confirm = findViewById(R.id.buttonConfirm);

        ArrayAdapter adp= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,city);
        selectcity.setAdapter(adp);

        ArrayAdapter adp2 = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,state);
        selectState.setAdapter(adp2);


        String Name = name.getText().toString().trim();
        if(Name.isEmpty())
        {
            name.setError("Name is required");
            name.requestFocus();
            return;
        }

        String Phone = phone.getText().toString().trim();
        if(Phone.isEmpty())
        {
            phone.setError("PhoneNumber is required");
            phone.requestFocus();
            return;
        }

        String Pincode = pincode.getText().toString().trim();
        if(Pincode.isEmpty())
        {
            pincode.setError("Pincode is required");
            pincode.requestFocus();
            return;
        }

        String HouseNo = houseNo.getText().toString().trim();
        if(HouseNo.isEmpty())
        {
            houseNo.setError("House Number is required");
            houseNo.requestFocus();
            return;
        }

        String Street = street.getText().toString().trim();
        if(Street.isEmpty())
        {
            street.setError("Street is required");
            street.requestFocus();
            return;
        }

        String Landmark = landmark.getText().toString().trim();
        if(Landmark.isEmpty())
        {
            landmark.setError("Landmark is required");
            landmark.requestFocus();
            return;
        }

        String City = selectcity.getSelectedItem().toString();
        if(City.equals("Select City"))
        {

        }

        String State = selectState.getSelectedItem().toString();
        if(State.equals("Select State"))
        {

        }

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

                        String Date = day + "/"+month+"/"+year;
                        date.setText(Date);
                    }
                },year,month,dayOfMonth);
                datePickerDialog.show();
            }
        });

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}

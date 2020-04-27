package com.example.lab;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
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
        final View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        final Spinner spinner = view.findViewById(R.id.spinner);
        ImageView imageView = view.findViewById(R.id.imageViewCalendar);
        final EditText name = view.findViewById(R.id.editTextFeedName);
        final EditText contact = view.findViewById(R.id.editTextContact);
        final RadioButton ques1Yes = view.findViewById(R.id.radioButton7);
        final RadioButton ques1No = view.findViewById(R.id.radioButton8);
        final RadioButton ques2Yes = view.findViewById(R.id.radioButton9);
        final RadioButton ques2No = view.findViewById(R.id.radioButton10);
        final TextView textView = view.findViewById(R.id.textView28);
        Button submit = view.findViewById(R.id.buttonSubmit2);
        textView.setText("Select Home Collection Date");

        String[] cities = {"Select city", "Patiala", "Nabha", "Rajpura", "Sanaur", "Samana", "Nabha"};
        ArrayAdapter adp = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item, cities);
        spinner.setAdapter(adp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textView.setText(day + "/" + month + "/" + year);

                    }
                }, year, month, day);
                datePicker.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sname = name.getText().toString().trim();
                String scontact = contact.getText().toString().trim();
                if (sname.isEmpty()) {
                    name.setError("Name Required");
                    name.requestFocus();
                    return ;
                }
                if (scontact.isEmpty()) {
                    contact.setError("Contact Number required");
                    contact.requestFocus();
                    return ;
                }
                if (scontact.length() != 10) {
                    contact.setError("Invalid Number");
                    contact.requestFocus();
                    return ;
                }
                boolean flagQues1 = false, flagQues2 = false, flagDate = false, flagCity = false;
                if (!ques1Yes.isChecked() && !ques1No.isChecked()) {
                    Toast.makeText(view.getContext(), "Please select one option for question 1", Toast.LENGTH_LONG).show();
                    flagQues1 = true;
                }
                if (!ques2Yes.isChecked() && !ques2No.isChecked()) {
                    Toast.makeText(view.getContext(), "Please select one option for question 2", Toast.LENGTH_LONG).show();
                    flagQues2 = true;
                }
                if (textView.getText().toString().trim().equals("Select Home Collection Date"))
                {
                    Toast.makeText(view.getContext(),"Please select the date",Toast.LENGTH_LONG).show();
                    flagDate = true;
                }
                if(spinner.getSelectedItem().equals("Select city")) {
                    Toast.makeText(view.getContext(),"Please select the city",Toast.LENGTH_LONG).show();
                    flagCity = true;
                }
                if(!flagQues1 && !flagQues2 && !flagDate && !flagCity)
                {
                    Toast.makeText(view.getContext(),"Thanks for the feedback",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

}

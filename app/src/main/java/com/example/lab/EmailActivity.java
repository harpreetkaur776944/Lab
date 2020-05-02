package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmailActivity extends AppCompatActivity {

    EditText to,subject,message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        to = findViewById(R.id.editTextTo);
        subject= findViewById(R.id.editTextSubject);
        message = findViewById(R.id.editTextMessage);
        send = findViewById(R.id.buttonSendEmail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();

            }
        });

    }
    private void sendMail()
    {
        String[] recipient = {to.getText().toString().trim()};
        Log.d("Email",recipient[0]);
        String sub = subject.getText().toString().trim();
        String msg = message.getText().toString().trim();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT,sub);
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email client"));

    }
}

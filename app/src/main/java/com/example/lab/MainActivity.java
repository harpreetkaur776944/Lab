package com.example.lab;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.sdsmdg.tastytoast.TastyToast;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button signUp;
    CheckBox showPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.signupEmail);
        password = findViewById(R.id.signupPassword);
        signUp = findViewById(R.id.signUp);
        showPassword = findViewById(R.id.checkBox);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView2);

        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),Login.class);
                startActivity(in);
            }
        });

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    password.setTransformationMethod(null);
                }
                else
                {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

    }
    private void registerUser()
    {

        String username = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(username.isEmpty())
        {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(pass.isEmpty())
        {

            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches())
        {
            email.setError("Please enter a valid email address");
            email.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            password.setError("Minimum length must be 6");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(username,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                TastyToast.makeText(getApplicationContext(),"Registered !! Please check your email for your verification.",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                                email.setText("");
                                password.setText("");
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        TastyToast.makeText(getApplicationContext(),"User Already Existed !!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}

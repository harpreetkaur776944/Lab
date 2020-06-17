package com.example.lab;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

public class Login extends AppCompatActivity {

    EditText email,password;
    Button login,newUser,forgotPassword;
    CheckBox showPassword;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    public static String LoginName="";
    public static String LoginId ="";
    public static boolean FLAG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginButton);
        newUser = findViewById(R.id.newUser);
        forgotPassword = findViewById(R.id.forgotPassward);
        showPassword = findViewById(R.id.checkBox2);
        progressBar = findViewById(R.id.progressBar2);

        firebaseAuth = FirebaseAuth.getInstance();



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


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = email.getText().toString().trim();
                if(username.isEmpty())
                {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.sendPasswordResetEmail(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);
                                TastyToast.makeText(getApplicationContext(),"Reset your password send your mail ",TastyToast.LENGTH_LONG,TastyToast.INFO);
                            }
                            else
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }
    private void check()
    {

        final String username = email.getText().toString().trim();
        final String pass = password.getText().toString().trim();

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



        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(username,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {

                    if(firebaseAuth.getCurrentUser().isEmailVerified())
                    {

                        databaseReference =  FirebaseDatabase.getInstance().getReference(Constants.DATABASE_LOGIN);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name ="";
                                String id="";
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    LoginDetails loginDetails = ds.getValue(LoginDetails.class);
                                    if(loginDetails.Username.equals(username) && loginDetails.Password.equals(pass)) {
                                        name = loginDetails.Name;
                                        id=loginDetails.Username;
                                    }
                                }
                                progressBar.setVisibility(View.GONE);
                                Intent in = new Intent(getApplicationContext(),mainDrawrer.class);
                                LoginName = name;
                                LoginId = id;
                                FLAG = true;
                                startActivity(in);

                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });



                    }
                    else
                    {
                        TastyToast.makeText(getApplicationContext(),"Please verify your email address",TastyToast.LENGTH_LONG,TastyToast.DEFAULT);
                    }
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(LogoutFragment.FLAG2==true) {
            LogoutFragment.FLAG2=false;
            Login.FLAG=false;
            finish();
        }
        finish();
    }
}

package com.example.universaldonor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, UserSignUp.OnFragmentInteractionListener{

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private Button signUpAsUser;
    private Button signUpAsBloodBank;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        signup = (Button) findViewById(R.id.signup);
        signUpAsUser = findViewById(R.id.signupAsUserButton);
        signUpAsBloodBank = findViewById(R.id.signupAsBloodBankButton);
//        email = (EditText) findViewById(R.id.email);
//        password = (EditText) findViewById(R.id.password);
//        login = (TextView) findViewById(R.id.login);
        database = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
//        signup.setOnClickListener(this);
//        login.setOnClickListener(this);
        signUpAsBloodBank.setOnClickListener(this);
        signUpAsUser.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signupAsUserButton:
                UserSignUp userSignUp = UserSignUp.newInstance();
                fragmentManager.beginTransaction().replace(R.id.signUpArea, userSignUp).commit();
                break;
            case R.id.signupAsBloodBankButton:
                break;

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
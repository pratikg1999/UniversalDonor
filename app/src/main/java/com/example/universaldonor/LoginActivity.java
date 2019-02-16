package com.example.universaldonor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    static EditText email;
    private EditText password;
    private TextView signup;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signup = (TextView) findViewById(R.id.signUpArea);
        progressDialog = new ProgressDialog(this);


        if(mAuth.getCurrentUser() != null){
            // start profile acivity
            finish();
            startActivity(new Intent(this,UserActivity.class));
        }
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    public void userlogin() {

        final String mailId = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(TextUtils.isEmpty(mailId)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG);
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please enter a password",Toast.LENGTH_SHORT);
        }

        progressDialog.setMessage("Logging in please wait ..........");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(mailId,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Login sucessfull",Toast.LENGTH_SHORT).show();
                            // start profile activity
                            finish();
                            startActivity(new Intent(LoginActivity.this,UserActivity.class));
                        }
                        else{
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

        if(v == signup){
            finish();
            startActivity(new Intent(this,SignupActivity.class));
        }
        if(v == login){
            userlogin();
        }
    }

    }


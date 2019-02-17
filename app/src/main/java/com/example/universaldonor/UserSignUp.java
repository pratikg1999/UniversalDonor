package com.example.universaldonor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserSignUp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserSignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSignUp extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private Button signup;
    private EditText email;
    private EditText password;
    private TextView login;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private OnFragmentInteractionListener mListener;
    private String userId;
    private DatabaseReference usersDatabase;
    DatabaseReference bloodBanksDatabase;
    DatabaseReference donationsDatabase;
    DatabaseReference aquiresDatabase;

    public UserSignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment UserSignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static UserSignUp newInstance() {
        UserSignUp fragment = new UserSignUp();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(getContext());
        usersDatabase = database.getReference("users");
        bloodBanksDatabase = database.getReference("bloodBanks");
        donationsDatabase = database.getReference("donations");
        aquiresDatabase = database.getReference("aquires");

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_sign_up, container, false);
        email = (EditText) v.findViewById(R.id.email);
        password = (EditText) v.findViewById(R.id.password);
        login = (TextView) v.findViewById(R.id.loginAsUser);
        signup = (Button) v.findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        return v;
    }

    private void usersignup(){
        final String tempEmail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(TextUtils.isEmpty(tempEmail)){
            Toast.makeText(getContext(),"Please enter email",Toast.LENGTH_LONG);
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(getContext(),"Please enter a password",Toast.LENGTH_SHORT);
            return;
        }
        final String mailId = 1+tempEmail;

        progressDialog.setMessage("Registering please wait ..........");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(mailId,pass)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Registered successfully",Toast.LENGTH_SHORT).show();
                            userId = mAuth.getCurrentUser().getUid();
                            User newUser = new User("", tempEmail, userId, "",0,0,0,"",SignupActivity.latitude, SignupActivity.longitude,0, new ArrayList<String>(Arrays.asList(new String[]{"asdf", "sdf"})), new ArrayList<String>());
                            usersDatabase.child(userId).setValue(newUser);
                            startActivity(new Intent(getContext(),UserActivity.class));
                        }
                        else{
                            Toast.makeText(getContext(),"Something happenned",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup:
                usersignup();
                break;
            case R.id.loginAsUser:
                startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

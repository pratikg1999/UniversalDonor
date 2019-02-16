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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.xml.sax.helpers.LocatorImpl;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BloodBankSignUp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BloodBankSignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BloodBankSignUp extends Fragment implements View.OnClickListener{
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
    private EditText addressET;
    private ImageButton addressButton;
    private EditText cityET;
    private EditText stateET;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private OnFragmentInteractionListener mListener;
    private Double latitude;
    private Double longitude;

    public BloodBankSignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BloodBankSignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static BloodBankSignUp newInstance() {
        BloodBankSignUp fragment = new BloodBankSignUp();
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
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blood_bank_sign_up, container, false);
        email = (EditText) v.findViewById(R.id.email);
        password = (EditText) v.findViewById(R.id.password);
        login = (TextView) v.findViewById(R.id.login);
        signup = (Button) v.findViewById(R.id.signup);
        addressET = v.findViewById(R.id.address);
        cityET = v.findViewById(R.id.cityET);
        stateET = v.findViewById(R.id.stateET);
        addressButton = v.findViewById(R.id.addressButton);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        addressButton.setOnClickListener(this);
        return v;
    }
    private void bloodBankSignup(){
        final String mailId = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String address = addressET.getText().toString().trim();
        String city = cityET.getText().toString().trim();
        String state = stateET.getText().toString().trim();

        if(TextUtils.isEmpty(mailId)){
            email.setError("Enter a email");
            Toast.makeText(getContext(),"Please enter email",Toast.LENGTH_LONG);
            return;
        }
        if(TextUtils.isEmpty(pass)){
            password.setError("Please enter password");
            Toast.makeText(getContext(),"Please enter a password",Toast.LENGTH_SHORT);
            return;
        }
        if(TextUtils.isEmpty(address)){
            addressET.setError("Enter Address");
            return;
        }
        if(latitude==null || longitude==null){
            Toast.makeText(getContext(), "Please click on address button to get location", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering please wait ..........");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(mailId,pass)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            /*DatabaseReference users = database.getReference("users").child(task.getResult().getUser().getUid().toString());
                            User newuser = new User(users.getKey(), mailId, new ArrayList<String>(Arrays.asList("dummy"))
                                    ,new ArrayList<String>(Arrays.asList("dummy")),
                                    new ArrayList<String>(Arrays.asList("dummy"))
                                    , 0);
                            users.setValue(newuser);*/
                            //profile activity
                            Toast.makeText(getContext(),"Registered successfully",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getContext(),MainActivity.class));
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
                bloodBankSignup();
                break;
            case R.id.login:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.addressButton:
                getLatLng();
                break;

        }
    }

    private void getLatLng() {
        if (mListener != null) {
            mListener.getLatLng();
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
        void getLatLng();
    }
}

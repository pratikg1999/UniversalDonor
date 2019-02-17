package com.example.universaldonor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;


public class UserProfile extends Fragment {


    private User curUserProfile;
    private DatabaseReference curUserDatabase;
    private EditText usernameET;
    private EditText addressET;
    private EditText telNoET;
    private Spinner bloodTypeSpinner;
    private Button updateIdButton;
    ArrayList<String> bloodTypes;

    private OnFragmentInteractionListener mListener;

    public UserProfile() {
        // Required empty public constructor
    }

    public static UserProfile newInstance(DatabaseReference curUserDatabase, User curUserProfile) {
        UserProfile fragment = new UserProfile();
        fragment.curUserDatabase = curUserDatabase;
        fragment.curUserProfile = curUserProfile;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_user_profile, container, false);
        usernameET = v.findViewById(R.id.usernameET);
        addressET = v.findViewById(R.id.addressET);
        telNoET = v.findViewById(R.id.telNoET);
        bloodTypeSpinner = v.findViewById(R.id.bloodTypeSpinner);
        updateIdButton = v.findViewById(R.id.updateIdButton);
        bloodTypes = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.blood_types)));
        updateValues();


        curUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                curUserProfile = dataSnapshot.getValue(User.class);
                updateValues();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        updateIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curUserProfile.setAddress(addressET.getText().toString());
                curUserProfile.setUserName(usernameET.getText().toString());
                curUserProfile.setMobileNumber(Long.parseLong(telNoET.getText().toString()));
                curUserProfile.setBloodGroup(bloodTypeSpinner.getSelectedItem().toString());

                curUserDatabase.setValue(curUserProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return v;

    }

    private void updateValues() {
        usernameET.setText(curUserProfile.getUserName());
        addressET.setText(curUserProfile.getAddress());
        telNoET.setText(curUserProfile.getMobileNumber()+"");
        if(curUserProfile.getBloodGroup()==null || curUserProfile.getBloodGroup().equals("")){
            bloodTypeSpinner.setEnabled(true);
        }
        else{
            bloodTypeSpinner.setEnabled(false);
            bloodTypeSpinner.setSelection(bloodTypes.indexOf(curUserProfile.getBloodGroup()));
        }
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

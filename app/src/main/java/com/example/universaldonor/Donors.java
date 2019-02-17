package com.example.universaldonor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Queue;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Donors.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Donors#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Donors extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    DatabaseReference requestsDatabase;
    FirebaseUser curUser;
    ArrayList<User> donors;
    public Donors() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Donors.
     */
    // TODO: Rename and change types and number of parameters
    public static Donors newInstance(ArrayList<User> donors) {
        Donors fragment = new Donors();
        fragment.donors = donors;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestsDatabase = FirebaseDatabase.getInstance().getReference("requests");
        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_donors, container, false);
        recyclerView = v.findViewById(R.id.rViewDonors);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        RViewDonorAdapter adapter = new RViewDonorAdapter(getActivity(), donors);
        recyclerView.setAdapter(adapter);
        return v;
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

    class RViewDonorAdapter extends RecyclerView.Adapter<RViewDonorAdapter.RViewDonorVHolder>{
        Context ctx;
        ArrayList<User> donors;

        RViewDonorAdapter(Context ctx, ArrayList<User> donors){
            this.ctx = ctx;
            this.donors = donors;
        }

        @NonNull
        @Override
        public RViewDonorVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_donor, viewGroup, false);
            RViewDonorVHolder  holder= new RViewDonorVHolder(v);
            return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull RViewDonorVHolder v, int i) {
           final User donor = donors.get(i);
            v.nameTV.setText(donor.getUserName());
            v.bloodTypeTV.setText(donor.getBloodGroup());
            v.requestBloodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Request request = new Request(curUser.getUid(), donor.getUserId());
                    requestsDatabase.child(request.getRequestId()+"").setValue(request);
                }
            });
        }

        @Override
        public int getItemCount() {
            return donors.size();
        }

        class RViewDonorVHolder extends RecyclerView.ViewHolder{
            TextView nameTV;
            TextView bloodTypeTV;
            RatingBar ratingBar;
            Button requestBloodButton;
            public RViewDonorVHolder(@NonNull View v) {
                super(v);
                nameTV = v.findViewById(R.id.nameTV);
                bloodTypeTV = v.findViewById(R.id.bloodTypeTV);
                ratingBar = v.findViewById(R.id.ratingBar);
                requestBloodButton = v.findViewById(R.id.requestBlood);
            }
        }
    }
}

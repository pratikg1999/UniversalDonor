package com.example.universaldonor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeUser.OnFragmentInteractionListener,
        Donors.OnFragmentInteractionListener,
        UserProfile.OnFragmentInteractionListener{

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    FirebaseUser curUser;
    FragmentManager fragmentManager;
    DatabaseReference usersDatabase;
    DatabaseReference userDatabase;
    User curUserProfile;
    ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        curUser  = mAuth.getCurrentUser();
        usersDatabase = database.getReference("users");
        fragmentManager = getSupportFragmentManager();
        userDatabase = usersDatabase.child(curUser.getUid());
        curUserProfile = new User();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Query usersQuery = database.getReference("users").orderByChild("numDonations");
        usersQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    Log.d("curuser", userSnapshot.getValue(User.class).getUserId());
                    users.add(userSnapshot.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                curUserProfile = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(curUserProfile.getUserName()==null || curUserProfile.getUserName().equals("")){
            UserProfile userProfile = UserProfile.newInstance(userDatabase, curUserProfile);
            fragmentManager.beginTransaction().replace(R.id.content_main_relative, userProfile).commit();
            Toast.makeText(this, "Please update your profile", Toast.LENGTH_SHORT).show();
        }
        else {
            HomeUser homeUser = HomeUser.newInstance();
            fragmentManager.beginTransaction().replace(R.id.content_main_relative, homeUser).commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                return  true;
            case R.id.action_logout:
                mAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_donors:
                Donors donorsFragement = Donors.newInstance(users);
                fragmentManager.beginTransaction().replace(R.id.content_main_relative, donorsFragement).commit();
                break;
            case R.id.nav_home:
                HomeUser homeFragement = HomeUser.newInstance();
                fragmentManager.beginTransaction().replace(R.id.content_main_relative, homeFragement).commit();
                break;
            case R.id.nav_profile:
                UserProfile userProfile = UserProfile.newInstance(userDatabase, curUserProfile);
                fragmentManager.beginTransaction().replace(R.id.content_main_relative, userProfile).commit();
                break;
            case R.id.nav_blood_banks:
                startActivity(new Intent(this, MapsStart.class));
                break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

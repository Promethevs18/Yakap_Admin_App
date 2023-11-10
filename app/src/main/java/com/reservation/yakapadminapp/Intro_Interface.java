package com.reservation.yakapadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Intro_Interface extends AppCompatActivity {

    //UI ELEMENTS
    Button scan;

    //Firebase related elements
    FirebaseUser user;

    //Google Sign-in elements
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI elements
        scan = findViewById(R.id.scan);


        //Firebase related calls
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Google sign in related calls
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(getString(R.string.default_web_client_id)).build();
        gsc = GoogleSignIn.getClient(this, gso);


        //BELOW IS WHERE THE MAGIC HAPPENS



    }
}
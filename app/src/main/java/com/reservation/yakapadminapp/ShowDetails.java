package com.reservation.yakapadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ShowDetails extends AppCompatActivity {


    //UI
    Intent getResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        //UI callback
        getResult = getIntent();

        Toast.makeText(this, getResult.getStringExtra("user path"), Toast.LENGTH_SHORT).show();
    }
}
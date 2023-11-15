package com.reservation.yakapadminapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ShowDetails extends AppCompatActivity {
    //UI
    Intent getResult;

    //Textviews
    TextView userName, address, email, payMode, phone, pwd, section,
             ticketNum, transactNo, ticketPrice;

    ImageView pwdId, receipt;
    Button confirm;

    //Firestore query
    DocumentReference getUser;

    //Firebase User query
    FirebaseUser currentUser;

    //Local data
    String[] fullPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        //UI callback
        getResult = getIntent();

        userName = findViewById(R.id.full_name);
        address = findViewById(R.id.address);
        payMode = findViewById(R.id.payMode);
        phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.pwdIndicate);
        section = findViewById(R.id.section);
        ticketNum = findViewById(R.id.ticketsBought);
        transactNo = findViewById(R.id.transactNo);
        ticketPrice = findViewById(R.id.ticketPrice);
        email = findViewById(R.id.email);

        pwdId = findViewById(R.id.idImage);
        receipt = findViewById(R.id.receiptImage);

        confirm = findViewById(R.id.confirm);

        //BELOW IS WHERE THE MAGIC HAPPENS

        //get the path from the scanned qr
        fullPath = Objects.requireNonNull(getResult.getStringExtra("user path")).split("/");

        //use the data taken to provide the path for our DocumentReference
        getUser = FirebaseFirestore.getInstance().collection(fullPath[0]).document(fullPath[1]);

        //get the current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //Start a progress dialog
        ProgressDialog pd = new ProgressDialog(ShowDetails.this);
        pd.setMessage("Processing");
        pd.show();
        //get the data now
        getUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        pd.dismiss();
                        userModel userDeets = documentSnapshot.toObject(userModel.class);
                        //assign the names to the textviews
                        userName.setText(Objects.requireNonNull(userDeets).getFullName());
                        address.setText(Objects.requireNonNull(userDeets).getAddress());
                        email.setText(Objects.requireNonNull(userDeets).getEmail());
                        payMode.setText(Objects.requireNonNull(userDeets).getPaymentMode());
                        phone.setText(Objects.requireNonNull(userDeets).getPhoneNum());
                        pwd.setText(Objects.requireNonNull(userDeets).getPwd());
                        section.setText(Objects.requireNonNull(userDeets).getSection());
                        ticketNum.setText(Objects.requireNonNull(userDeets).getTicketsBought());
                        transactNo.setText(Objects.requireNonNull(userDeets).getTransactNo());
                        ticketPrice.setText(String.valueOf(Objects.requireNonNull(userDeets).getTotalPrice()));

                        //assign the links to the images using Picasso
                        //check if the there is a pwd, else use a default
                        if(userDeets.getPwdIdLink().contentEquals("")){
                            pwdId.setImageResource(R.drawable.noid);
                        }
                        else{
                            Picasso.get().load(userDeets.getPwdIdLink()).into(pwdId);
                        }
                        //to show the receipt to the imageview
                        Picasso.get().load(userDeets.getReceiptLink()).into(receipt);

                        if(documentSnapshot.contains("confirmedTime")){
                            AlertDialog.Builder alertWarn = new AlertDialog.Builder(ShowDetails.this);
                            alertWarn.setTitle("User already checked in!");
                            alertWarn.setIcon(R.drawable.check);
                            alertWarn.setMessage("This user has already confirmed their attendance at: " + userDeets.getConfirmedTime() + "\n\nBy: " + userDeets.getConfirmedBy());
                            alertWarn.setCancelable(true);
                            alertWarn.show();

                            confirm.setVisibility(View.GONE);
                        }
                    }
                });
        //now, when the user presses on the confirm attendance button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("HH:MM - yyyy:MM:dd", Locale.getDefault());
                String chrono = format.format(new Date(System.currentTimeMillis()));
                getUser.update("confirmedTime", chrono, "confirmedBy", currentUser.getDisplayName()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        AlertDialog.Builder notify = new AlertDialog.Builder(ShowDetails.this);
                        notify.setIcon(R.drawable.check);
                        notify.setTitle("Attendance Confirmed!");
                        notify.setMessage("This customer's ticket has been confirmed and locked as present in our database. \nThis change is irreversible, unless modified through our main dev");
                        notify.setCancelable(true);
                        notify.show();
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check for any changes within the document
        getUser.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                AlertDialog.Builder gawa = new AlertDialog.Builder(ShowDetails.this);
                if(value.contains("confirmedTime") &&  value.contains("confirmedBy")){
                    gawa.setTitle("This user has been checked in now");
                    gawa.setMessage(value.get("fullName").toString());
                    gawa.setMessage("Checked in at: " + value.get("confirmedTime").toString());
                    gawa.setMessage("Verified by: " + value.get("confirmedBy").toString());
                    gawa.setCancelable(true);
                    gawa.setIcon(R.drawable.check);
                    gawa.show();
                }
            }
        });
    }
}
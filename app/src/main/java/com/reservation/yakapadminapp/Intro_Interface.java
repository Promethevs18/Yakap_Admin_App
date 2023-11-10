package com.reservation.yakapadminapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Intro_Interface extends AppCompatActivity {

    //UI ELEMENTS
    Button scan;
    ProgressDialog pd;

    //Firebase related elements
    FirebaseUser user;

    //Google Sign-in elements
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    //ActivityResult call
    ActivityResultLauncher<ScanOptions> goScan;

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


        //This is for the scanner code instantiation
        goScan = registerForActivityResult(new ScanContract(), result -> {
            if(result.getContents() == null){
                Toast.makeText(Intro_Interface.this, "Scanning cancelled", Toast.LENGTH_SHORT).show();
            }
            else {
                String buongData = result.getContents();
                int start = buongData.indexOf("- ") + 2;
                if(start > 1 && start < buongData.length()){
                    String extracted = buongData.substring(start);
                    Intent passPath = new Intent(Intro_Interface.this, ShowDetails.class);
                    passPath.putExtra("user path", extracted);
                    startActivity(passPath);
                }
            }
        });


        //BELOW IS WHERE THE MAGIC HAPPENS

        //This is for signing the user to Google
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign_In();
            }
        });


    }

    private void Sign_In() {
        Intent getGoogle = gsc.getSignInIntent();
        startActivityForResult(getGoogle, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            Task<GoogleSignInAccount> signIn = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount googleAccount = signIn.getResult(ApiException.class);
                if(googleAccount != null){
                    AuthCredential authCreds = GoogleAuthProvider.getCredential(googleAccount.getIdToken(),null);
                    firebaseSignin(authCreds);
                }
            }
           catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void firebaseSignin(AuthCredential authCreds) {
        pd = new ProgressDialog(Intro_Interface.this);
        pd.setTitle("Authenticating...");
        pd.setMessage("Credentials Received. Sending result to Firebase for account setup");
        pd.show();

        FirebaseAuth.getInstance().signInWithCredential(authCreds).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                pd.dismiss();
                ScanOptions scanOptions = new ScanOptions();
                scanOptions.setPrompt("Press volume key up to turn on light\nPress volume key down to turn it off");
                scanOptions.setOrientationLocked(true);
                scanOptions.setBeepEnabled(true);
                scanOptions.setCaptureActivity(Scanner.class);
                goScan.launch(scanOptions);
            }
        });
    }
}
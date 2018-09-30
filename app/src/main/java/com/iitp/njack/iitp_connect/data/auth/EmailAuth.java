package com.iitp.njack.iitp_connect.data.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iitp.njack.iitp_connect.R;

public class EmailAuth extends AppCompatActivity {

    private EditText email,password;
    private TextView textThereAtCardView;
    private Button switchBwSign,forgotPassword;
    private CardView signupButton;
    private ProgressDialog progressDialog;
    private int statusSwitch = 0;

    private NetworkInfo info;
    private ConnectivityManager connectivityManager;
    boolean isNetworkactive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_auth);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle("Verify your identity");
        actionBar.setDisplayHomeAsUpEnabled(true);

        connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        isNetworkactive = info!=null && info.isConnectedOrConnecting();
        if (!isNetworkactive){
            Toast.makeText(getApplicationContext(),"Check your internet", Toast.LENGTH_SHORT).show();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        switchBwSign =findViewById(R.id.switchBwSignup);
        signupButton = findViewById(R.id.emailSignUpButton);
        textThereAtCardView = findViewById(R.id.textToDisplay);
        forgotPassword = findViewById(R.id.forgotPassword);

        switchBwSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (textThereAtCardView.getText().toString().equals("Login")){
                    //Changing to signup mode
                    statusSwitch = 0;
                    textThereAtCardView.setText("Signup");
                    switchBwSign.setText("Already have an account, Login Here");
                }
                else if (textThereAtCardView.getText().toString().equals("Signup")){
                    //Changing to login mode
                    statusSwitch = 1;
                    textThereAtCardView.setText("Login");
                    switchBwSign.setText("Don't have an account, Signup Here");
                }
                progressDialog.dismiss();
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (statusSwitch == 0){
                    signup();
                }if (statusSwitch == 1){
                    login();
                }
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = email.getText().toString();
                if (TextUtils.isEmpty(emailAddress)){
                    email.setError("enter email");
                    return;
                }
                progressDialog.show();
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(EmailAuth.this, "Check your inbox to reset password", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(EmailAuth.this, "Contact admin", Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        });

            }
        });
    }

    private void login(){
        String emailAddress = email.getText().toString();
        String passwd = password.getText().toString();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailAddress,passwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            loginSuccess();
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(EmailAuth.this, error, Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    private void signup(){
        String emailAddress = email.getText().toString();
        String passwd = password.getText().toString();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailAddress,passwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            loginSuccess();
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(EmailAuth.this, error, Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }

    private void loginSuccess(){
        //Check if user is existing and go to homepage if profile data is found
        startActivity(new Intent(EmailAuth.this,CompleteProfile.class));
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EmailAuth.this,FirebaseMainAuthActiviy.class));
        finish();
    }
}

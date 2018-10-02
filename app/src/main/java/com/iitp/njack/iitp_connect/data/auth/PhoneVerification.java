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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.iitp.njack.iitp_connect.R;

import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {

    private static final String TAG = "PhoneLogin";

    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private TextView mStatusText;

    private EditText mPhoneNumberField;
    private EditText mVerificationField;

    private ProgressDialog progressDialog;

    private NetworkInfo info;
    private ConnectivityManager connectivityManager;
    boolean isNetworkactive;

    private CardView phoneNumber,otp,startVerification,resendCode,verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
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
        progressDialog.setMessage("Please wait...");
        mAuth = FirebaseAuth.getInstance();

        phoneNumber = findViewById(R.id.mobileNumberCardView);
        phoneNumber.setVisibility(View.VISIBLE);

        otp = findViewById(R.id.otpCardView);
        otp.setVisibility(View.GONE);

        startVerification = findViewById(R.id.startCardView);
        startVerification.setVisibility(View.VISIBLE);

        verify = findViewById(R.id.verifyCardView);
        verify.setVisibility(View.GONE);

        resendCode = findViewById(R.id.resendCardView);
        resendCode.setVisibility(View.GONE);

        mStatusText = (TextView)findViewById(R.id.statusText);
        mStatusText.setText("Enter your mobile number");

        mPhoneNumberField = (EditText) findViewById(R.id.field_phone_number);
        mVerificationField = (EditText) findViewById(R.id.field_verification_code);


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(final PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);
                mStatusText.setText("Verification Completed");
                Toast.makeText(getApplicationContext(),"Auto verification completed", Toast.LENGTH_LONG).show();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                try{
                    mStatusText.setText("Welcome user, Firebase ID: " + user.getUid()+" ");
                }catch (NullPointerException e){
                    e.printStackTrace();
                }finally {
                    loginSuccess();
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                String mess="";
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mess = "Invalid Phone Number";
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    mess = "Too many requests, please try again";
                }else if (e instanceof FirebaseAuthUserCollisionException){
                    mess = "Oops, looks someone is already signed in";
                }else if (e instanceof FirebaseNetworkException){
                    mess = "Check your internet";
                }else if (e instanceof FirebaseAuthException){
                    mess = ((FirebaseAuthException) e).getErrorCode();
                }
                progressDialog.dismiss();
                mStatusText.setText("Verification failed, "+ mess);
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mStatusText.setText("Enter the code sent you mobile or wait for auto-verification");
                progressDialog.dismiss();
                mVerificationId = verificationId;
                mResendToken = token;
                //mVerifyButton.setEnabled(true);
                phoneNumber.setVisibility(View.GONE);
                startVerification.setVisibility(View.GONE);
                verify.setVisibility(View.VISIBLE);
                resendCode.setVisibility(View.VISIBLE);
                otp.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                progressDialog.dismiss();
                mStatusText.setText("Auto-verification timed out, please enter the code");
            }
        };

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkactive){
                    Toast.makeText(getApplicationContext(),"Check your internet", Toast.LENGTH_SHORT).show();
                }
                progressDialog.show();
                resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkactive){
                    Toast.makeText(getApplicationContext(),"Check your internet", Toast.LENGTH_SHORT).show();
                }
                progressDialog.show();
                if (!validateCode()){
                    mVerificationField.setError("Invalid code");
                    return;
                }
                String code = mVerificationField.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);
            }
        });

        startVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVerifictaionProcess();
            }
        });
    }

    private void startVerifictaionProcess(){
        if (!isNetworkactive){
            Toast.makeText(getApplicationContext(),"Check your internet", Toast.LENGTH_SHORT).show();
        }
        if (!validatePhoneNumber()){
            mPhoneNumberField.setError("Invalid phone");
            progressDialog.dismiss();
            return;
        }
        progressDialog.show();
        String phone = "+91" + mPhoneNumberField.getText().toString();
        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, PhoneVerification.this, mCallbacks);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            try{
                                mStatusText.setText("Welcome user, Firebase ID: " + user.getUid()+" ");
                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }finally {
                                loginSuccess();
                            }

                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                mStatusText.setText("Invalid otp");
                            }

                        }
                    }
                });
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                PhoneVerification.this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        mStatusText.setText("Verification code resent");
        progressDialog.dismiss();
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length()!=10) {
            mPhoneNumberField.setError("Invalid phone");
            return false;
        }
        return true;
    }


    private boolean validateCode() {
        String co = mVerificationField.getText().toString();
        if (co.isEmpty() || (co.length()!=6)){
            return false;
        }
        return true;
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
        startActivity(new Intent(PhoneVerification.this,CompleteProfile.class));
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PhoneVerification.this,FirebaseMainAuthActiviy.class));
        finish();
    }

}

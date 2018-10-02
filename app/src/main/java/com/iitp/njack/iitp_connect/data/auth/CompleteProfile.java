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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.core.home.HomeActivity;
import com.iitp.njack.iitp_connect.data.user.Student_Profile_model;

public class CompleteProfile extends AppCompatActivity {

    private EditText name,email,phone,college,roll;
    private Spinner year,dept;
    private CardView proceed;
    private ProgressDialog progressDialog;
    private FirebaseUser user;
    private NetworkInfo info;
    private ConnectivityManager connectivityManager;
    boolean isNetworkactive;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tell us about you");

        connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        isNetworkactive = info!=null && info.isConnectedOrConnecting();
        if (!isNetworkactive){
            Toast.makeText(getApplicationContext(),"No internet", Toast.LENGTH_SHORT).show();
        }

        user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        name = findViewById(R.id.nameCP);
        email = findViewById(R.id.emailCP);
        phone = findViewById(R.id.phoneCP);
        college = findViewById(R.id.collegeCP);
        roll = findViewById(R.id.rollCP);
        year = findViewById(R.id.year);
        dept = findViewById(R.id.dept);

//        Toast.makeText(this, user.getUid()+" ", Toast.LENGTH_SHORT).show();

        proceed = findViewById(R.id.proceedCP);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }


    private void saveUserData(){

        if (TextUtils.isEmpty(name.getText().toString())){
            name.setError("Invalid");
            return;
        }if (TextUtils.isEmpty(email.getText().toString())){
            email.setError("Invalid");
            return;
        }if (TextUtils.isEmpty(phone.getText().toString())){
            phone.setError("Invalid");
            return;
        }if (TextUtils.isEmpty(college.getText().toString())){
            college.setError("Invalid");
            return;
        }if (TextUtils.isEmpty(roll.getText().toString())){
            roll.setError("Invalid");
            return;
        }if (!isNetworkactive){
            Toast.makeText(this, "Internet connection is unavailable", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();

        //Saving data in sharedprefs
        //editor.putBoolean("key_name", true); // Storing boolean - true/false
        // Storing string
//        editor.putInt("key_name", "int value"); // Storing integer
//        editor.putFloat("key_name", "float value"); // Storing float
//        editor.putLong("key_name", "long value"); // Storing long

        Student_Profile_model st = new Student_Profile_model();
        st.setName(name.getText().toString());
        st.setEmail(email.getText().toString());
        st.setPhone(phone.getText().toString());
        st.setCollege(college.getText().toString());
        st.setRoll(roll.getText().toString());
        st.setYear(year.getSelectedItem().toString());
        st.setDept(dept.getSelectedItem().toString());
        st.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());

        FirebaseDatabase.getInstance().getReference().child("User_data")
                .push()
                .setValue(st)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //To do 
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            finish();
                        }
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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You cannot skip this step, please continue", Toast.LENGTH_SHORT).show();
//        super.onBackPressed();
//
//        startActivity(new Intent(getApplicationContext(),Home.class));
//        finish();
    }
    
}

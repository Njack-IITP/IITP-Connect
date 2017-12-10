package com.iitp.njack.iitp_connect.CodingCalendar.Activities;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.iitp.njack.iitp_connect.R;

public class Settings extends AppCompatActivity {
    CheckBox codechef;
    CheckBox codeforces;
    CheckBox hackerrank;
    CheckBox topcoder;
    CheckBox urioj;
    CheckBox hackerearth;

    private Boolean CODECHEF;
    private Boolean CODEFORCES;
    private Boolean HACKERRANK;
    private Boolean TOPCODER;
    private Boolean URIOJ;
    private Boolean HACKEREARTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        codechef = (CheckBox) findViewById(R.id.codechef);
        codeforces = (CheckBox) findViewById(R.id.codeForces);
        hackerrank = (CheckBox) findViewById(R.id.hackerRank);
        topcoder = (CheckBox) findViewById(R.id.topCoder);
        urioj = (CheckBox) findViewById(R.id.URIOJ);

        hackerearth = (CheckBox) findViewById(R.id.hackerearth);
        SharedPreferences i = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        CODECHEF = i.getBoolean(getString(R.string.codechef), true);
        CODEFORCES = i.getBoolean(getString(R.string.codeforces), true);
        HACKERRANK = i.getBoolean(getString(R.string.hackerrank), true);
        TOPCODER = i.getBoolean(getString(R.string.topcoder), true);
        URIOJ = i.getBoolean(getString(R.string.urioj), true);
        HACKEREARTH = i.getBoolean(getString(R.string.hackerearth), true);

        codechef.setChecked(CODECHEF);
        codeforces.setChecked(CODEFORCES);
        hackerrank.setChecked(HACKERRANK);
        topcoder.setChecked(TOPCODER);
        urioj.setChecked(URIOJ);
        hackerearth.setChecked(HACKEREARTH);
        codechef.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CODECHEF = isChecked;
            }
        });

        codeforces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CODEFORCES = isChecked;
            }
        });

        hackerrank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HACKERRANK = isChecked;
            }
        });

        topcoder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TOPCODER = isChecked;
            }
        });

        urioj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                URIOJ = isChecked;
            }
        });

        hackerearth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HACKEREARTH = isChecked;
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calender_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void save(){
        SharedPreferences i = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor e = i.edit();
        e.putBoolean(getString(R.string.codechef), CODECHEF);
        e.putBoolean(getString(R.string.codeforces), CODEFORCES);
        e.putBoolean(getString(R.string.hackerrank), HACKERRANK);
        e.putBoolean(getString(R.string.topcoder), TOPCODER);
        e.putBoolean(getString(R.string.urioj), URIOJ);
        e.putBoolean(getString(R.string.hackerearth), HACKEREARTH);
        e.commit();
        finish();
    }
}
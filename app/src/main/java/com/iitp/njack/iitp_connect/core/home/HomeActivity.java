package com.iitp.njack.iitp_connect.core.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;
import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.databinding.ActivityHomeBinding;
import com.iitp.njack.iitp_connect.databinding.MainNavHeaderBinding;

import javax.inject.Inject;

/**
 * The HomeActivity which acts as the entry point to all the other Activities and Fragments
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RC_SIGN_IN = 123;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityHomeBinding binding;
    private MainNavHeaderBinding headerBinding;

    private DrawerNavigator drawerNavigator;
    private AuthViewModel authViewModel;

    private LiveData<FirebaseUser> firebaseUserLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        headerBinding = MainNavHeaderBinding.bind(binding.navView.getHeaderView(0));

        authViewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthViewModel.class);

        setSupportActionBar(binding.main.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.main.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        drawerNavigator = new DrawerNavigator(this, authViewModel);

        firebaseUserLiveData = AuthViewModel.getFirebaseAuthLiveData();

        firebaseUserLiveData.observe(HomeActivity.this, firebaseUser -> {
            if (firebaseUser == null) {
                binding.navView.getMenu().findItem(R.id.nav_logout).setTitle("Login");
            } else {
                binding.navView.getMenu().findItem(R.id.nav_logout).setTitle("Logout");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                binding.navView.setCheckedItem(item.getItemId());
                drawerNavigator.selectItem(item);
                binding.drawerLayout.removeDrawerListener(this);
            }
        });
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode != RESULT_OK) {
                if (response == null) {
                    Toast.makeText(this,R.string.sign_in_cancelled,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this,R.string.no_internet_connection,Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this,R.string.unknown_error,Toast.LENGTH_SHORT).show();
                Log.e( "Sign-in error: ", response.getError().toString());
            }
            else {
                Toast.makeText(this,"Signed in successfully",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

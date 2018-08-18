package com.iitp.njack.iitp_connect.core.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.databinding.ActivityHomeBinding;
import com.iitp.njack.iitp_connect.databinding.MainNavHeaderBinding;

import javax.inject.Inject;

/**
 * The HomeActivity which acts as the entry point to all the other Activities and Fragments
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityHomeBinding binding;
    private MainNavHeaderBinding headerBinding;

    private DrawerNavigator drawerNavigator;
    private AuthViewModel authViewModel;

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
}

package com.example.crosstalk;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crosstalk.fragments.homeActivity.CompanyFragment;
import com.example.crosstalk.fragments.homeActivity.SecondFragment;
import com.example.crosstalk.fragments.homeActivity.HomeFragment;
import com.example.crosstalk.fragments.homeActivity.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView nav;
    HomeFragment homeFragment = new HomeFragment();
    SecondFragment secondFragment = new SecondFragment();
    CompanyFragment companyFragment = new CompanyFragment();
    NotificationsFragment notificationsFragment = new NotificationsFragment();

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Removing header
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // Initializations
        nav = findViewById(R.id.nav);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout, homeFragment)
                .commit();

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout, homeFragment).commit();
                        break;

                    case R.id.job:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout, companyFragment).commit();
                        break;

                    case R.id.notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout, notificationsFragment).commit();
                        break;

                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout, secondFragment).commit();
                        break;

                    default:

                }
                return true;
            }
        });

    }
}
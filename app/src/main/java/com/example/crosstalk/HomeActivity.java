package com.example.crosstalk;
import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button registerButton;

    BottomNavigationView nav;

    HomeActivityFragment homeActivityFragment = new HomeActivityFragment();
    BlankFragment2 blankFragment2 = new BlankFragment2();
    BlankFragment3 blankFragment3 = new BlankFragment3();
    BlankFragment4 blankFragment4 = new BlankFragment4();




    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        nav = findViewById(R.id.nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.layout, homeActivityFragment).commit();

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout, homeActivityFragment).commit();
                        Toast.makeText(HomeActivity.this,"Home",Toast.LENGTH_LONG).show();
                        break;

                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout,blankFragment2).commit();
                        Toast.makeText(HomeActivity.this,"search",Toast.LENGTH_LONG).show();
                        break;

                    case R.id.notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout,blankFragment3).commit();
                        Toast.makeText(HomeActivity.this,"notification",Toast.LENGTH_LONG).show();
                        break;

                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout,blankFragment4).commit();
                        Toast.makeText(HomeActivity.this,"account",Toast.LENGTH_LONG).show();
                        break;

                    default:

                }
                return true;
            }
        });

    }
}
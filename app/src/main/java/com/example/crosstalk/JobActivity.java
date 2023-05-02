package com.example.crosstalk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;

import com.example.crosstalk.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.crosstalk.fragments.jobActivity.JobsOfCompanyFragment;

public class JobActivity extends AppCompatActivity {

    Activity activity;
    JobsOfCompanyFragment jo_cf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        // Declarations
        ImageView goBackButton;

        // Removing header
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // Initializations
        activity = this;
        jo_cf = new JobsOfCompanyFragment();
        goBackButton = findViewById(R.id.goBackButton);

        goToJobListing();

        // Listeners
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGoBack();
            }
        });
    }

    /**
     * @TODO Document this
     */
    public void handleGoBack() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.jobFragmentsContainer);
        if (fragment instanceof JobsOfCompanyFragment)
            finish();
        else
            goToJobListing();
    }

    /**
     * @TODO Document this
     */
    public void goToJobListing() {

        Intent intent;
        Bundle bundle;
        String companyId;
        String companyName;

        intent = getIntent();
        bundle = new Bundle();

        // Getting company id
        companyId = intent.getStringExtra(Constants.COMPANY_ID);
        companyName = intent.getStringExtra(Constants.COMPANY_NAME);

        bundle.putString(Constants.COMPANY_ID, companyId);
        bundle.putString(Constants.COMPANY_NAME, companyName);

        jo_cf.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.jobFragmentsContainer, jo_cf);
        transaction.commit();
    }


}

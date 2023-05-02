package com.example.crosstalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.crosstalk.fragments.companyActivity.CreateCompanyFragment;
import com.example.crosstalk.fragments.companyActivity.EditCompanyFragment;
import com.example.crosstalk.utils.Constants;

public class CompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        // Declarations
        Intent intent;
        Bundle bundle;
        String companyId;
        String companyName;
        String companyLocation;
        ImageView goBackButton;
        EditCompanyFragment ecf;
        CreateCompanyFragment ccf;

        // Removing header
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // Initializations
        intent = getIntent();
        ecf = new EditCompanyFragment();
        ccf = new CreateCompanyFragment();
        goBackButton = findViewById(R.id.goBackButton);

        // Getting company id
        companyId = intent.getStringExtra(Constants.COMPANY_ID);
        companyName = intent.getStringExtra(Constants.COMPANY_NAME);
        companyLocation = intent.getStringExtra(Constants.COMPANY_LOCATION);

        if(companyId != null) {
            bundle= new Bundle();
            bundle.putString(Constants.COMPANY_ID, companyId);
            bundle.putString(Constants.COMPANY_NAME, companyName);
            bundle.putString(Constants.COMPANY_LOCATION, companyLocation);
            ecf.setArguments(bundle);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.companyFragmentContainer, companyId == null ? ccf : ecf);
        transaction.commit();

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
        finish();
    }

}
package com.example.crosstalk.fragments.homeActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crosstalk.CompanyActivity;
import com.example.crosstalk.R;
import com.example.crosstalk.adapters.CompaniesRecyclerAdapter;
import com.example.crosstalk.models.CompanyServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.CompaniesService;
import com.example.crosstalk.services.ShareDataService;
import com.example.crosstalk.utils.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyFragment} factory method to
 * create an instance of this fragment.
 */
public class CompanyFragment extends Fragment {

    View progressBar;
    ShareDataService sds;
    SharedPreferences sharedPref;
    CompaniesService companiesService;
    CompaniesRecyclerAdapter companiesRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Declarations
        View view;
        TextView createJobButton;
        RecyclerView recyclerView;
        String authorizationHeader;
        ApiService.CompanyServiceInterface companyService;

        // Initializations
        view = inflater.inflate(R.layout.fragment_company, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.companiesList);
        createJobButton = view.findViewById(R.id.createJobButton);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        companyService = ApiService.getCompanyService();

        companiesRecyclerAdapter = new CompaniesRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(companiesRecyclerAdapter);

        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");
        companiesService = new CompaniesService(companyService, this, authorizationHeader);

        progressBar.setVisibility(View.VISIBLE);
        companiesService.getMyCompanies();

        // Listeners
        createJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateCompany();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Fetch the companies and update the UI here
        sds = new ViewModelProvider(requireActivity()).get(ShareDataService.class);

        progressBar.setVisibility(View.VISIBLE);
        companiesService.getMyCompanies();
    }

    public void goToCreateCompany() {
        Intent intent = new Intent(getContext(), CompanyActivity.class);
        startActivity(intent);
    }

    public void updateUi(List<CompanyServiceModel.CompanyModel> companies) {
        companiesRecyclerAdapter.setCompanies(companies);
        progressBar.setVisibility(View.GONE);
    }

    public void showError(String error) {
        Log.e(Constants.RESPONSE_LOG_STR, error);
    }

}
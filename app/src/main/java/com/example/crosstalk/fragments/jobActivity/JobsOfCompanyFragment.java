package com.example.crosstalk.fragments.jobActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.crosstalk.R;
import com.example.crosstalk.adapters.JobsOfCompanyRecyclerAdapter;
import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.JobServices;
import com.example.crosstalk.utils.Constants;

import java.util.List;

/**
 * @TODO Document this
 */
public class JobsOfCompanyFragment extends Fragment {

    View progressBar;
    String companyId;
    JobServices jobServices;
    SharedPreferences sharedPref;
    View resultsNotFoundContainer;
    FrameLayout fragmentsContainer;
    FragmentManager fragmentManager;

    CreateJobFragment createJobFragment;
    JobsOfCompanyRecyclerAdapter jobsOfCompanyRecyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Declarations
        View view;
        Bundle bundle;
        String companyName;
        TextView createJobButton;
        RecyclerView recyclerView;
        String authorizationHeader;
        int maxLengthOfCompanyName;
        TextView jobsOfCompanyHeading;
        Boolean showHalfNameOfCompany;
        ApiService.JobServiceInterface jobService;

        // Getting companyId
        companyId = getArguments().getString(Constants.COMPANY_ID);
        companyName = getArguments().getString(Constants.COMPANY_NAME);

        if (companyId == null || companyName == null)
            showError(Constants.SOMETHING_WENT_WRONG);

        // Initializations
        view = inflater.inflate(R.layout.fragment_jobs_of_company, container, false);

        maxLengthOfCompanyName = 10;

        jobService = ApiService.getJobService();
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.jobsOfCompanyList);
        createJobButton = view.findViewById(R.id.createJobButton);
        jobsOfCompanyHeading = view.findViewById(R.id.jobsOfCompanyHeading);
        resultsNotFoundContainer = view.findViewById(R.id.resultsNotFoundContainer);
        fragmentsContainer = requireActivity().findViewById(R.id.jobFragmentsContainer);

        if (companyName.length() > maxLengthOfCompanyName)
            showHalfNameOfCompany = true;
        else
            showHalfNameOfCompany = false;

        resultsNotFoundContainer.setVisibility(View.GONE);
        jobsOfCompanyHeading.setText((showHalfNameOfCompany ? companyName.substring(0, maxLengthOfCompanyName - 3) + "..." : companyName) + "'s Jobs");

        fragmentManager = getActivity().getSupportFragmentManager();
        jobsOfCompanyRecyclerAdapter = new JobsOfCompanyRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        recyclerView.setAdapter(jobsOfCompanyRecyclerAdapter);

        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");
        jobServices = new JobServices(jobService, this, authorizationHeader);

        progressBar.setVisibility(View.VISIBLE);
        jobServices.getJobsOfCompany(companyId);

        bundle = new Bundle();
        createJobFragment = new CreateJobFragment();
        bundle.putString(Constants.COMPANY_ID, companyId);
        bundle.putString(Constants.COMPANY_NAME, companyName);
        createJobFragment.setArguments(bundle);


        // Listeners
        createJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.jobFragmentsContainer, createJobFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    public void updateUi(List<JobServiceModel.MyJobModel> jobsOfCompany) {
        jobsOfCompanyRecyclerAdapter.setJobs(jobsOfCompany, fragmentManager, companyId);
        progressBar.setVisibility(View.GONE);
        if (jobsOfCompany.size() == 0)
            resultsNotFoundContainer.setVisibility(View.VISIBLE);
    }

    public void showError(String error) {
        Log.e(Constants.RESPONSE_LOG_STR, error);
    }

}
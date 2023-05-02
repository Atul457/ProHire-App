package com.example.crosstalk.fragments.jobActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crosstalk.R;
import com.example.crosstalk.adapters.ApplicantsOfJobRecyclerAdapter;
import com.example.crosstalk.models.UserServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.JobServices;
import com.example.crosstalk.utils.Constants;

import java.util.List;

/**
 * @TODO Document this
 */
public class ApplicantsOfJobFragment extends Fragment {

    String jobId;
    View progressBar;
    JobServices jobServices;
    View resultsNotFoundContainer;
    SharedPreferences sharedPref;
    FragmentManager fragmentManager;

    ApplicantsOfJobFragment applicantsOfJobFragment;
    ApplicantsOfJobRecyclerAdapter applicantsOfJobRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Declarations
        View view;
        String jobName;
        int maxLengthOfJobName;
        TextView applicantsOfJobHeading;
        Boolean showHalfNameOfJob;
        RecyclerView recyclerView;
        String authorizationHeader;
        ApiService.JobServiceInterface jobService;

        // Initializations
        maxLengthOfJobName = 15;
        view = inflater.inflate(R.layout.fragment_applicants_of_job, container, false);

        jobService = ApiService.getJobService();
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.applicantsOfJob);
        applicantsOfJobHeading = view.findViewById(R.id.applicantsOfJobHeading);
        fragmentManager = getActivity().getSupportFragmentManager();
        resultsNotFoundContainer = view.findViewById(R.id.resultsNotFoundContainer);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Getting jobId
        jobId = getArguments().getString(Constants.JOB_ID);
        jobName = getArguments().getString(Constants.JOB_NAME);

        if (jobName.length() > maxLengthOfJobName)
            showHalfNameOfJob = true;
        else
            showHalfNameOfJob = false;

        resultsNotFoundContainer.setVisibility(View.GONE);
        applicantsOfJobHeading.setText((showHalfNameOfJob ? jobName.substring(0, maxLengthOfJobName - 3) + "..." : jobName) + "'s Applicants");

        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");
        jobServices = new JobServices(jobService, this, authorizationHeader);

        if (jobId == null || jobName == null)
            showError(Constants.SOMETHING_WENT_WRONG);

        applicantsOfJobFragment = new ApplicantsOfJobFragment();
        applicantsOfJobRecyclerAdapter = new ApplicantsOfJobRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(applicantsOfJobRecyclerAdapter);

        progressBar.setVisibility(View.VISIBLE);
        jobServices.getApplicantsOfJob(jobId);

        return view;
    }

    public void updateUi(List<UserServiceModel.UserModel> applicants) {
        applicantsOfJobRecyclerAdapter.setApplicants(applicants, fragmentManager);
        progressBar.setVisibility(View.GONE);
        if (applicants.size() == 0)
            resultsNotFoundContainer.setVisibility(View.VISIBLE);
    }


    public void showError(String error) {
        Log.e(Constants.RESPONSE_LOG_STR, error);
    }

}
package com.example.crosstalk.fragments.homeActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.example.crosstalk.R;
import com.example.crosstalk.adapters.HomeActivityRecyclerAdapter;
import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.HomepageService;
import com.example.crosstalk.services.ShareDataService;
import com.example.crosstalk.utils.Constants;

import java.util.List;

public class HomeFragment extends Fragment {

    View view;
    Object query;
    View progressBar;
    ShareDataService sds;
    SearchView searchBox;
    Fragment searchFragment;
    String authorizationHeader;
    SharedPreferences sharedPref;
    View resultsNotFoundContainer;
    HomepageService homepageService;
    FragmentManager fragmentManager;
    HomeActivityRecyclerAdapter homeActivityRecyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Declarations
        String searchFor;

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_activity_fragment, container, false);

        // Initializations
        searchBox = view.findViewById(R.id.searchBox);
        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView recyclerView = view.findViewById(R.id.job_profiles);
        resultsNotFoundContainer = view.findViewById(R.id.resultsNotFoundContainer);

        searchFragment = new SearchFragment();
        homeActivityRecyclerAdapter = new HomeActivityRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        // If there is something sent from search activity,
        // then search set the query/search value to that value
        sds = new ViewModelProvider(requireActivity()).get(ShareDataService.class);
        searchFor = sds.getSearchedFor();

        if (searchFor != null && searchFor.trim().length() > 0) {
            query = searchFor;
            searchBox.setQuery(searchFor, false);
        } else {
            query = null;
        }

        recyclerView.setAdapter(homeActivityRecyclerAdapter);

        if (isAdded()) {
            fragmentManager = getActivity().getSupportFragmentManager();
        }

        ApiService.JobServiceInterface jobService = ApiService.getJobService();

        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");
        homepageService = new HomepageService(jobService, this, authorizationHeader);
        progressBar.setVisibility(View.VISIBLE);
        resultsNotFoundContainer.setVisibility(View.INVISIBLE);
        homepageService.getJobs(query, null, null, null, null, null);

        searchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction().replace(R.id.layout, searchFragment).commit();
                }
            }
        });

        searchBox.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && fragmentManager != null) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.layout, searchFragment)
                            .commit();
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        sds.setSearchedFor("");
        // Perform operation when Fragment is paused
    }


    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        resultsNotFoundContainer.setVisibility(View.INVISIBLE);
        homepageService.getJobs(query, null, null, null, null, null);
    }


    public void updateUi(List<JobServiceModel.JobModel> jobs) {
        if (jobs.size() == 0)
            resultsNotFoundContainer.setVisibility(View.VISIBLE);
        else
            resultsNotFoundContainer.setVisibility(View.GONE);
        homeActivityRecyclerAdapter.setJobs(jobs);
        progressBar.setVisibility(View.GONE);
    }

    public void showError(String error) {
        Log.w("error", error);
    }

}
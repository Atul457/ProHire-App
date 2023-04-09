package com.example.crosstalk.fragments.homeActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.List;

public class HomeFragment extends Fragment {

    View view;
    View progressBar;
    SearchView searchBox;
    // Number currPage = 1;
    Fragment searchFragment;
    HomepageService homepageService;
    FragmentManager fragmentManager;
    // List<JobServiceModel.JobModel> jobs = null;
    HomeActivityRecyclerAdapter homeActivityRecyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Declarations
        Object query;
        String searchFor;
        ShareDataService sds;

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_activity_fragment, container, false);

        // Initializations
        searchBox = view.findViewById(R.id.searchBox);
        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView recyclerView = view.findViewById(R.id.job_profiles);

        searchFragment = new SearchFragment();
        homeActivityRecyclerAdapter = new HomeActivityRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // If there is something sent from search activity,
        // then search set the query/search value to that value
        sds = new ViewModelProvider(requireActivity()).get(ShareDataService.class);
        searchFor = sds.getSearchedFor();

        if (searchFor != null && searchFor.trim().length() > 0) {
            query = searchFor;
            searchBox.setQuery(searchFor, false);
        } else
            query = null;

        recyclerView.setAdapter(homeActivityRecyclerAdapter);

        if (isAdded())
            fragmentManager = getActivity().getSupportFragmentManager();

        ApiService.JobServiceInterface jobService = ApiService.getJobService();

        homepageService = new HomepageService(jobService, this);
        progressBar.setVisibility(View.VISIBLE);
        homepageService.getJobs(query, 1, null, 20, null, null);

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

    public void updateUi(List<JobServiceModel.JobModel> jobs) {
        homeActivityRecyclerAdapter.setJobs(jobs);
        progressBar.setVisibility(View.GONE);
    }

    public void showError(String error) {
        Log.w("error", error);
    }

}
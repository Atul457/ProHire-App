package com.example.crosstalk.fragments.homeActivity;

import android.content.SharedPreferences;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.example.crosstalk.R;
import com.example.crosstalk.adapters.SearchResultsRecyclerAdapter;
import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.HomepageService;
import com.example.crosstalk.services.ShareDataService;
import com.example.crosstalk.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    View view;
    View progressBar;
    String searchValue;
    SearchView searchBox;
    ShareDataService sds;
    HomeFragment homeFragment;
    RecyclerView recyclerView;
    String authorizationHeader;
    SharedPreferences sharedPref;
    View resultsNotFoundContainer;
    List<String> searchedDataList;
    FragmentManager fragmentManager;
    SearchResultsRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Declarations
        String searchFor;
        ApiService.JobServiceInterface jobService;

        // Initializations
        view = inflater.inflate(R.layout.search_fragment, container, false);
        recyclerView = view.findViewById(R.id.searchResultRecyclerView);
        sds = new ViewModelProvider(requireActivity()).get(ShareDataService.class);

        if (isAdded())
            fragmentManager = getActivity().getSupportFragmentManager();

        homeFragment = new HomeFragment();
        searchedDataList = new ArrayList<>();
        jobService = ApiService.getJobService();
        searchBox = view.findViewById(R.id.searchBox);
        progressBar = view.findViewById(R.id.progressBar);
        resultsNotFoundContainer = view.findViewById(R.id.resultsNotFoundContainer);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Focusing search box
        searchBox.requestFocus();

        recyclerView.setVisibility(View.GONE);
        resultsNotFoundContainer.setVisibility(View.GONE);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");
        HomepageService homepageService = new HomepageService(jobService, this, authorizationHeader);

        adapter = new SearchResultsRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);

        // If there is something sent from search activity,
        // then search set the query/search value to that value
        sds = new ViewModelProvider(requireActivity()).get(ShareDataService.class);
        searchFor = sds.getSearchedFor();

        if (searchFor != null && searchFor.trim().length() > 0) {
            searchBox.setQuery(searchFor, false);
            homepageService.getJobs(searchFor, 1, null, 20, null, null);
        }


        // Listeners
        searchBox.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    openKeyboard();
            }
        });

        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFor(query);
                searchValue = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchValue = query;
                if (query != null && query.length() == 0) {
                    sds.setSearchedFor(null);
                    updateUi(new ArrayList<>());
                } else {
                    handleLoader(true);
                    homepageService.getJobs(query, 1, null, 20, null, null);
                }
                return false;
            }
        });


        return view;
    }

    public void handleLoader(Boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void updateUi(List<JobServiceModel.JobModel> jobs) {
        if (jobs.size() == 0 && searchValue != null && searchValue.length() > 0)
            resultsNotFoundContainer.setVisibility(View.VISIBLE);
        else
            resultsNotFoundContainer.setVisibility(View.GONE);

        adapter.setSearchResults(jobs);
        handleLoader(false);
    }

    public void searchFor(String query) {
        sds.setSearchedFor(query);
        fragmentManager.beginTransaction().replace(R.id.layout, homeFragment).commit();
    }

    /**
     * Method to open the keyboard
     */
    private void openKeyboard() {
        InputMethodManager imm;
        imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchBox, InputMethodManager.SHOW_IMPLICIT);
    }

    public void showError(String error) {
        Log.w("error", error);
    }

}
package com.example.crosstalk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeActivityFragment extends Fragment {

    View view;

    public class JobProfiles{

        String jobTitle;
        String jobProvider;
        String jobLocation;
        String jobPackage;

        public JobProfiles(String jobTitle, String jobProvider, String jobLocation, String jobPackage){
            this.jobTitle = jobTitle;
            this.jobProvider = jobProvider;
            this.jobLocation = jobLocation;
            this.jobPackage = jobPackage;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_activity_fragment, container, false);

        RecyclerView jobProfiles = view.findViewById(R.id.job_profiles);
        jobProfiles.setLayoutManager(new LinearLayoutManager(getContext()));

        JobProfiles[] jobLists = new JobProfiles[5];
        jobLists[0]  = new JobProfiles("Web developer", "Mind roots", "Chandigarh, punjab", "20k-30k");
        jobLists[1]  = new JobProfiles("Backend developer", "Infosis", "Zirakpur, punjab", "50k-80k");
        jobLists[2]  = new JobProfiles("Web developer", "Mind roots", "Chandigarh, punjab", "20k-30k");
        jobLists[3]  = new JobProfiles("Web developer", "Mind roots", "Chandigarh, punjab", "20k-30k");
        jobLists[4]  = new JobProfiles("Web developer", "Mind roots", "Chandigarh, punjab", "20k-30k");

        jobProfiles.setAdapter(new HomeActivityRecyclerAdapter(jobLists));

        return view;
    }
}
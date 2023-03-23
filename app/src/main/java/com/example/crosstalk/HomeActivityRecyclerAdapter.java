package com.example.crosstalk;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivityRecyclerAdapter extends RecyclerView.Adapter<HomeActivityRecyclerAdapter.HomeActivityRecyclerViewHolder> {

    private HomeActivityFragment.JobProfiles[] jobProfiles;

    public HomeActivityRecyclerAdapter(HomeActivityFragment.JobProfiles[] data){
        this.jobProfiles = data;
    }

    @NonNull
    @Override
    public HomeActivityRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.job_profile, parent, false);
        return new HomeActivityRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeActivityRecyclerViewHolder holder, int position) {

        String jobTitle = jobProfiles[position].jobTitle;
        String jobProvider = jobProfiles[position].jobProvider;
        String jobLocation = jobProfiles[position].jobLocation;
        String jobPackage = jobProfiles[position].jobPackage;

        holder.jobTitle.setText(jobTitle);
        holder.jobProvider.setText(jobProvider);
        holder.jobLocation.setText(jobLocation);
        holder.jobPackage.setText(jobPackage);
    }

    @Override
    public int getItemCount() {
        return this.jobProfiles.length;
    }

    public class HomeActivityRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitle;
        TextView jobProvider;
        TextView jobLocation;
        TextView jobPackage;

        public HomeActivityRecyclerViewHolder(@NonNull View itemView) {

            super(itemView);

            jobTitle = itemView.findViewById(R.id.job_title);
            jobProvider = itemView.findViewById(R.id.job_provider);
            jobLocation = itemView.findViewById(R.id.job_location);
            jobPackage = itemView.findViewById(R.id.job_package);

        }
    }

}

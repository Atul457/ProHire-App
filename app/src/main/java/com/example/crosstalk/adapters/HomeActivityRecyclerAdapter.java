package com.example.crosstalk.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crosstalk.JobDetailsActivity;
import com.example.crosstalk.R;
import com.example.crosstalk.models.JobServiceModel;

import java.util.List;

public class HomeActivityRecyclerAdapter extends RecyclerView.Adapter<HomeActivityRecyclerAdapter.HomeActivityRecyclerViewHolder> {

    private List<JobServiceModel.JobModel> jobs;

    public void setJobs(List<JobServiceModel.JobModel> jobs) {
        this.jobs = jobs;
        notifyDataSetChanged();
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

        JobServiceModel.JobModel job = jobs.get(position);
        String jobTitle = job.getJob_name();
        String jobLocation = job.getCompany_location();
        String jobPackage = job.getJob_salary().toString();
        String jobProvider = job.getCompany_name();
        String jobReviews = job.getReviews().toString();

        holder.jobTitle.setText(jobTitle);
        holder.jobProvider.setText(jobProvider);
        holder.jobLocation.setText(jobLocation);
        holder.jobPackage.setText(jobPackage);
        holder.jobReviews.setText(jobReviews.trim() + " Reviews");
    }

    @Override
    public int getItemCount() {
        if (this.jobs != null) return this.jobs.size();
        else return 0;
    }

    public class HomeActivityRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView jobTitle;
        TextView jobProvider;
        TextView jobLocation;
        TextView jobPackage;

        TextView jobReviews;

        public HomeActivityRecyclerViewHolder(@NonNull View itemView) {

            super(itemView);

            jobTitle = itemView.findViewById(R.id.job_title);
            jobProvider = itemView.findViewById(R.id.job_provider);
            jobLocation = itemView.findViewById(R.id.job_location);
            jobPackage = itemView.findViewById(R.id.job_package);
            jobReviews = itemView.findViewById(R.id.job_reviews);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // Get the item position.
            int position = getBindingAdapterPosition();
            JobServiceModel.JobModel job = jobs.get(position);
            String jobId = jobs.get(position).get_id();
            Log.w("jobId", jobId);

            // Start the new activity.
            Context context = v.getContext();
            Intent intent = new Intent(context, JobDetailsActivity.class);
            intent.putExtra("jobId", jobId);
            context.startActivity(intent);
        }
    }

}

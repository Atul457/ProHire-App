package com.example.crosstalk.adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crosstalk.R;
import com.example.crosstalk.fragments.jobActivity.ApplicantsOfJobFragment;
import com.example.crosstalk.fragments.jobActivity.CreateJobFragment;
import com.example.crosstalk.fragments.jobActivity.EditJobFragment;
import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.utils.Constants;

import java.util.List;

public class JobsOfCompanyRecyclerAdapter extends RecyclerView.Adapter<JobsOfCompanyRecyclerAdapter.JobsOfCompanyRecyclerViewHolder> {

    String companyId;
    EditJobFragment editJobFragment;
    ApplicantsOfJobFragment applicantsOfJobFragment;
    private FragmentManager fragmentManager;
    private List<JobServiceModel.MyJobModel> jobs;

    public void setJobs(List<JobServiceModel.MyJobModel> jobs, FragmentManager fragmentManager, String companyId) {
        this.jobs = jobs;
        this.companyId = companyId;
        this.fragmentManager = fragmentManager;
        editJobFragment = new EditJobFragment();
        applicantsOfJobFragment = new ApplicantsOfJobFragment();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobsOfCompanyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.job_of_company, parent, false);
        return new JobsOfCompanyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsOfCompanyRecyclerViewHolder holder, int position) {

        JobServiceModel.MyJobModel job = jobs.get(position);
        String jobName = job.getJob_name();
        String jobSalary = job.getJob_salary().toString();
        String jobReviews = job.getReviews().toString();

        holder.jobName.setText(jobName);
        holder.jobSalary.setText(jobSalary);
        holder.jobReviews.setText(jobReviews.trim() + " Reviews");

    }

    @Override
    public int getItemCount() {
        if (this.jobs != null) return this.jobs.size();
        else return 0;
    }

    public class JobsOfCompanyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView jobName;
        TextView jobSalary;
        TextView jobReviews;
        ImageView editJobIcon;

        public JobsOfCompanyRecyclerViewHolder(@NonNull View itemView) {

            super(itemView);

            jobName = itemView.findViewById(R.id.jobName);
            jobSalary = itemView.findViewById(R.id.jobSalary);
            jobReviews = itemView.findViewById(R.id.jobReviews);
            editJobIcon = itemView.findViewById(R.id.editJobIcon);

            itemView.setOnClickListener(this);
            editJobIcon.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            // Declarations
            int position;
            Fragment fragment;
            Bundle bundle;

            // Initializations
            bundle = new Bundle();
            position = getBindingAdapterPosition();

            JobServiceModel.MyJobModel job = jobs.get(position);
            Log.i(Constants.JOB_ID, job.get_id());

            String jobId = job.get_id();
            String jobName = job.getJob_name();
            Number jobSalary = job.getJob_salary();
            String jobDescription = job.getJob_description();

            if (v == editJobIcon) {
                fragment = editJobFragment;
            } else {
                fragment = applicantsOfJobFragment;
            }

            bundle.putString(Constants.JOB_ID, jobId);
            bundle.putString(Constants.JOB_NAME, jobName);
            bundle.putString(Constants.COMPANY_ID, companyId);
            bundle.putLong(Constants.JOB_SALARY, jobSalary.longValue());
            bundle.putString(Constants.JOB_DESCRIPTION, jobDescription);

            fragment.setArguments(bundle);

            // Start the new fragment.
            fragmentManager.beginTransaction()
                    .replace(R.id.jobFragmentsContainer, fragment)
                    .addToBackStack(null)
                    .commit();

        }
    }

}

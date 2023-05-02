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
import com.example.crosstalk.models.UserServiceModel;
import com.example.crosstalk.utils.Constants;

import java.util.List;

public class ApplicantsOfJobRecyclerAdapter extends RecyclerView.Adapter<ApplicantsOfJobRecyclerAdapter.ApplicantsOfJobRecyclerViewHolder> {

    ApplicantsOfJobFragment applicantsOfJobFragment;
    private FragmentManager fragmentManager;
    private List<UserServiceModel.UserModel> applicants;

    public void setApplicants(List<UserServiceModel.UserModel> applicants, FragmentManager fragmentManager) {
        this.applicants = applicants;
        this.fragmentManager = fragmentManager;
        applicantsOfJobFragment = new ApplicantsOfJobFragment();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ApplicantsOfJobRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.applicant_of_job, parent, false);
        return new ApplicantsOfJobRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicantsOfJobRecyclerViewHolder holder, int position) {

        UserServiceModel.UserModel user = applicants.get(position);
        String userName = user.getName();
        String userEmail = user.getEmail();
        String userPhone = user.getPhone();

        holder.userName.setText(userName);
        holder.userEmail.setText(userEmail);
        holder.userPhone.setText(userPhone.trim());

    }

    @Override
    public int getItemCount() {
        if (this.applicants != null) return this.applicants.size();
        else return 0;
    }

    public class ApplicantsOfJobRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userEmail;
        TextView userPhone;

        public ApplicantsOfJobRecyclerViewHolder(@NonNull View itemView) {

            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            userEmail = itemView.findViewById(R.id.userEmail);
            userPhone = itemView.findViewById(R.id.userPhone);

        }

    }

}

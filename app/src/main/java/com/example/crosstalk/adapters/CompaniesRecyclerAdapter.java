package com.example.crosstalk.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crosstalk.CompanyActivity;
import com.example.crosstalk.JobActivity;
import com.example.crosstalk.JobDetailsActivity;
import com.example.crosstalk.R;
import com.example.crosstalk.fragments.homeActivity.CompanyFragment;
import com.example.crosstalk.models.CompanyServiceModel;
import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.utils.Constants;

import java.util.List;

public class CompaniesRecyclerAdapter extends RecyclerView.Adapter<CompaniesRecyclerAdapter.CompaniesViewHolder> {

    private List<CompanyServiceModel.CompanyModel> companies;

    public void setCompanies(List<CompanyServiceModel.CompanyModel> companies) {
        this.companies = companies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompaniesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.company, parent, false);
        return new CompaniesRecyclerAdapter.CompaniesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompaniesViewHolder holder, int position) {

        CompanyServiceModel.CompanyModel company = companies.get(position);
        String companyName = company.getCompany_name();
        String companyLocation = company.getCompany_location();

        holder.companyName.setText(companyName);
        holder.companyLocation.setText(companyLocation);

    }

    @Override
    public int getItemCount() {
        if (this.companies != null) return this.companies.size();
        return 0;
    }

    public class CompaniesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView companyName;
        TextView companyLocation;
        ImageView editCompanyIcon;

        public CompaniesViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.companyName);
            editCompanyIcon = itemView.findViewById(R.id.editCompanyIcon);
            companyLocation = itemView.findViewById(R.id.companyLocation);

            editCompanyIcon.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // Get the item position.
            int position = getBindingAdapterPosition();
            CompanyServiceModel.CompanyModel company = companies.get(position);
            String companyId = company.get_id();
            String companyName = company.getCompany_name();
            String companyLocation = company.getCompany_location();
            Log.w(Constants.RESPONSE_LOG_STR, companyId);

            // Start the new activity.
            Context context = v.getContext();

            Intent intent;

            if (v == editCompanyIcon) {
                intent = new Intent(context, CompanyActivity.class);
                intent.putExtra(Constants.COMPANY_LOCATION, companyLocation);
            } else {
                intent = new Intent(context, JobActivity.class);
            }

            intent.putExtra(Constants.COMPANY_NAME, companyName);
            intent.putExtra(Constants.COMPANY_ID, companyId);
            context.startActivity(intent);

        }
    }

}

package com.example.crosstalk.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crosstalk.R;
import com.example.crosstalk.fragments.homeActivity.SearchFragment;
import com.example.crosstalk.models.JobServiceModel;

import java.util.List;

public class SearchResultsRecyclerAdapter extends RecyclerView.Adapter<SearchResultsRecyclerAdapter.SearchItemViewHolder> {

    View view;
    TextView resultTitle;
    SearchFragment searchFragment;
    private List<JobServiceModel.JobModel> searchResults;

    public SearchResultsRecyclerAdapter(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }

    public void setSearchResults(List<JobServiceModel.JobModel> jobs) {
        this.searchResults = jobs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.searched_item, parent, false);
        return new SearchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        String text = searchResults.get(position).getJob_name();
        resultTitle = holder.searchedItem.findViewById(R.id.resultTitle);
        resultTitle.setText(text);
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }


    /**
     * @info This represents single searched item
     */
    public class SearchItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View searchedItem;

        public SearchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            searchedItem = itemView.findViewById(R.id.searched_item);
            searchedItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the item position.
            int position = getBindingAdapterPosition();
            String jobName = searchResults.get(position).getJob_name();
            Log.w("search for", jobName);
            searchFragment.searchFor(jobName);
        }
    }

}

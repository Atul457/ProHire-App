package com.example.crosstalk.fragments.homeActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crosstalk.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyFragment} factory method to
 * create an instance of this fragment.
 */
public class CompanyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Declarations
        View view;

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_job, container, false);

        return view;
    }
}
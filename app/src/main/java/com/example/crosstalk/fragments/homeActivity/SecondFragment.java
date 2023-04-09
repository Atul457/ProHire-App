package com.example.crosstalk.fragments.homeActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.crosstalk.R;


public class SecondFragment extends Fragment {
    View view;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blank2, container, false);
        imageView = view.findViewById(R.id.image);

        String webContentLink = "https://www.w3schools.com/w3images/fjords.jpg";

        Glide.with(this).load(webContentLink).into(imageView);

        return view;
    }
}
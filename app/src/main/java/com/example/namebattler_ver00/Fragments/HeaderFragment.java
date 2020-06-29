package com.example.namebattler_ver00.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.namebattler_ver00.R;


public class HeaderFragment extends Fragment {

    private static String mTitleText;
    private static Activity mPreActivity;

    public static HeaderFragment newInstance(String titleText, Activity preActivity) {
        HeaderFragment fragment = new HeaderFragment();
        mTitleText = titleText;
        mPreActivity = preActivity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_header, container, false);

        final TextView titleTextView = view.findViewById(R.id.title_text);
        titleTextView.setText(mTitleText);

        final Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), mPreActivity.getClass());
                startActivity(intent);
            }
        });

        return view;
    }
}
package com.vnoders.spotify_el8alaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class ChartsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_charts, container, false);

        Toolbar toolbar = root.findViewById(R.id.charts_fragment_toolbar);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return root;
    }
}

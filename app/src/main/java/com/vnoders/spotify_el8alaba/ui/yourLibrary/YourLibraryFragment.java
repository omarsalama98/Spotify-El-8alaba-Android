package com.vnoders.spotify_el8alaba.ui.yourLibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.vnoders.spotify_el8alaba.R;


public class YourLibraryFragment extends Fragment {

    private YourLibraryViewModel yourLibraryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yourLibraryViewModel =
                ViewModelProviders.of(this).get(YourLibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_your_library, container, false);
        final TextView textView = root.findViewById(R.id.text_your_library);
        yourLibraryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
package com.vnoders.spotify_el8alaba.ui.premium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.R;


public class PremiumFragment extends Fragment {

    private PremiumViewModel premiumViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        premiumViewModel =
                new ViewModelProvider(this).get(PremiumViewModel.class);
        View root = inflater.inflate(R.layout.fragment_premium, container, false);
        final TextView textView = root.findViewById(R.id.text_premium);
        premiumViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        if (navView.getSelectedItemId() != R.id.navigation_premium) {
            navView.setSelectedItemId(R.id.navigation_premium);
        }
    }
}
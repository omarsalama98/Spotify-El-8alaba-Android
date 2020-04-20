package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vnoders.spotify_el8alaba.R;

public class CurrentUserProfileBottomSheet extends BottomSheetDialogFragment {
    private LinearLayout shareLinearLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.current_user_profile_bottom_sheet,container,false);
    shareLinearLayout=view.findViewById(R.id.share);
    shareLinearLayout.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            CurrentUserProfileShareSheet shareSheet=new CurrentUserProfileShareSheet();
            dismiss();
            shareSheet.show(getActivity().getSupportFragmentManager(),"CURRENT_USER_PROFILE_SHARE_SHEET");

        }
    });
    return view;
    }
}

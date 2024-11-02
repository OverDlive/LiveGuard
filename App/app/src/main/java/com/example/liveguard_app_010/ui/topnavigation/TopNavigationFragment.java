package com.example.liveguard_app_010.ui.topnavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.dialogs.AddLocationDialogFragment;

public class TopNavigationFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_top_navigation, container, false);

        // 신고 버튼 클릭 시 동작
        ImageButton reportButton = view.findViewById(R.id.report);
        reportButton.setOnClickListener(v -> {
            // 신고 버튼 클릭 시의 동작을 여기에 추가하세요
        });

        // 지역 추가 버튼 클릭 시 다이얼로그 표시
        ImageButton addLocationButton = view.findViewById(R.id.locationbutton);
        addLocationButton.setOnClickListener(v -> {
            AddLocationDialogFragment dialog = new AddLocationDialogFragment();
            dialog.show(getParentFragmentManager(), "AddLocationDialog");
        });

        return view;
    }
}

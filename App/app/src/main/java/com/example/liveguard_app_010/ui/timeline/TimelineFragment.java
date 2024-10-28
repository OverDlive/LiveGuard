package com.example.liveguard_app_010.ui.timeline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.liveguard_app_010.R;

public class TimelineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 간단한 타임라인 레이아웃을 로드합니다.
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }
}
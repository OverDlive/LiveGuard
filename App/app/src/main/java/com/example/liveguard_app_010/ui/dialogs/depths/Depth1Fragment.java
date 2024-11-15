package com.example.liveguard_app_010.ui.dialogs.depths;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.addlocationdialog.AddLocationDialogFragment;

public class Depth1Fragment extends Fragment {

    public Depth1Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depth1, container, false);

        Button nextButton = view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            // 부모 프래그먼트가 AddLocationDialogFragment 인스턴스인지 확인 후 showDepth2Fragment 호출
            if (getParentFragment() instanceof AddLocationDialogFragment) {
                ((AddLocationDialogFragment) getParentFragment()).showDepth2Fragment();
            }
        });

        return view;
    }
}

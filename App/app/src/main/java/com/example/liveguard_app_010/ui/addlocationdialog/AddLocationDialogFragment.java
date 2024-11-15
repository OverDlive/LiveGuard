package com.example.liveguard_app_010.ui.addlocationdialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.dialogs.depths.Depth1Fragment;
import com.example.liveguard_app_010.ui.dialogs.depths.Depth2Fragment;

public class AddLocationDialogFragment extends DialogFragment {

    public AddLocationDialogFragment() {
        super(R.style.FullScreenDialog);
    }

    // Depth2Fragment로 전환
    public void showDepth2Fragment() {
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(
                        android.R.anim.slide_in_left,  // Depth1에서 Depth2로 이동할 때 애니메이션
                        android.R.anim.slide_out_right, // Depth2에서 Depth1로 돌아갈 때 애니메이션
                        android.R.anim.slide_in_left,  // 뒤로 가기 애니메이션 (Depth2에서 Depth1로 돌아갈 때)
                        android.R.anim.slide_out_right  // 앞으로 가기 애니메이션 (Depth1에서 Depth2로 이동할 때)
                )
                .replace(R.id.dialog_container, new Depth2Fragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_add_location_dialog, container, false);

        // Depth1Fragment로 초기화
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.dialog_container, new Depth1Fragment())
                    .commit();
        }

        // "Close" 버튼 클릭 시 다이얼로그 종료
        ImageButton closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> dismiss());

        return view;
    }
}

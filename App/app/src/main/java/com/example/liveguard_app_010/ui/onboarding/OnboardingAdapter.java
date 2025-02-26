package com.example.liveguard_app_010.ui.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private final Context context; // ✅ Context 추가
    private final List<Integer> onboardingScreens;

    public OnboardingAdapter(Context context, List<Integer> onboardingScreens) {
        this.context = context;
        this.onboardingScreens = onboardingScreens;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(onboardingScreens.get(viewType), parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        // 추가 UI 바인딩 필요 시 작성
    }

    @Override
    public int getItemCount() {
        return onboardingScreens.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

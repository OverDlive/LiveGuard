package com.example.liveguard_app_010.ui.exhibition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveguard_app_010.R;

import java.util.List;

public class ExhibitionOnboardingAdapter extends RecyclerView.Adapter<ExhibitionOnboardingAdapter.ExhibitionViewHolder> {

    private final Context context;
    private final List<Integer> pageLayouts;
    private final ChoiceListener choiceListener;

    public interface ChoiceListener {
        void onChoiceSelected(int pageIndex);
    }

    public ExhibitionOnboardingAdapter(Context context, List<Integer> pageLayouts, ChoiceListener listener) {
        this.context = context;
        this.pageLayouts = pageLayouts;
        this.choiceListener = listener;
    }

    @NonNull
    @Override
    public ExhibitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(pageLayouts.get(viewType), parent, false);
        return new ExhibitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExhibitionViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return pageLayouts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ExhibitionViewHolder extends RecyclerView.ViewHolder {

        public ExhibitionViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(int position) {
            if (position == 0) {
                Button startButton = itemView.findViewById(R.id.btn_start);
                if (startButton != null) {
                    startButton.setOnClickListener(v -> {
                        ((ExhibitionOnboardingActivity) context).moveToNextPage();
                    });
                }
            } else {
                for (int i = 1; i <= 4; i++) {
                    int buttonId = context.getResources().getIdentifier("btn_choice_" + i, "id", context.getPackageName());
                    Button choiceButton = itemView.findViewById(buttonId);
                    if (choiceButton != null) {
                        final int finalPosition = position; // position을 final로 복사
                        choiceButton.setOnClickListener(v -> {
                            choiceListener.onChoiceSelected(finalPosition);
                        });
                    }
                }
            }
        }
    }
}

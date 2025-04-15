package com.example.liveguard_app_010.ui.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.liveguard_app_010.R;
import java.util.List;

public class FoodOnboardingAdapter extends RecyclerView.Adapter<FoodOnboardingAdapter.FoodViewHolder> {

    private final Context context;
    private final List<Integer> pageLayouts;
    private final ChoiceListener choiceListener;

    public interface ChoiceListener {
        void onChoiceSelected(int pageIndex);
    }

    public FoodOnboardingAdapter(Context context, List<Integer> pageLayouts, ChoiceListener listener) {
        this.context = context;
        this.pageLayouts = pageLayouts;
        this.choiceListener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(pageLayouts.get(viewType), parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
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

    class FoodViewHolder extends RecyclerView.ViewHolder {

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(int position) {
            if (position == 0) {
                Button startButton = itemView.findViewById(R.id.btn_start);
                if (startButton != null) {
                    startButton.setOnClickListener(v -> {
                        ((FoodOnboardingActivity) context).moveToNextPage();
                    });
                }
            } else {
                for (int i = 1; i <= 4; i++) {
                    int buttonId = context.getResources().getIdentifier("btn_choice_" + i, "id", context.getPackageName());
                    Button choiceButton = itemView.findViewById(buttonId);
                    if (choiceButton != null) {
                        choiceButton.setOnClickListener(v -> choiceListener.onChoiceSelected(position));
                    }
                }
            }
        }
    }
}

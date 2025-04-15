package com.example.liveguard_app_010.ui.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.liveguard_app_010.R;
import java.util.List;

public class ShoppingOnboardingAdapter extends RecyclerView.Adapter<ShoppingOnboardingAdapter.ShoppingViewHolder> {

    private final Context context;
    private final List<Integer> pageLayouts;
    private final ChoiceListener choiceListener;

    public interface ChoiceListener {
        void onChoiceSelected(int pageIndex);
    }

    public ShoppingOnboardingAdapter(Context context, List<Integer> pageLayouts, ChoiceListener listener) {
        this.context = context;
        this.pageLayouts = pageLayouts;
        this.choiceListener = listener;
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(pageLayouts.get(viewType), parent, false);
        return new ShoppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
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

    class ShoppingViewHolder extends RecyclerView.ViewHolder {

        public ShoppingViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(int position) {
            if (position == 0) {
                Button startButton = itemView.findViewById(R.id.btn_start);
                if (startButton != null) {
                    startButton.setOnClickListener(v -> {
                        ((ShoppingOnboardingActivity) context).moveToNextPage();
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

package com.example.liveguard_app_010.ui.tour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveguard_app_010.R;

import java.util.List;

public class TourOnboardingAdapter extends RecyclerView.Adapter<TourOnboardingAdapter.TourViewHolder> {

    private final List<Integer> pageLayouts;
    private final ChoiceListener choiceListener;
    private final List<List<Integer>> buttonIdLists;
    private final List<List<String>> buttonLabelLists;

    public interface ChoiceListener {
        void onChoiceSelected(int pageIndex, String choiceValue);
    }

    public TourOnboardingAdapter(List<Integer> pageLayouts, List<List<Integer>> buttonIdLists, List<List<String>> buttonLabelLists, ChoiceListener listener) {
        this.pageLayouts = pageLayouts;
        this.buttonIdLists = buttonIdLists;
        this.buttonLabelLists = buttonLabelLists;
        this.choiceListener = listener;
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(pageLayouts.get(viewType), parent, false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
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

    class TourViewHolder extends RecyclerView.ViewHolder {

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(int position) {
            // Bind choice buttons for each page
            List<Integer> ids = buttonIdLists.get(position);
            List<String> labels = buttonLabelLists.get(position);
            for (int i = 0; i < ids.size(); i++) {
                Button choiceButton = itemView.findViewById(ids.get(i));
                if (choiceButton != null) {
                    choiceButton.setText(labels.get(i));
                    String value = labels.get(i);
                    choiceButton.setOnClickListener(v -> {
                        int adapterPos = getAbsoluteAdapterPosition();
                        if (adapterPos != RecyclerView.NO_POSITION) {
                            choiceListener.onChoiceSelected(adapterPos, value);
                        }
                    });
                }
            }
        }
    }
}

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

    private final Context context;
    private final List<Integer> pageLayouts;
    private final ChoiceListener choiceListener;
    private final List<List<Integer>> buttonIdLists;
    private final List<List<String>> buttonLabelLists;

    public interface ChoiceListener {
        void onChoiceSelected(int pageIndex, String choiceValue);
    }

    public TourOnboardingAdapter(Context context, List<Integer> pageLayouts, List<List<Integer>> buttonIdLists, List<List<String>> buttonLabelLists, ChoiceListener listener) {
        this.context = context;
        this.pageLayouts = pageLayouts;
        this.buttonIdLists = buttonIdLists;
        this.buttonLabelLists = buttonLabelLists;
        this.choiceListener = listener;
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(pageLayouts.get(viewType), parent, false);
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
            if (position == 0) {
                // 시작하기 버튼 처리
                Button startButton = itemView.findViewById(R.id.btn_start);
                if (startButton != null) {
                    startButton.setOnClickListener(v -> {
                        ((TourOnboardingActivity) context).moveToNextPage(); // ✅ 그냥 다음 페이지로 넘기기만!
                    });
                }
            } else {
                // 선택지 버튼 처리
                List<Integer> ids = buttonIdLists.get(position - 1);
                List<String> labels = buttonLabelLists.get(position - 1);
                for (int i = 0; i < ids.size(); i++) {
                    Button choiceButton = itemView.findViewById(ids.get(i));
                    if (choiceButton != null) {
                        choiceButton.setText(labels.get(i));
                        String value = labels.get(i);
                        choiceButton.setOnClickListener(v -> {
                            choiceListener.onChoiceSelected(position, value);
                        });
                    }
                }
            }
        }
    }
}

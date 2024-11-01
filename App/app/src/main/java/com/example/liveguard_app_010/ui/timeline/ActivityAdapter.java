// ActivityAdapter.java
package com.example.liveguard_app_010.ui.timeline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.models.TimelineResponse.Activity;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<Activity> activityList;

    public ActivityAdapter(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity activity = activityList.get(position);
        holder.tvTitle.setText(activity.getTitle());
        holder.tvTimestamp.setText(activity.getTimestamp());

        // 아이템 클릭 이벤트 (선택 사항)
        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), "Clicked: " + activity.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return activityList != null ? activityList.size() : 0;
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTimestamp;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvActivityTitle);
            tvTimestamp = itemView.findViewById(R.id.tvActivityTimestamp);
        }
    }
}

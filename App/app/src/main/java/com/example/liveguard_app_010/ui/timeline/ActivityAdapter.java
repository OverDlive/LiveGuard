package com.example.liveguard_app_010.ui.timeline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.models.Timeline;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.TimelineViewHolder> {

    private List<Timeline> timelineList;

    public ActivityAdapter(List<Timeline> timelineList) {
        this.timelineList = timelineList;
    }

    public void setActivityList(List<Timeline> timelineList) {
        this.timelineList = timelineList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline, parent, false);
        return new TimelineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        Timeline timeline = timelineList.get(position);
        holder.tvContent.setText(timeline.getContent());
        holder.tvTimestamp.setText(timeline.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return (timelineList != null) ? timelineList.size() : 0;
    }

    static class TimelineViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvTimestamp;

        TimelineViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
        }
    }
}
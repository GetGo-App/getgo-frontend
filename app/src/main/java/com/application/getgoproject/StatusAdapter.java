package com.application.getgoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.models.Status;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    private Context context;
    private List<Status> statusList;

    public StatusAdapter(Context context, List<Status> statusList) {
        this.context = context;
        this.statusList = statusList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_status_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Status status = statusList.get(position);

        ImageStatusAdapter adapter = new ImageStatusAdapter(context, status.getUserImage());

        holder.avatar.setImageResource(status.getUserAvatar());
        holder.username.setText(status.getUsername());
        holder.lastTime.setText(status.getLastTime());
        holder.title.setText(status.getTitle());
        holder.content.setText(status.getContent());
        holder.listImage.setAdapter(adapter);

        setGridViewHeightBasedOnChildren(holder.listImage, 2);
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView username;
        TextView lastTime;
        TextView title;
        TextView content;
        GridView listImage;
        ImageButton heartButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            username = itemView.findViewById(R.id.username);
            lastTime = itemView.findViewById(R.id.userLastUploadTime);
            title = itemView.findViewById(R.id.statusTitle);
            content = itemView.findViewById(R.id.statusContent);
            listImage = itemView.findViewById(R.id.gridViewImage);
            heartButton = itemView.findViewById(R.id.heartButton);
        }
    }

    private void setGridViewHeightBasedOnChildren(GridView gridView, int numColumns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > numColumns) {
            x = (float) items / numColumns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }
}

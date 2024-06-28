package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.application.getgoproject.R;
import com.application.getgoproject.models.ImageNewStatus;

import java.util.List;

public class NewStatusImageAdapter extends RecyclerView.Adapter<NewStatusImageAdapter.ViewHolder> {
    private List<ImageNewStatus> imageList;
    private Context context;
    private int layout;

    private NewStatusImageAdapter.OnItemClickListener onItemClickListener;
    private int selectedPosition = -1;
    public NewStatusImageAdapter(Context context, int layout, List<ImageNewStatus> imageList) {
        this.context = context;
        this.layout = layout;
        this.imageList = imageList;
    }

    public void setOnItemClickListener(NewStatusImageAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NewStatusImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_newstatus, parent, false);
        return new NewStatusImageAdapter.ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewStatusImageAdapter.ViewHolder holder, int position) {
        ImageNewStatus item = imageList.get(position);
        holder.imageStatus.setImageResource(item.getImage());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageStatus;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView, final NewStatusImageAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            // Ánh xạ view
            imageStatus = itemView.findViewById(R.id.imageStatus);
//            checkBox = itemView.findViewById(R.id.checkBox);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }
}

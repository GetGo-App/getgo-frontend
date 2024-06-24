package com.application.getgoproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.Comment;
import com.application.getgoproject.models.LocationComment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<LocationComment> commentList;

    public CommentAdapter(Context context, int layout, List<LocationComment> commentList) {
        this.context = context;
        this.layout = layout;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        //ánh xạ view
        TextView name = (TextView) convertView.findViewById(R.id.tvName);
        TextView time = (TextView) convertView.findViewById(R.id.tvTime);
        TextView comment = (TextView) convertView.findViewById(R.id.tvComment);
        ImageView avatar = (ImageView) convertView.findViewById(R.id.imgAvatar);
        RatingBar star = (RatingBar) convertView.findViewById(R.id.ratingBarComment);

        //gan gia tri
        LocationComment comments = commentList.get(position);
        name.setText(comments.getUserId());
        time.setText(getTimeAgo(comments.getCreatedDate()));
        comment.setText(comments.getContent());
        avatar.setImageResource(R.drawable.avatar);
        star.setRating(comments.getRating());

        return convertView;
    }

    public String getTimeAgo(LocalDateTime createdDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdDate, now);

        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return "just now";
        } else if (seconds < 3600) {
            long minutes = TimeUnit.SECONDS.toMinutes(seconds);
            return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        } else if (seconds < 86400) {
            long hours = TimeUnit.SECONDS.toHours(seconds);
            return hours + (hours == 1 ? " hour ago" : " hours ago");
        } else if (seconds < 2592000) {
            long days = TimeUnit.SECONDS.toDays(seconds);
            return days + (days == 1 ? " day ago" : " days ago");
        } else if (seconds < 31104000) {
            long months = TimeUnit.SECONDS.toDays(seconds) / 30;
            return months + (months == 1 ? " month ago" : " months ago");
        } else {
            long years = TimeUnit.SECONDS.toDays(seconds) / 365;
            return years + (years == 1 ? " year ago" : " years ago");
        }
    }
}

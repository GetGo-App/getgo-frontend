package com.application.getgoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.application.getgoproject.models.Comment;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Comment> commentList;

    public CommentAdapter(Context context, int layout, List<Comment> commentList) {
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
        Comment comments = commentList.get(position);
        name.setText(comments.getName());
        time.setText(comments.getTime());
        comment.setText(comments.getComment());
        avatar.setImageResource(comments.getAvatar());
        star.setRating(comments.getStar());

        return convertView;
    }
}

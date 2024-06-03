package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.ListItem;

import java.util.List;

public class PaymentMethodAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ListItem> itemlList;

    public PaymentMethodAdapter(Context context, int layout, List<ListItem> itemlList) {
        this.context = context;
        this.layout = layout;
        this.itemlList = itemlList;
    }

    @Override
    public int getCount() {
        return itemlList.size();
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
        TextView tvTitlePayment = convertView.findViewById(R.id.tvTitlePayment);
        ImageView imgPayment = convertView.findViewById(R.id.imgPayment);
        RadioButton radioButton = convertView.findViewById(R.id.radioButton);

        //gan gia tri
        ListItem item = itemlList.get(position);
        tvTitlePayment.setText(item.getTitle());
        imgPayment.setImageResource(item.getIcon());
        radioButton.setChecked(false);

        return convertView;
    }
}

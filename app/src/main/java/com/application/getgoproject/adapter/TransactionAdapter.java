package com.application.getgoproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.getgoproject.R;
import com.application.getgoproject.models.ListPackage;
import com.application.getgoproject.models.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<Transaction> transactionList;

    public TransactionAdapter(Context context, int layout, List<Transaction> transactionList) {
        this.context = context;
        this.layout = layout;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transaction, parent, false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.tvPackage.setText(transaction.getTitle());
        holder.tvTime.setText(transaction.getTime());
        holder.tvPrice.setText(transaction.getPrice());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong item layout
        TextView tvPackage, tvTime, tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            tvPackage = itemView.findViewById(R.id.tvPackage);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPrice = itemView.findViewById(R.id.tvPrice);

        }
    }
}

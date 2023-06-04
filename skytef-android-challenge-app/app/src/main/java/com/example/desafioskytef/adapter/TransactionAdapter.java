package com.example.desafioskytef.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafioskytef.R;
import com.example.desafioskytef.model.Transaction;
import com.example.desafioskytef.viewmodel.EditTransactionActivity;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder> {

    private final List<Transaction> transactionList;
    private final Context context;

    public TransactionAdapter(List<Transaction> transactionList, Context context) {
        this.transactionList = transactionList;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_transaction_item, parent, false);
        return new TransactionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.establishmentName.setText(transaction.getEstablishmentName());
        holder.cardNumber.setText(transaction.getCardNumber());
        holder.value.setText(String.valueOf(transaction.getValue()));

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditTransactionActivity.class);
            intent.putExtra("id", transaction.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

}

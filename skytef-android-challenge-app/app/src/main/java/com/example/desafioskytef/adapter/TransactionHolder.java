package com.example.desafioskytef.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafioskytef.R;

public class TransactionHolder extends RecyclerView.ViewHolder {

    TextView establishmentName, cardNumber, value;

    public TransactionHolder(@NonNull View itemView) {
        super(itemView);
        establishmentName = itemView.findViewById(R.id.transactionListItem_EstablishmentName);
        cardNumber = itemView.findViewById(R.id.transactioListItem_CardNumber);
        value = itemView.findViewById(R.id.transactioListItem_Value);
    }

}

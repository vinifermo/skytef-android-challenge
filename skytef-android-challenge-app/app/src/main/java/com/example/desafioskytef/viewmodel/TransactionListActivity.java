package com.example.desafioskytef.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafioskytef.R;
import com.example.desafioskytef.adapter.TransactionAdapter;
import com.example.desafioskytef.api.ApiServiceSingleton;
import com.example.desafioskytef.api.TransactionApiService;
import com.example.desafioskytef.model.Transaction;
import com.example.desafioskytef.utils.AppPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransactionApiService transactionApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        recyclerView = findViewById(R.id.transactionList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton floatingActionButton = findViewById(R.id.transactionList_fab);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateTransactionActivity.class);
            startActivity(intent);

        });

        transactionApiService = ApiServiceSingleton.getInstance();
        loadTransactions();
    }

    private void loadTransactions() {
        transactionApiService.getTransactions()
                .enqueue(new Callback<List<Transaction>>() {
                    @Override
                    public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                        List<Transaction> transactions = response.body();
                        populateListView(transactions);
                        Toast.makeText(TransactionListActivity.this, "Listing successful!", Toast.LENGTH_SHORT).show();

                        AppPreferences appPreferences = new AppPreferences(TransactionListActivity.this);
                        String jsonTransactions = convertTransactionsToJson(transactions);
                        appPreferences.saveString("transactions", jsonTransactions);
                    }

                    @Override
                    public void onFailure(Call<List<Transaction>> call, Throwable t) {
                        Toast.makeText(TransactionListActivity.this, "Save failed!!!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(TransactionListActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);

                        AppPreferences appPreferences = new AppPreferences(TransactionListActivity.this);
                        String jsonTransactions = appPreferences.getString("transactions", "");
                        if (!jsonTransactions.isEmpty()) {
                            List<Transaction> cachedTransactions = convertJsonToTransactions(jsonTransactions);
                            populateListView(cachedTransactions);
                        }
                    }
                });
    }

    private void populateListView(List<Transaction> transactionList) {
        TransactionAdapter transactionAdapter = new TransactionAdapter(transactionList, this);
        recyclerView.setAdapter(transactionAdapter);
    }

    private String convertTransactionsToJson(List<Transaction> transactions) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Transaction>>() {
        }.getType();
        return gson.toJson(transactions, listType);
    }

    private List<Transaction> convertJsonToTransactions(String jsonTransactions) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Transaction>>() {
        }.getType();
        return gson.fromJson(jsonTransactions, listType);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTransactions();
    }
}
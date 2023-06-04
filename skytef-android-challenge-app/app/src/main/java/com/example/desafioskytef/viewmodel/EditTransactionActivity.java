package com.example.desafioskytef.viewmodel;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.desafioskytef.R;
import com.example.desafioskytef.api.ApiServiceSingleton;
import com.example.desafioskytef.api.TransactionApiService;
import com.example.desafioskytef.model.Transaction;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTransactionActivity extends AppCompatActivity {
    private Long transactionId;
    private TransactionApiService transactionApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        transactionId = getIntent().getLongExtra("id", 1);
        transactionApiService = ApiServiceSingleton.getInstance();

        initializeComponents();
    }

    private void initializeComponents() {
        TextInputEditText inputEditTextEstablishmentName = findViewById(R.id.editTextEstablishmentName);
        TextInputEditText inputTextCardNumber = findViewById(R.id.editTextCardNumber);
        TextInputEditText inputTextValue = findViewById(R.id.editTextValue);
        MaterialButton buttonUpdateTransaction = findViewById(R.id.buttonUpdateTransaction);
        MaterialButton buttonDeleteTransaction = findViewById(R.id.buttonDeleteTransaction);

        transactionApiService.getTransaction(transactionId).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(@NonNull Call<Transaction> call, @NonNull Response<Transaction> response) {
                if (response.isSuccessful()) {
                    Transaction transaction = response.body();
                    inputEditTextEstablishmentName.setText(transaction.getEstablishmentName());
                    inputTextCardNumber.setText(transaction.getCardNumber());
                    inputTextValue.setText(transaction.getValue().toString());
                } else {
                    Toast.makeText(EditTransactionActivity.this, "Could not load transaction", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Toast.makeText(EditTransactionActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

        buttonUpdateTransaction.setOnClickListener(view -> {
            String establishmentName = String.valueOf(inputEditTextEstablishmentName.getText());
            String cardNumber = String.valueOf(inputTextCardNumber.getText());
            BigDecimal value = new BigDecimal(String.valueOf(inputTextValue.getText()));

            Transaction transaction = new Transaction();
            transaction.setEstablishmentName(establishmentName);
            transaction.setCardNumber(cardNumber);
            transaction.setValue(value);
            System.out.println(transaction);
            transactionApiService.updateTransaction(transactionId, transaction)
                    .enqueue(new Callback<Transaction>() {
                        public void onResponse(@NonNull Call<Transaction> call, @NonNull Response<Transaction> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(EditTransactionActivity.this, "Update successful!", Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.code() == 409) {
                                    Toast.makeText(EditTransactionActivity.this, "Card already exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditTransactionActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Transaction> call, @NonNull Throwable t) {
                            Toast.makeText(EditTransactionActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(EditTransactionActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }
                    });
        });

        buttonDeleteTransaction.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditTransactionActivity.this);
            builder.setTitle("Confirm Deletion");
            builder.setMessage("Are you sure you want to delete this transaction?");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                transactionApiService.deleteTransaction(transactionId)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(EditTransactionActivity.this, "Transaction deleted successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(EditTransactionActivity.this, "Failed to delete transaction", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                Toast.makeText(EditTransactionActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(EditTransactionActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                            }
                        });
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });

    }
}

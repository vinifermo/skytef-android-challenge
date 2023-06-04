package com.example.desafioskytef.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.desafioskytef.R;
import com.example.desafioskytef.api.ApiServiceSingleton;
import com.example.desafioskytef.api.TransactionApiService;
import com.example.desafioskytef.model.Transaction;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTransactionActivity extends AppCompatActivity {

    private TransactionApiService transactionApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transactionApiService = ApiServiceSingleton.getInstance();

        initializeComponents();
    }

    private void initializeComponents() {
        TextInputEditText inputEditTextEstablishmentName = findViewById(R.id.editTextEstablishmentName);
        TextInputEditText inputTextCardNumber = findViewById(R.id.editTextCardNumber);
        TextInputEditText inputTextValue = findViewById(R.id.editTextValue);
        MaterialButton buttonCreateTransaction = findViewById(R.id.buttonCreateTransaction);
        MaterialButton buttonWriteFile = findViewById(R.id.writeButton);

        buttonCreateTransaction.setOnClickListener(view -> {
            Transaction transaction = new Transaction(
                    String.valueOf(inputEditTextEstablishmentName.getText()),
                    String.valueOf(inputTextCardNumber.getText()),
                    new BigDecimal(String.valueOf(inputTextValue.getText()))
            );

            createTransaction(transaction);
        });

        buttonWriteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction transaction = new Transaction(
                        String.valueOf(inputEditTextEstablishmentName.getText()),
                        String.valueOf(inputTextCardNumber.getText()),
                        new BigDecimal(String.valueOf(inputTextValue.getText()))
                );
                writeToFile(transaction);
            }
        });

    }

    private void createTransaction(Transaction transaction) {
        transactionApiService.createTransaction(transaction)
                .enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(@NonNull Call<Transaction> call, @NonNull Response<Transaction> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CreateTransactionActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
                            writeToFile(transaction);
                        } else {
                            Toast.makeText(CreateTransactionActivity.this, "Save failed!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Transaction> call, @NonNull Throwable t) {
                        Toast.makeText(CreateTransactionActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(CreateTransactionActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
    }

    private void writeToFile(Transaction transaction) {
        String filename = "transaction.txt";
        String fileContents = transaction.toString();
        try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
            Toast.makeText(this, "Transaction details written to file successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to write transaction details to file", Toast.LENGTH_SHORT).show();
        }
    }

}


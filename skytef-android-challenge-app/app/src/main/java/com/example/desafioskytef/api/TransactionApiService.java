package com.example.desafioskytef.api;

import com.example.desafioskytef.model.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TransactionApiService {

    @GET("transactions")
    Call<List<Transaction>> getTransactions();

    @POST("transactions")
    Call<Transaction> createTransaction(@Body Transaction transaction);

    @GET("transactions/{id}")
    Call<Transaction> getTransaction(@Path("id") Long id);

    @PUT("transactions/{id}")
    Call<Transaction> updateTransaction(@Path("id") Long transactionId, @Body Transaction transaction);

    @DELETE("transactions/{id}")
    Call<Void> deleteTransaction(@Path("id") Long id);
}


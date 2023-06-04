package com.example.desafioskytef.api;

public class ApiServiceSingleton {

    private static TransactionApiService transactionApiService;

    public static TransactionApiService getInstance() {
        if (transactionApiService == null) {
            RetrofitClient retrofitClient = new RetrofitClient();
            transactionApiService = retrofitClient.getRetrofit().create(TransactionApiService.class);
        }
        return transactionApiService;
    }

}

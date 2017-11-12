package com.horses.mercadopago.helper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author @briansalvattore on 8/11/2017.
 */

class RetrofitApi {

    private static final int TIMEOUT = 120;

    @SuppressWarnings("FieldCanBeLocal")
    private static String URL_BASE = "https://api.mercadopago.com";

    private static Retrofit createAdapter(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    static MercadoService getService() {
        return createAdapter().create(MercadoService.class);
    }
}

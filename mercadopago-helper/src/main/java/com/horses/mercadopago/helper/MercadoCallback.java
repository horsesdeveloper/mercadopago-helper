package com.horses.mercadopago.helper;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author @briansalvattore on 10/11/2017.
 */

abstract class MercadoCallback<T> implements Callback<T> {

    abstract TaskCompletionSource<MercadoObject> getTask();

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        MercadoObject mercadoObject = new MercadoObject();

        if (response.isSuccessful()) {
            if (response.body() == null) {
                onFailure(call, new NullPointerException("Body is null"));
                return;
            }

            mercadoObject.setSuccessful(true);
            mercadoObject.setData((JsonElement) response.body());

            getTask().setResult(mercadoObject);
        }
        else {
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                onFailure(call, new NullPointerException("Body is null"));
                return;
            }

            MercadoError error = new Gson().fromJson(String.valueOf(errorBody), MercadoError.class);

            mercadoObject.setSuccessful(false);
            mercadoObject.setError(error);

            getTask().setResult(mercadoObject);
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        getTask().setException(new Exception(t));
    }
}

package com.horses.mercadopago.helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author @briansalvattore on 8/11/2017.
 */

interface MercadoService {

    @POST("v1/card_tokens/")
    Call<JsonObject> createNewCardToken(
            @Query("public_key") String publicKey,
            @Body RequestBody params);

    @POST("v1/customers")
    Call<JsonObject> createNewUser(
            @Query("access_token") String accessToken,
            @Body RequestBody params);

    @POST("v1/customers/{user}/cards")
    Call<JsonObject> associateCardWithUser(
            @Path("user") String user,
            @Query("access_token") String accessToken,
            @Body RequestBody params);

    @GET("v1/customers/{user}/cards")
    Call<JsonArray> getCardsFromUser(
            @Path("user") String user,
            @Query("access_token") String accessToken);

    @POST("v1/payments")
    Call<JsonObject> createAPayment(
            @Query("access_token") String accessToken,
            @Body RequestBody params);

}

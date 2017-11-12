package com.horses.mercadopago.helper;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author @briansalvattore on 8/11/2017.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class MercadoPago {

    private Builder build;

    private MercadoService mercadoService;

    private MercadoPago() {
        /** not access */
        mercadoService = RetrofitApi.getService();
    }

    public static MercadoPago getInstance() {
        return new MercadoPago();
    }

    public void init(Builder build) {
        this.build = build;
    }

    /**
     * Create new Token Card
     *
     * @param data in Json format, like this
     *             {
     *             "security_code" : "333",
     *             "expiration_year" : 2020,
     *             "expiration_month" : 9,
     *             "card_number": "4009175332806176",
     *             "cardholder": {
     *             "identification": {
     *             "number": "85695236",
     *             "type": "DNI"
     *             },
     *             "name": "APRO"
     *             }
     *             }
     * @return MercadoObject Task
     */
    public Task<MercadoObject> createNewCard(@NonNull final Map<String, Object> data) {
        final TaskCompletionSource<MercadoObject> sendTask = new TaskCompletionSource<>();

        if (build == null || !build.isValid()) {
            sendTask.setException(new NullPointerException("keys is required"));
            return sendTask.getTask();
        }

        if (data.isEmpty()) {
            sendTask.setException(new NullPointerException("data is required"));
            return sendTask.getTask();
        }

        mercadoService.createNewCardToken(build.publicKey, mapToJson(data))
                .enqueue(new MercadoCallback<JsonObject>() {
                    @Override
                    protected TaskCompletionSource<MercadoObject> getTask() {
                        return sendTask;
                    }
                });

        return sendTask.getTask();
    }

    /**
     * Create new User
     *
     * @param data in Json format, like this
     *             {
     *             "email": "jperez1@mivan.com",
     *             "first_name": "Juan",
     *             "last_name": "Perez"
     *             }
     * @return MercadoObject Task
     */
    public Task<MercadoObject> createNewUser(@NonNull final Map<String, Object> data) {
        final TaskCompletionSource<MercadoObject> sendTask = new TaskCompletionSource<>();

        if (build == null || !build.isValid()) {
            sendTask.setException(new NullPointerException("keys is required"));
            return sendTask.getTask();
        }

        if (data.isEmpty()) {
            sendTask.setException(new NullPointerException("data is required"));
            return sendTask.getTask();
        }

        mercadoService.createNewUser(build.accessToken, mapToJson(data))
                .enqueue(new MercadoCallback<JsonObject>() {
                    @Override
                    protected TaskCompletionSource<MercadoObject> getTask() {
                        return sendTask;
                    }
                });

        return sendTask.getTask();
    }

    /**
     * Associate Card With User
     *
     * @param user is a User key
     * @param card is a Card token
     * @return MercadoObject Task
     */
    public Task<MercadoObject> associateCardWithUser(@NonNull final String user, @NonNull final String card) {
        final TaskCompletionSource<MercadoObject> sendTask = new TaskCompletionSource<>();

        if (build == null || !build.isValid()) {
            sendTask.setException(new NullPointerException("keys is required"));
            return sendTask.getTask();
        }

        if (user.isEmpty() || card.isEmpty()) {
            sendTask.setException(new NullPointerException("data is required"));
            return sendTask.getTask();
        }

        mercadoService.associateCardWithUser(user, build.accessToken, mapToJson(new HashMap<String, Object>() {{
            put("token", card);
        }}))
                .enqueue(new MercadoCallback<JsonObject>() {
                    @Override
                    protected TaskCompletionSource<MercadoObject> getTask() {
                        return sendTask;
                    }
                });

        return sendTask.getTask();
    }

    /**
     * Get all cards from User
     *
     * @param user is a User key
     * @return MercadoObject Task
     */
    public Task<MercadoObject> getCardsFromUser(@NonNull final String user) {
        final TaskCompletionSource<MercadoObject> sendTask = new TaskCompletionSource<>();

        if (build == null || !build.isValid()) {
            sendTask.setException(new NullPointerException("keys is required"));
            return sendTask.getTask();
        }

        if (user.isEmpty()) {
            sendTask.setException(new NullPointerException("data is required"));
            return sendTask.getTask();
        }

        mercadoService.getCardsFromUser(user, build.accessToken)
                .enqueue(new MercadoCallback<JsonArray>() {
                    @Override
                    protected TaskCompletionSource<MercadoObject> getTask() {
                        return sendTask;
                    }
                });

        return sendTask.getTask();
    }

    /**
     * Get all cards from User
     *
     * @param code is a security code from CVV or CSV
     * @param id   is a ID from user's card
     * @return MercadoObject Task
     */
    public Task<MercadoObject> getTokenFromCard(@NonNull final String code, @NonNull final String id) {
        final TaskCompletionSource<MercadoObject> sendTask = new TaskCompletionSource<>();

        if (build == null || !build.isValid()) {
            sendTask.setException(new NullPointerException("keys is required"));
            return sendTask.getTask();
        }

        if (code.isEmpty() || id.isEmpty()) {
            sendTask.setException(new NullPointerException("data is required"));
            return sendTask.getTask();
        }

        mercadoService.createNewCardToken(build.publicKey, mapToJson(new HashMap<String, Object>() {{
            put("security_code", code);
            put("cardId", id);
        }}))
                .enqueue(new MercadoCallback<JsonObject>() {
                    @Override
                    protected TaskCompletionSource<MercadoObject> getTask() {
                        return sendTask;
                    }
                });

        return sendTask.getTask();
    }

    /**
     * Create new Token Card
     *
     * @param data in Json format, like this
     *             {
     *             "transaction_amount": 250,
     *             "token": "69bd05d00959e54ad336bf15fce69965", [token from card {@link com.horses.mercadopago.helper.MercadoPago#getTokenFromCard getTokenFromCard}]
     *             "description": "Esto es una prueba",
     *             "installments" : 1,
     *             "payment_method_id": "visa",
     *             "payer": {
     *             "id" : "282643880-m0NuyiMEb4wkL6", [optional if user is save or logget now ]
     *             "email": "jperez1@mivan.com"
     *             }
     *             }
     * @return MercadoObject Task
     */
    public Task<MercadoObject> createAPayment(@NonNull final Map<String, Object> data) {
        final TaskCompletionSource<MercadoObject> sendTask = new TaskCompletionSource<>();

        if (build == null || !build.isValid()) {
            sendTask.setException(new NullPointerException("keys is required"));
            return sendTask.getTask();
        }

        if (data.isEmpty()) {
            sendTask.setException(new NullPointerException("data is required"));
            return sendTask.getTask();
        }

        mercadoService.createAPayment(build.accessToken, mapToJson(data))
                .enqueue(new MercadoCallback<JsonObject>() {
                    @Override
                    protected TaskCompletionSource<MercadoObject> getTask() {
                        return sendTask;
                    }
                });

        return sendTask.getTask();
    }

    private RequestBody mapToJson(Map<String, Object> data) {
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonObject = new JSONObject(data);
        return RequestBody.create(mediaType, jsonObject.toString());
    }

    public static class Builder {

        private String publicKey;
        private String accessToken;

        public Builder() {

        }

        public Builder(String publicKey, String accessToken) {
            this.publicKey = publicKey;
            this.accessToken = accessToken;
        }

        public Builder setPublicKey(@NonNull String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public Builder setAccessToken(@NonNull String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder build() {
            return new Builder(publicKey, accessToken);
        }

        private boolean isValid() {
            return publicKey != null && accessToken != null &&
                    !publicKey.isEmpty() && !accessToken.isEmpty();
        }
    }
}

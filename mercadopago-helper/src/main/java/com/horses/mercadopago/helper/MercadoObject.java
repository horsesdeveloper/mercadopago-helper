package com.horses.mercadopago.helper;

import com.google.gson.JsonElement;

import java.io.Serializable;

/**
 * @author @briansalvattore on 9/11/2017.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class MercadoObject implements Serializable {

    private boolean isSuccessful;
    private String id;
    private JsonElement data;

    private MercadoError error;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public MercadoError getError() {
        return error;
    }

    public void setError(MercadoError error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SuppressWarnings("unchecked")
    public <T extends JsonElement> T toJson() {
        return (T) data;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

}

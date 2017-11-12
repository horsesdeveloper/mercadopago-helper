package com.horses.mercadopago.helper.main;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.horses.mercadopago.helper.MercadoObject;
import com.horses.mercadopago.helper.MercadoPago;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final String public_key = "TEST-98041829-8c47-4c6a-9c23-7b6e1855f31d";
    final String access_token = "TEST-3029117202042245-103104-2fd0688859e43720378e5ed1043114f4__LC_LB__-182447115";

    private MercadoPago mercadoPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mercadoPago = MercadoPago.getInstance();
        mercadoPago.init(new MercadoPago.Builder()
                .setPublicKey(public_key)
                .setAccessToken(access_token)
                .build());

        createNewCard();
        //createNewUser();

        //associateCardWithUser();

        //mercadoPago.getCardsFromUser("282643880-m0NuyiMEb4wkL6");
        //mercadoPago.getTokenFromCard("333", "1510188321113");

    }

    private void associateCardWithUser() {
        final String TAG = "associateCardWithUser";

        String user = "282643880-m0NuyiMEb4wkL6";
        String card = "5194d49e648ff89ab0231f99278fad86";

        Task<MercadoObject> mTask =  mercadoPago.associateCardWithUser(user, card);
        mTask.addOnSuccessListener(new OnSuccessListener<MercadoObject>() {
            @Override
            public void onSuccess(MercadoObject mercadoObject) {
                Log.d(TAG, "onSuccess() called with: mercadoObject = [" + mercadoObject.toJson() + "]");
            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.wtf(TAG, "onFailure: ", e);
            }
        });

    }

    private void createNewCard() {
        final String TAG = "createNewCard";

        Map<String, Object> map = new HashMap<>();
        map.put("security_code", "250");
        map.put("expiration_year", 2020);
        map.put("expiration_month", 8);
        map.put("card_number", "4009175332806176");
        map.put("cardholder", new HashMap<String, Object>(){{
            put("identification", new HashMap<String, Object>(){{
                put("number", "52658963");
                put("type", "DNI");
            }});
            put("name", "APRO");
        }});

        Task<MercadoObject> mTask =  mercadoPago.createNewCard(map);
        mTask.addOnSuccessListener(new OnSuccessListener<MercadoObject>() {
            @Override
            public void onSuccess(MercadoObject mercadoObject) {
                Log.d(TAG, "onSuccess() called with: mercadoObject = [" + mercadoObject.toJson() + "]");
            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.wtf(TAG, "onFailure: ", e);
            }
        });
    }

    private void createNewUser() {
        final String TAG = "createNewUser";

        Map<String, Object> map = new HashMap<>();
        map.put("email", "jperez3@mivan.com");
        map.put("first_name", "Juan");
        map.put("last_name", "Perez");

        Task<MercadoObject> mTask =  mercadoPago.createNewUser(map);
        mTask.addOnSuccessListener(new OnSuccessListener<MercadoObject>() {
            @Override
            public void onSuccess(MercadoObject mercadoObject) {
                if (mercadoObject.isSuccessful()) {
                    Log.d(TAG, "onSuccess() called with: mercadoObject = [" + mercadoObject.toJson() + "]");
                }
                else {

                    Log.wtf(TAG, "onSuccess() called with: error = [" + mercadoObject.getError().toString() + "]");
                }

            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.wtf(TAG, "onFailure: ", e);
            }
        });
    }
}

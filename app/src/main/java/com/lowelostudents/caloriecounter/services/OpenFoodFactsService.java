package com.lowelostudents.caloriecounter.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.lowelostudents.caloriecounter.models.entities.Food;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// TODO rework ERRORHANDLING ETC PP trycatch helper class default value
public class OpenFoodFactsService {
    private static OpenFoodFactsService openFoodFactsService;
    private final NutrientService nutrientService = NutrientService.getInstance();
    public RequestQueue requestQueue;
    private String url = "https://world.openfoodfacts.org/api/v2";

    private OpenFoodFactsService(Context context) {
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized OpenFoodFactsService getInstance(Context context) {
        if (openFoodFactsService == null) openFoodFactsService = new OpenFoodFactsService(context);

        return openFoodFactsService;
    }

    public void getProduct(Barcode barcode, ResponseCallback responseCallback) {

        Log.w("APISERVICEBARCODE", barcode.getRawValue());

        String endpoint = this.url + "/product/" + barcode.getRawValue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, endpoint, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Food food;
                        try {
                            food = mapToFood(response);
                            Log.w("ONRESPONSE", "ONRESPONSE");
                            responseCallback.onResult(food);
                        } catch (JSONException e) {
                            Log.w("ONERROR", "ONERROR");
                            e.printStackTrace();
                            responseCallback.onError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("ERRORAPICALL", error.toString());
                        responseCallback.onError(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("User-Agent", "CalorieCounter - Android - Version 1.0");

                return params;
            }
        };

//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
//                5000, 3,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        this.requestQueue.add(jsonObjectRequest);
    }

    private Food mapToFood(JSONObject openFoodFacts) throws JSONException {
        JSONObject product = openFoodFacts.getJSONObject("product");
        Gson gson = new Gson();
        Food food = gson.fromJson(String.valueOf(product.getJSONObject("nutriments")), Food.class);

        try {
            food.setPortionSize(product.getDouble("serving_quantity"));
        } catch (JSONException e) {
            Log.e("SERVING QUANTITY NOT FOUND", Arrays.toString(e.getStackTrace()));
            mapToFoodDefaultSize(food, product);
        }

        try {
            food.setGramTotal(product.getDouble("product_quantity"));
        } catch (JSONException e) {
            food.setGramTotal(food.getPortionSize());
        }

        try {
            food.setName(product.getString("product_name"));
        } catch (JSONException e) {
            food.setName("");
        }

        nutrientService.calculateNutrients(food);

        return food;
    }

    private void mapToFoodDefaultSize(Food food, JSONObject product) {
        food.setPortionSize(100);

        try {
            food.setCarbsGram(product.getJSONObject("nutriments").getDouble("carbohydrates_100g"));

        } catch (JSONException ignored) { }

        try {
            food.setFatGram(product.getJSONObject("nutriments").getDouble("fat_100g"));
        } catch (JSONException ignored) { }

        try {
            food.setProteinGram(product.getJSONObject("nutriments").getDouble("protein_100g"));
        } catch (JSONException ignored) { }

    }
}

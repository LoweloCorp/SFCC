package com.lowelostudents.caloriecounter.services;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lowelostudents.caloriecounter.data.repositories.FoodRepo;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OpenFoodFactsService {
    private static OpenFoodFactsService openFoodFactsService;
    private NutrientService nutrientService = NutrientService.getInstance();

    public static synchronized OpenFoodFactsService getInstance() {
        if (openFoodFactsService == null) openFoodFactsService = new OpenFoodFactsService();

        return openFoodFactsService;
    }

    public void getProduct(FoodRepo repository, int barcode) {
        RequestQueue queue = Volley.newRequestQueue(repository.getContext());
        String url = "https://world.openfoodfacts.org/api/v2/product/" + barcode;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        serialize(response);
                        Log.d("Response: ", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("Error", error.toString());
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

        queue.add(jsonObjectRequest);
    }

    public void serialize(JSONObject food) {
        try {
            Gson gson = new Gson();
            Food foods = gson.fromJson(String.valueOf(food.getJSONObject("product").getJSONObject("nutriments")), Food.class);

            nutrientService.calculateNutrients(foods);

            Log.d("DEINEMUDDER", foods.toString());
        } catch (JSONException e) {
            Log.e("Error getting JSON Object 'nutriments' Open Food Facts response", e.toString());
        }
    }
}

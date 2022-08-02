package com.lowelostudents.caloriecounter.services;

import android.content.res.Resources;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OpenFoodFactsService {
    private static OpenFoodFactsService openFoodFactsService;
    private final NutrientService nutrientService = NutrientService.getInstance();

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

    public void serialize(JSONObject openFoodFacts) {
        try {
            JSONObject product = openFoodFacts.getJSONObject("product");
            Food food = this.mapToFood(product);

            nutrientService.calculateNutrients(food);

            Log.w("FOOD VON API", food.toString());
        } catch (Exception e) {
            Log.e("Error getting JSON Object 'nutriments' Open Food Facts response", e.toString());
        }
    }

    private Food mapToFood(JSONObject product) throws Exception {
        Gson gson = new Gson();

        Food food = gson.fromJson(String.valueOf(product.getJSONObject("nutriments")), Food.class);
        food.setName(this.getName(product));
        food.setPortionSize(product.getDouble("serving_quantity"));
        food.setGramTotal(product.getDouble("product_quantity"));

        return food;
    }

    private String getName(JSONObject openFoodFacts) throws Exception {
        final String[] names = {
                openFoodFacts.getString("product_name"),
                openFoodFacts.getString("product_name_en"),
                openFoodFacts.getString("product_name_de"),
                openFoodFacts.getString("product_name_fr")
        };

        String result = null;

        for (String name : names) {
            if (!name.isEmpty()) {
                result = name;
                break;
            }
        }

        return result;
    }
}

package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        String mainname = "";
        String placeoforigin = "";
        String description = "";
        String image = "";
        List<String> alsoknown = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject sandwiches = new JSONObject(json);

            JSONObject name = sandwiches.getJSONObject("name");
            mainname = name.getString("mainName");


            if (!sandwiches.getString("placeOfOrigin").equals("")) {
                placeoforigin = sandwiches.getString("placeOfOrigin");
            } else {
                placeoforigin = "(Data not available)";
            }

            description = sandwiches.getString("description");
            image = sandwiches.getString("image");
            JSONArray alsoknownas = name.getJSONArray("alsoKnownAs");
            for (int j = 0; j < alsoknownas.length(); j++) {
                alsoknown.add(alsoknownas.getString(j));
            }


            JSONArray ingredient = sandwiches.getJSONArray("ingredients");
            for (int k = 0; k < ingredient.length(); k++) {
                ingredients.add(ingredient.getString(k));
            }


            return new Sandwich(mainname, alsoknown, placeoforigin, description, image, ingredients);


        } catch (JSONException e) {
            Log.e("Json Utils", "problem parsing json data", e);
        }
        return null;
    }
}

package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich = new Sandwich();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        } else {

            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());

            TextView descriptionText = (TextView) findViewById(R.id.description_tv);
            descriptionText.setText(sandwich.getDescription());

            TextView alsoKnownAsText = (TextView) findViewById(R.id.also_known_tv);
            List<String> other_names = sandwich.getAlsoKnownAs();
            String merge = makeList(other_names);
            alsoKnownAsText.setText(edgeCase(merge));

            //Populating ingredients text with JSON
            TextView ingredientsText = (TextView) findViewById(R.id.ingredients_tv);
            List<String> other_names1 = sandwich.getIngredients();
            String merge1 = makeList(other_names1);
            ingredientsText.setText(edgeCase(merge1));


            //Populating place of origin text with JSON
            TextView placeOfOriginText = (TextView) findViewById(R.id.origin_tv);
            placeOfOriginText.setText(sandwich.getPlaceOfOrigin());
        }


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private String makeList(List<String> stuff) {
        String merge = "";
        if (stuff.size() > 0) {
            for (String s : stuff) {
                merge += "- " + s + "\n";
            }
        }
        return merge;
    }

    private String edgeCase(String s) {
        s = s.equals("") ? getString(R.string.missing_detail) : s;
        return s;
    }
}

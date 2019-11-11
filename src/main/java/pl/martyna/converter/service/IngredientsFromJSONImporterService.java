package pl.martyna.converter.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.martyna.converter.builder.Ingredient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class IngredientsFromJSONImporterService {

    static int counter = 0;

    public Set<Ingredient> importIngredientsFromJSON(String fileName){

        JSONParser jsonParser = new JSONParser();
        Set<Ingredient> ingredients = new HashSet<>();

        try(FileReader reader = new FileReader(fileName)){

            Object obj = jsonParser.parse(reader);

            JSONArray ingredientsList = (JSONArray) obj;

            for(Object ingredientObj: ingredientsList){
                JSONObject ingredient = (JSONObject) ingredientObj;

                Ingredient.builder()
                        .name((String) ingredient.get("product_name"))
                        .ingredientTypes((String) ingredient.get("categories"))
                        .quantity((String) ingredient.get("quantity"))
                        .unit((String) ingredient.get("quantity"))
                        .brands((String) ingredient.get("brands"))
                        .allergens((String) ingredient.get("allergens") +","
                                + (String) ingredient.get("allergens_tags") + ","
                                + (String) ingredient.get("traces") + ","
                                + (String) ingredient.get("traces_tags"))
                        .labels((String) ingredient.get("labels_tags"))
                        .nutrition(ingredient)
                        .build()
                        .ifPresent(ingredients::add);

                counter++;
            }

            System.out.println(String.format("Nuber of read ingredients: %d", counter));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            e.printStackTrace();
        }
        return ingredients;
    }

}

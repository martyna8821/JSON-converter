package pl.martyna.converter.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pl.martyna.converter.Nutrition;
import pl.martyna.converter.builder.Ingredient;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class IngredientsToJSONExportService {

    public boolean writeIngredientsToJSONFile(Set<Ingredient> ingredients, String fileName){

        JSONArray jsonIngredients = new JSONArray();

        ingredients.forEach(
                ingredient -> {
                    JSONObject jsonIngredient = new JSONObject();
                    jsonIngredient.put("name", ingredient.getName());
                    jsonIngredient.put("quantity", ingredient.getQuantity());
                    jsonIngredient.put("unit", ingredient.getUnit());

                    JSONArray jsonTypes = new JSONArray();
                    ingredient.getIngredientTypes().forEach(jsonTypes::add);
                    jsonIngredient.put("ingredientTypes", jsonTypes);

                    JSONArray jsonLabels = new JSONArray();
                    ingredient.getLabels().forEach(jsonLabels::add);
                    jsonIngredient.put("labels", jsonLabels);

                    JSONArray jsonBrands = new JSONArray();
                    ingredient.getBrands().forEach(jsonBrands::add);
                    jsonIngredient.put("brands", jsonBrands);

                    JSONArray jsonAllergens = new JSONArray();
                    ingredient.getAllergens().forEach(jsonAllergens::add);
                    jsonIngredient.put("allergens", jsonAllergens);

                    JSONArray jsonNutrition = new JSONArray();
                    for (Map.Entry<String, Nutrition> entry : ingredient.getNutrition().entrySet()) {
                        JSONObject nutritionObj = new JSONObject();
                        nutritionObj.put("englishName", entry.getKey());
                        nutritionObj.put("polishName", entry.getValue().getPolishName());
                        nutritionObj.put("value", entry.getValue().getValue());
                        nutritionObj.put("unit", entry.getValue().getUnit());
                        jsonNutrition.add(nutritionObj);
                    }
                    jsonIngredient.put("nutrition", jsonNutrition);
                    jsonIngredients.add(jsonIngredient);
                    System.out.println(String.format("Added %s", ingredient.getName()));
                });


        try(FileWriter file = new FileWriter(fileName)){
            file.write(jsonIngredients.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}

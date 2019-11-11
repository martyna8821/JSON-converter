package pl.martyna.converter.builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;
import pl.martyna.converter.Nutrition;
import pl.martyna.converter.translator.AllergenTranslator;
import pl.martyna.converter.translator.LabelTranslator;
import pl.martyna.converter.translator.NutritionTranslator;
import pl.martyna.converter.translator.Translator;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    public static String[] NUTRIENTS_EN = {"fat", "energy", "saturated-fat", "sugars",
                                            "sodium", "salt", "proteins"};
    private String name;
    private int quantity;
    private String unit;
    private Set<String> brands;
    private Set<String> ingredientTypes;
    private Set<String> allergens;
    private Set<String> labels;
    private Map<String, Nutrition> nutrition;

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String name;
        private int quantity = 1;
        private String unit;
        private Set<String> brands = new HashSet<>();
        private Set<String> ingredientTypes = new HashSet<>();
        private Set<String> allergens = new HashSet<>();
        private Set<String> labels = new HashSet<>();
        private Map<String, Nutrition> nutrition = new HashMap<>();

        private Translator allergenTranslator = new AllergenTranslator();
        private Translator labelTranslator = new LabelTranslator();
        private Translator nutritionTranslator = new NutritionTranslator();

        public Builder name(String name){

            this.name = name;
            System.out.println("NAME: " + name);
            return this;
        }

        public Builder quantity(String quantityWithUnit){

            quantityWithUnit = quantityWithUnit.replaceAll("\\s+", "");
            String[] splittedQuantity = quantityWithUnit.split("[^-?0-9]+");
             long quantity = 1;
            try{
                quantity = Long.parseLong(splittedQuantity[0]);
            }catch (NumberFormatException | ArrayIndexOutOfBoundsException | ClassCastException ex){

                if(ex instanceof ClassCastException){
                    System.out.println("Error value: " + splittedQuantity[0]);
                }
                System.out.println("Reading quantity exception. Setting quantity to 1");
                quantity = 1;
            }

            this.quantity *= quantity;
            return this;
        }

        public Builder unit(String unitWithQuantity){
            unitWithQuantity = unitWithQuantity.replaceAll("\\s+", "");
            String[] splittedQuantity = unitWithQuantity.split("[^-?0-9]+");

            if(splittedQuantity.length > 1)
                   this.unit =
                           switch(splittedQuantity[1].toLowerCase()){
                               case "mg": {this.quantity *= 0.001; yield "g";}
                               case "l": yield "l";
                               case "ml": {this.quantity *= 0.001; yield "l";}
                               case "kg": {this.quantity *= 1000; yield "g";}
                               case "x": {setMultipliedQuantity(unitWithQuantity); yield "g";}
                               case "g":
                               default:
                                   yield "g";
                   };
            else
                this.unit = "g";

            return this;
        }

        public Builder brands(String brandsString){
            this.brands.addAll(Arrays.asList(
                    brandsString.replaceAll("\\s+", "")
                            .split(",")));

            return this;
        }

        public Builder ingredientTypes(String ingredientTypesString){

            this.ingredientTypes.addAll(Arrays.stream(ingredientTypesString
                                    .replaceAll("\\s+", "")
                                    .split(","))
                                        .filter(type -> type.startsWith("pl:"))
                                        .map(type1 ->  type1.substring(3))
                                        .collect(Collectors.toSet())
                                );
            return this;
        }

        public Builder allergens(String allergensString){
            this.allergens.addAll(Arrays.stream( allergensString
                                .replaceAll("\\s+", "")
                                .split(","))
                                  .filter(allergen -> (allergen.startsWith("pl:") || allergen.startsWith("en:")))
                                  .map(allergen -> allergenTranslator.translate(allergen))
                                  .filter(allergen -> !allergen.isBlank())
                                  .collect(Collectors.toSet()));
            return this;
        }

        public Builder labels(String labelsString){
            this.labels.addAll(Arrays.stream( labelsString
                    .replaceAll("\\s+", "")
                    .split(","))
                    .filter(label -> (label.startsWith("pl:") || label.startsWith("en:")))
                    .map(label -> labelTranslator.translate(label))
                    .filter(label -> !label.isBlank())
                    .collect(Collectors.toSet()));
            return this;
        }

        public Builder nutrition(JSONObject jsonIngredient){

            for(String nutritionStr: Ingredient.NUTRIENTS_EN){

                Nutrition nutrition = new Nutrition();
                nutrition.setPolishName(this.nutritionTranslator.translate(nutritionStr));

                double nutritionValue;
                String nutritionName = nutritionStr+"_100g";

                try{

                    nutritionValue =  (Double) jsonIngredient.get(nutritionName);

                }catch(ClassCastException valueIsLong){

                    try{
                        nutritionValue = (Long) jsonIngredient.get(nutritionName);
                    }catch (ClassCastException valueIsString){
                        nutritionValue = 0;
                    }
                }


                nutrition.setValue(nutritionValue);
                if(nutritionStr.equals("energy")){
                    nutrition.setUnit("kJ");
                    double caloriesValue =  (nutritionValue * 0.238845);
                    Nutrition calories = new Nutrition("Kalorie", caloriesValue, "kcal" );
                    this.nutrition.put("calories", calories);
                }
                else {
                    nutrition.setUnit("g");
                }

                this.nutrition.put(nutritionStr, nutrition);
            }
            return this;
        }

        public Optional<Ingredient> build() {
            if(name.isBlank())
                return Optional.empty();

            Ingredient ingredient = new Ingredient();
            ingredient.name = this.name;
            ingredient.quantity = this.quantity;
            ingredient.unit = this.unit;
            ingredient.labels = this.labels;
            ingredient.brands = this.brands;
            ingredient.ingredientTypes = this.ingredientTypes;
            ingredient.allergens = this.allergens;
            ingredient.nutrition = this.nutrition;

            return Optional.of(ingredient);
        }


        private void setMultipliedQuantity(String unitWithQuantity){

            String[] splittedQuantity = unitWithQuantity.replaceAll("\\s+", "").split("[^-?0-9]+");
            try{
                this.quantity *=  Long.parseLong(splittedQuantity[2]);

            }catch(ClassCastException  ex){
                if(ex instanceof ClassCastException){
                    System.out.println("Error value"+ splittedQuantity[2]);
                }
            }

        }
    }

}

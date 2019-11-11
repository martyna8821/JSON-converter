package pl.martyna.converter.translator;

public class NutritionTranslator implements Translator {

    @Override
    public String translate(String item) {
        return translateEnglishNutrientName(item);
    }

    private String translateEnglishNutrientName(String englishName){
        return switch (englishName.toLowerCase()){

            case "fat":
               yield "Tłuszcze";

            case "energy":
               yield "Energia";

            case "saturated-fat":
               yield "Węglowodany";

            case "sugars":
               yield "Cukry";

            case "sodium":
               yield "Sód";

            case "salt":
               yield "Sól";

            case "proteins":
               yield "Białko";

            default: yield  "";
        };
    }



}

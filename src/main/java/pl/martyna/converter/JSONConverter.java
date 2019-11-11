package pl.martyna.converter;

import pl.martyna.converter.builder.Ingredient;
import pl.martyna.converter.service.IngredientsFromJSONImporterService;
import pl.martyna.converter.service.IngredientsToJSONExportService;

import java.util.Set;

public class JSONConverter {


    public static void main(String[] args){

        if(args.length < 2){
            System.out.println("Provide files names");
            return;
        }
        String importFile = args[0];
        String exportFile = args[1];
        IngredientsFromJSONImporterService importerService = new IngredientsFromJSONImporterService();

        Set<Ingredient> ingredients = importerService.importIngredientsFromJSON(importFile);

        IngredientsToJSONExportService exportService = new IngredientsToJSONExportService();
        if(exportService.writeIngredientsToJSONFile(ingredients, exportFile)){
            System.out.println("File successfully saved.");
        }

    }


}

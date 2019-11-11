package pl.martyna.converter.translator;

public class LabelTranslator implements Translator {

    @Override
    public String translate(String item) {

        if(item.startsWith("pl:")){
            return translatePolishLabel(item);
        }
        else if(item.startsWith("en:")){
            return translateEnglishLabel(item);
        }
       else{
            throw new IllegalArgumentException("Unknown language");
        }
    }


    private  String translatePolishLabel(String polishName){
        return switch(polishName.toLowerCase()){
            case "bezglutenowy"        :
            case "bezglutenomy"        :
            case "glutenfree"          :
            case "produkt-bezglutenowy":
                yield "bezglutenowy";

            case "bez-konserwantow"                      :
            case "bez-dodatku-substancji-konserwujących" :
            case "bez-substancji-konserwujących"         :
                yield "bez-konserwantow";

            case "bez-aromatów"                        :
            case "bez-sztucznych-aromatów-i-barwników" :
            case "bez-barwnikow-i-aromatow"            :
            case "bez-barwników-i-aromatów"            :
                yield "bez barwników i aromatów";

            case "wegański":
                yield "wegański";

            case "bez-dodatku-cukru" :
                yield "bez-dodatku-cukru";

            case "for-vegeterians":
                yield "wegetariański";

            default:
                yield "";
        };
    }


    private  String translateEnglishLabel(String englishName){
        return switch(englishName.toLowerCase()){
            case "gluten-free":
                yield "bezglutenowy";

            case "no preservatives"           :
            case "no artificial preservatives":
            case "preservative-free"          :
            case "no-preservatives-added"     :
                yield "bez-konserwantow";

            case "without dyes or preservatives":
                yield "bez barwników i aromatów";

            case "vegan"         :
            case "100% vegetable":
                yield "wegański";

            case "no added sugar" :
            case "No sugar"       :
            case "zero-sugar"     :
                yield "bez-dodatku-cukru";

            case "vegetarian":
            case "european vegetarian union":
                yield "wegetariański";

            case "contains gluten":
                yield "zawiera gluten";

            default:
                yield "";
        };
    }
}

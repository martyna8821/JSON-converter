package pl.martyna.converter.translator;

public class AllergenTranslator implements Translator{

    @Override
    public String translate(String allergen){

        String translatedValue = "";
        if(allergen.startsWith("pl:")){
            translatedValue = translatePolishAllergen(allergen.substring(3));
        }
        else if(allergen.startsWith("en:")) {
            translatedValue = translateEnglishAllergen(allergen.substring(3));
        }
        else{
            throw new IllegalArgumentException("Unknown language");
        }

        return translatedValue;
    }

    private  String translateEnglishAllergen(String englishName){
        return switch(englishName.toLowerCase()){
            case "milk": yield "mleko";
            case "gluten": yield "gluten";
            case "eggs": yield "jajka";
            case "celery": yield "seler";
            case "soybeans": yield "soja";
            case "nuts": yield "orzechy";
            case "peanuts": yield "orzeszki ziemne";
            case "fish": yield "ryby";
            case "mustard": yield "musztarda";
            case "sesame-seeds": yield "ziarenka sezamu";
            case "crustaceans": yield "skorupiaki";
            case "molluscs": yield "mięczaki";
            case "lupin": yield "łupin";
            case "coconut": yield "kokos";
            case "oats":
            case "oat":
                yield "płatki";
            case "lemon": yield "cytryna";
            case "avoine": yield "owies";
            case "corn": yield "kukurydza";

            default:
                yield "";
        };
    }

    private  String translatePolishAllergen(String polishName){

        return switch(polishName.toLowerCase()){
            case "mleka"   :
            case "mleczny" :
            case "mleczna" :
                yield "mleko";

            case "soi"     :
            case "sojowe"  :
            case "sojowa"  :
            case "sojowego":
            case "soję"    :
            case "sojowy"  :
            case "soją"    :
                yield "soja";

            case "pszenicy"   :
            case "pszenicą"   :
            case "pszenicę"   :
            case "pszeniczny" :
            case "pszennego"  :
                yield "pszenica";

            case "śmietanka" :
            case "śmietana"  :
            case "śmietanki" :
                 yield "śmietana";

            case "selera"  :
            case "selerem" :
                yield "seler";

            case "serwatka"   :
            case "serwatkowy" :
                yield "serwatka";

            case "ser"    :
            case "serek"  :
            case "sery"   :
            case "edamski":
                yield "ser";

            case "sezam" :
            case "sezamowa" :
                yield "sezam";


            case "orzeszki-arachidowe"   :
            case "orzechy-arachidowe"    :
            case "orzeszków-arachidowych":
            case "orzechów-arachidowych" :
            case "arachidowy"            :
                yield "orzechy archaidowe";

            case "orzechów-laskowych"    :
            case "orzecha-laskowego"     :
            case "orzeszki-laskowe"      :
            case "orzech-laskowy"        :
            case "laskowe"               :
                yield "orzechy laskowe";

            case "orzechy-ziemne"        :
            case "orzeszków-ziemnych"    :
            case "orzechów-ziemnych"     :
                yield "orzeszki ziemne";

            case "orzechów"              :
            case "orzechowa"             :
            case "orzech-włoski"         :
                yield "orzechy";

            case "jaj"   :
            case "jajowa":
                yield "jajka";


            case "jęczmiennego" :
            case "jęczmienna"   :
            case "jęczmienia"   :
            case "jęczmienne"   :
            case "jęczmleń"     :
                yield "jęczmień";

            case "makrela" :
            case "makreli" :
                yield "makrela";


            case "gorczycę" :
            case "gorczyce" :
            case "gorczycy" :
                yield "gorczyca";

            case "glutenem" :
                yield "gluten";

            case "owsiana" :
            case "owsiany" :
            case "owsa"    :
                yield "owies";

            case "migdał"   :
            case "migdałów" :
            case "migdałowe":
                yield "migdały";

            case "laktoza" :
            case "laktozę" :
                yield "laktoza";

            case "żyto"   :
            case "żyta"   :
            case "żytnie" :
            case "żytni"  :
                yield "żyto";

            case "śledzia"  :
            case "śledź"    :
            case "śledziowe":
                yield "śledź";

            case "pirosiarczyn-sodu" :
            case "pirosiarczyn"      :
            case "pirosarczyn"       :
                  yield "pirosiarczyn-sodu";

             case "tuńczyk" :
             case "tunczyk" :
             case "tuiczyk" :
                 yield "tuńczyk";

            default: yield polishName;
        };
    }
}

package pl.martyna.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Nutrition {

    private String polishName;
    private double value;
    private String unit;
}

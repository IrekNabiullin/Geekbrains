//This class is written by baeldung and taken from the site https://www.baeldung.com/java-convert-roman-arabic
// Little modified


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ArabicRomanConverter {

    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }


    public static String arabicToRoman(int number) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        if ((number <= -10) || (number > 100000)) {
            throw new IllegalArgumentException(number + " is not in range (-10,4000]");
        }

        if ((number > -10) && (number <= 0)) {       //to output negative result
            number = -1 * number;

            sb.append("- ");
            while ((number >= -10) && (i < romanNumerals.size())) {
                RomanNumeral currentSymbol = romanNumerals.get(i);
                if (currentSymbol.getValue() <= number) {
                    sb.append(currentSymbol.name());
                    number -= currentSymbol.getValue();
                } else {
                    i++;
                }
            }
            System.out.println(sb.toString());


        } else if ((number > 0) && (number <= 100000)) {

            while ((number >= -10) && (i < romanNumerals.size())) {
                RomanNumeral currentSymbol = romanNumerals.get(i);
                if (currentSymbol.getValue() <= number) {
                    sb.append(currentSymbol.name());
                    number -= currentSymbol.getValue();
                } else {
                    i++;
                }
            }
            System.out.println(sb.toString());
        }
        return sb.toString();
    }
}

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RomanArabicConverter {
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

    /**  not need here from Roman to Arabic convertation

     public static int romanToArabic(String input) {
     String romanNumeral = input.toUpperCase();
     int result = 0;

     List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

     int i = 0;

     while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
     RomanNumeral symbol = romanNumerals.get(i);
     if (romanNumeral.startsWith(symbol.name())) {
     result += symbol.getValue();
     romanNumeral = romanNumeral.substring(symbol.name().length());
     } else {
     i++;
     }
     }

     if (romanNumeral.length() > 0) {
     throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
     }

     return result;
     }
     */

    public static String arabicToRoman(int number) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        String output;
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        if ((number <= -10) || (number > 100)) {
            System.out.println("number = " + number);
            throw new IllegalArgumentException(number + " is not in range (-10,100]");
        }

        if ((number > -10) && (number <= 0)) {       //to output nagative result
            System.out.println("number = " + number);
            number = -1 * number;
            System.out.println("number = " + number);

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
//            output = "-" + sb.toString();
//            System.out.println("output = " + output);

        } else if ((number > 0) && (number <= 100)) {

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
 //           output = sb.toString();

        }
        return sb.toString();
    }
}

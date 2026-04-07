package util;

import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    public static boolean esteEmailValid(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean esteParolaValida(String parola) {
        if (parola == null || parola.length() < 6) {
            return false;
        }

        boolean areLiteraMare = Pattern.compile("[A-Z]").matcher(parola).find();
        boolean areCifra = Pattern.compile("[0-9]").matcher(parola).find();

        return areLiteraMare && areCifra;
    }
}

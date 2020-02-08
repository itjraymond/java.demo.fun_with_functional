package ca.jent.functional;

import java.util.function.Function;
import java.util.regex.Pattern;

public class Validator {
    static Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    static Function<String,Result<String>> emailValidator = email -> {
        if (email == null) {
            return Result.failure("Invalid email: email cannot be null");
        } else if (email.trim().length() == 0) {
            return Result.failure("Invalid email: email cannot be an empty string");
        } else if (emailPattern.matcher(email).matches()) {
            return Result.success(email);
        } else {
            return Result.failure("Invalid email: " + email + " is not properly formatted");
        }
    };

    public static SideEffect<String> success = s -> System.out.println("Mail sent to " + s);
    public static SideEffect<String> failure = s -> System.out.println("LOG: Error " + s);

    public static void main(String[] args) {
        emailValidator.apply(null).bind(success, failure);
        emailValidator.apply("   ").bind(success, failure);
        emailValidator.apply("johndoe@com").bind(success, failure);
        emailValidator.apply("johndoe@nowhere.com").bind(success, failure);

        // LOG: Error Invalid email: email cannot be null
        // LOG: Error Invalid email: email cannot be an empty string
        // LOG: Error Invalid email: johndoe@com is not properly formatted
        // Mail sent to johndoe@nowhere.com

    }
}

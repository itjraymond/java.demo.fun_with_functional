package ca.jent.matchcase;

import ca.jent.functional.Result;
import ca.jent.functional.SideEffect;

import java.util.function.Function;
import java.util.regex.Pattern;
import static ca.jent.matchcase.Case.match;
import static ca.jent.matchcase.Case.matchCase;
import static ca.jent.functional.Result.failure;
import static ca.jent.functional.Result.success;

public class Validator {

    static Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    static SideEffect<String> success = s -> System.out.println("Email sent to " + s);
    static SideEffect<String> failure = s -> System.out.println("LOG: Error " + s);

    static Function<String, Result<String>> emailValidator = email -> match(
            matchCase( () -> success(email) ),
            matchCase( () -> email == null, () -> failure("Invalid email: cannot be null") ),
            matchCase( () -> email.trim().length() == 0, () -> failure("Invalid email: cannot be an empty string") ),
            matchCase( () -> !emailPattern.matcher(email).matches(), () -> failure("Invalid email: not properly formatted"))
    );

    public static void main(String[] args) {
        emailValidator.apply(null).bind(success, failure);
        emailValidator.apply("  ").bind(success, failure);
        emailValidator.apply("johndoe@nowhere").bind(success, failure);
        emailValidator.apply("johndoe@nowhere.com").bind(success, failure);

    }
}

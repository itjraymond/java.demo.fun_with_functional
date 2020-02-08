package ca.jent.functional;

/**
 * Disclaimer: Since I am currently ready "Functional Programming in Java" by Pierre-Yves Saumont, several idea/code
 * are taken from the book. The purpose is solely for learning and trying out the concept.
 * The interface Result<T> is the same as the one found in the book.
 *
 * @param <T>
 */
public interface Result<T> {

    void bind(SideEffect<T> success, SideEffect<String> failure);

    public static <T> Result<T> success(T value) {
        return new Success(value);
    }

    public static <T> Result<T> failure(String msg) {
        return new Failure(msg);
    }

    public class Success<T> implements Result<T> {

        // Declared here since the T value is specific to the Success class (otherwise we may have wanted to use
        // an abstract class with common members).
        private final T value;

        private Success(T value) {
            this.value = value;
        }
        @Override
        public void bind(SideEffect<T> success, SideEffect<String> failure) {
            success.apply(value);
        }
    }

    public class Failure<T> implements Result<T> {

        private final String msg;

        private Failure(String msg) {
            this.msg = msg;
        }

        @Override
        public void bind(SideEffect<T> success, SideEffect<String> failure) {
            failure.apply(msg);
        }
    }
}

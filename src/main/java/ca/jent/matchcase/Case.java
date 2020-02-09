package ca.jent.matchcase;

import ca.jent.Tuple;
import ca.jent.functional.Result;

import java.util.function.Supplier;

public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>> {

    private Case(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier) {
        super(booleanSupplier, resultSupplier);
    }

    public static <T> Case<T> matchCase(Supplier<Boolean> predicateSupplier, Supplier<Result<T>> resultSupplier) {
        return new Case<>(predicateSupplier, resultSupplier);
    }

    public static <T> DefaultCase<T> matchCase(Supplier<Result<T>> resultSupplier) {
        return new DefaultCase<>( () -> true, resultSupplier );
    }

    public static <T> Result<T> match(DefaultCase<T> defaultCase, Case<T>... cases) {
        for (Case<T> acase : cases) {
            if (acase.getFirst().get()) return acase.getSecond().get();
        }
        return defaultCase.getSecond().get();
    }

    private static class DefaultCase<T> extends Case<T> {
        private DefaultCase(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier) {
            super(booleanSupplier, resultSupplier);
        }
    }
}

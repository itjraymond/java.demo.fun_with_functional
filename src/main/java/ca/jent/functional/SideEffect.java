package ca.jent.functional;

public interface SideEffect<T> {
    void apply(T t);
}

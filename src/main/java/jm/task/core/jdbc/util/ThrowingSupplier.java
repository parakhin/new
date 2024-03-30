package jm.task.core.jdbc.util;
@FunctionalInterface
public interface ThrowingSupplier<T, U extends Exception> {
    T get() throws U;
}

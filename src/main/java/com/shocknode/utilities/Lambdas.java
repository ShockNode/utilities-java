package com.shocknode.utilities;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Lambdas {


    @FunctionalInterface
    public interface RunnableWithException<E extends Exception> {
        void run() throws E;
    }

    public static <E extends Exception> Runnable runWithException(RunnableWithException<E> runnable) throws E  {
        return () -> {

            try { runnable.run(); }
            catch (Exception exception) { toss(exception); }

        };
    }

    @FunctionalInterface
    public interface FunctionWithException<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    @FunctionalInterface
    public interface ConsumerWithException<T, E extends Exception> {
        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface BiConsumerWithException<T, U, E extends Exception> {
        void accept(T t, U u) throws E;
    }

    @FunctionalInterface
    public interface SupplierWithException<T, E extends Exception> {
        T get() throws E;
    }

    public static <T, R, E extends Exception> Function<T, R> applyWithException(FunctionWithException<T, R, E> function) throws E  {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                toss(exception);
                return null;
            }
        };
    }

    public static <T, E extends Exception> Consumer<T> consumeWithException(ConsumerWithException<T, E> consumer) throws E  {
        return t -> {
            try { consumer.accept(t); }
            catch (Exception exception) { toss(exception); }
        };
    }

    public static <T, U, E extends Exception> BiConsumer<T, U> consumeBothWithException(BiConsumerWithException<T, U, E> consumer) throws E  {
        return (t, u) -> {
            try { consumer.accept(t, u); }
            catch (Exception exception) { toss(exception); }
        };
    }

    public static <T, E extends Exception> Supplier<T> supplyWithException(SupplierWithException<T, E> supplier) throws E  {
        return () -> {
            try { return supplier.get(); }
            catch (Exception exception) { toss(exception); return null; }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Exception> void toss(Exception exception) throws E {
        throw (E) exception;
    }

}

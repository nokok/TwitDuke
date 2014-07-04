package net.nokok.twitduke.core.util;

import java.util.function.Supplier;

public class BooleanFunctionalUtil {

    /**
     * 指定されたコンディションがtrueの時に指定された例外をスローします。
     *
     * @param <X>               スローされる例外の型
     * @param cond              コンディション
     * @param exceptionSupplier 例外をスローするサプライヤ
     *
     * @throws X
     */
    public static <X extends Throwable> void throwWhenTrueCondition(boolean cond, Supplier<? extends X> exceptionSupplier) throws X {
        if ( cond ) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 指定されたコンディションがtrueの時に指定された例外をスローします。
     *
     * @param <X>               スローされる例外の型
     * @param booleanSupplier   Booleanのサプライヤ
     * @param exceptionSupplier 例外をスローするサプライヤ
     *
     * @throws X
     */
    public static <X extends Throwable> void throwWhenTrueCondition(Supplier<Boolean> booleanSupplier, Supplier<? extends X> exceptionSupplier) throws X {
        if ( booleanSupplier.get() ) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 指定されたコンディションがfalseの時に指定された例外をスローします。
     *
     * @param <X>               スローされる例外の型
     * @param cond              コンディション
     * @param exceptionSupplier 例外をスローするサプライヤ
     *
     * @throws X
     */
    public static <X extends Throwable> void throwWhenFalseCondition(boolean cond, Supplier<? extends X> exceptionSupplier) throws X {
        if ( !cond ) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 指定されたコンディションがfalseの時に指定された例外をスローします。
     *
     * @param <X>               スローされる例外の型
     * @param booleanSupplier   Booleanのサプライヤ
     * @param exceptionSupplier 例外をスローするサプライヤ
     *
     * @throws X
     */
    public static <X extends Throwable> void throwWhenFalseCondition(Supplier<Boolean> booleanSupplier, Supplier<? extends X> exceptionSupplier) throws X {
        if ( !booleanSupplier.get() ) {
            throw exceptionSupplier.get();
        }
    }
}

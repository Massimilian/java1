package ru.progwards.java2.lessons.annotation.annotations;

import org.junit.Test;

public @interface TestAnn {
    Class<? extends Throwable> expected() default Test.None.class;

    long timeout() default 0L;

    public static class None extends Throwable {
        private static final long serialVersionUID = 1L;

        private None() {
        }
    }
}

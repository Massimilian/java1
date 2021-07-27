package ru.progwards.java2.lessons.annotation;

import ru.progwards.java2.lessons.calculator.Calculator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Before {
    Class object() default Object.class;
}

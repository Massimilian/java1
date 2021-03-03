package ru.progwards.java2.lessons.old_version.annotation.annotations;

import ru.progwards.java2.lessons.old_version.annotation.calculator.Calculator2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface BeforeAnn {
    Class calculator() default Calculator2.class;
}


package ru.progwards.java2.lessons.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD) // указание, что данная аннотация будет работать с полями класса
@Retention(value = RetentionPolicy.RUNTIME) // указание, что данная аннотация относится к типу RUNTIME
public @interface BeforeAnn {

}


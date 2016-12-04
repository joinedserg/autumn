package dev.autumn.annotaion;

import java.lang.annotation.*;

@Target(value=ElementType.TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
public @interface Component {
	String value() default "auto";	
}


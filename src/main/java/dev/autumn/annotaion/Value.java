package dev.autumn.annotaion;

import java.lang.annotation.*;

@Target(value=ElementType.FIELD)
@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
public @interface Value {
	String value();
	
}

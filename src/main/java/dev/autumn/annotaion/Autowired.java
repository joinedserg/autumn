package dev.autumn.annotaion;

import java.lang.annotation.*;


@Target(value=ElementType.FIELD)
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Autowired {
	String value() default "auto";	
}

package com.sequoiacap.enums;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Ordinal
{
	int value();
	String name() default "";
}

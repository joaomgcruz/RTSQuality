package br.ufrn.ppgsc.scenario.analyzer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Reliability {
	public String name();
	public double failure_rate() default 0;
}

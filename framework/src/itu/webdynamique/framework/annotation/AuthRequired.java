package itu.webdynamique.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthRequired {
    String value() default "user";
    String roleAttribute() default "role";
    String[] roles() default {};
    String redirect() default "/login";
}

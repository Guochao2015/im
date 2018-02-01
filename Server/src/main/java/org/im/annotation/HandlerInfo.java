package org.im.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HandlerInfo {
    String desc() default "";
    String xmlns() default "";
}

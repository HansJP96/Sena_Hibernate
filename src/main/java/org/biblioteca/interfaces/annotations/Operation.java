package org.biblioteca.interfaces.annotations;

import jakarta.validation.groups.Default;
import org.biblioteca.enums.ModelInputEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion con la informacion necesaria para ejecutar correctamente las acciones de los controladores
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Operation {

    String selectable() default "";

    String result() default "";

    ModelInputEnum inputType() default ModelInputEnum.MODEL_PARAM;

    String id() default "";

    Class<?>[] opClassRestrict() default {Default.class};
}

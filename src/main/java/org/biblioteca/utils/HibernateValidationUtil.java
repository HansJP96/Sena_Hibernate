package org.biblioteca.utils;

import jakarta.validation.Configuration;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;

import java.util.Arrays;

/**
 * Clase de utilidad para configurar la capa de validaciones de Hiberante
 */
public class HibernateValidationUtil {

    private HibernateValidationUtil() {
    }

    /**
     * Configura el constructor de la clase ValidatorFactory requerida para interceptar correctamente las excepciones por validacion de datos
     * @return Devuelve la instancia unica de la clase Validator con los datos de configuracion
     */
    public static Validator generateValidator() {
        Configuration<?> configuration = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(
                        new ResourceBundleMessageInterpolator(
                                new AggregateResourceBundleLocator(
                                        Arrays.asList(
                                                "CategoriasValidationMessages",
                                                "EditorialesValidationMessages",
                                                "LibrosValidationMessages"
                                        )
                                )
                        )
                );
        try (ValidatorFactory factory = configuration.buildValidatorFactory()) {
            return factory.getValidator();
        }
    }
}

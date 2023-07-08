package org.biblioteca.utils;

import jakarta.persistence.Entity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import java.util.Set;

/**
 * Clase de utilidades para la configuracion inicial de Hibernate relativo a la sesion de comunicacion
 */
public class SessionConfigUtil {

    private static SessionFactory sessionFactory;
    private static final Set<Class<?>> entityClasses = new Reflections("org.biblioteca.models").getTypesAnnotatedWith(Entity.class);

    private SessionConfigUtil() {
    }

    /**
     * Devuelve una instancia de SessionFactory configurada
     * @return Objeto session configurada para iniciar las transacciones de Hiberante
     */
    public static synchronized SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
                for (Class<?> clazz : entityClasses) {
                    configuration.addAnnotatedClass(clazz);
                }
                sessionFactory = configuration.buildSessionFactory();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return sessionFactory;
    }
}

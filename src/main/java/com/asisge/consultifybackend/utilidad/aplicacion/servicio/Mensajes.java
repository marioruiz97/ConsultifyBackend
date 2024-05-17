package com.asisge.consultifybackend.utilidad.aplicacion.servicio;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Mensajes {
    public static final String BUNDLE_NAME = "messages";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private Mensajes() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, Object... args) {
        try {
            String mensaje = RESOURCE_BUNDLE.getString(key);
            return String.format(mensaje, args);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}

package com.asisge.consultifybackend.aplicacion.servicio;

import java.security.SecureRandom;
import java.util.Random;

public final class GeneradorContrasena {

    private static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final String CARACTERES_ESPECIALES = "@$!%*?&";
    private static final Random RANDOM = new SecureRandom();

    private GeneradorContrasena() {
    }

    /**
     * GeneradorContrasena.generarContrasenaSegura
     * este método se encarga de crear una contraseña segura siguiendo los requisitos mínimos del sistema.
     * la contraseña a generar tiene que tener mínimo 1 mayúscula, 1 minúscula, 1 número y 1 caracter especial, y debe tener entre 8 y 16 caracteres de longitud.
     * <p>
     * la contraseña que genera este método contiene 9 caracteres, 3 mayúsculas, 3 minúsculas, 2 números y 1 caracter especial, ordenado de forma aleatoria
     *
     * @return contrasena devuelve una contrasena con 3 mayúsculas, 3 minúsculas, 2 números y 1 caracter especial ordenados de forma aleatoria
     */
    public static String generarContrasenaSegura() {
        StringBuilder contrasena = new StringBuilder();
        String[] grupos = {MAYUSCULAS, MAYUSCULAS, MAYUSCULAS, MINUSCULAS, MINUSCULAS, MINUSCULAS, NUMEROS, NUMEROS, CARACTERES_ESPECIALES};

        for (String grupo : grupos) {
            char caracter = grupo.charAt(RANDOM.nextInt(grupo.length()));
            contrasena.append(caracter);
        }

        // Mezclar los caracteres aleatoriamente
        for (int i = contrasena.length() - 1; i > 0; i--) {
            int indiceAleatorio = RANDOM.nextInt(i + 1);
            char temp = contrasena.charAt(indiceAleatorio);
            contrasena.setCharAt(indiceAleatorio, contrasena.charAt(i));
            contrasena.setCharAt(i, temp);
        }

        return contrasena.toString();
    }

}

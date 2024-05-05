package com.asisge.consultifybackend.utilidad.dominio.modelo;

public abstract class ExpresionRegular {

    public static final String PATRON_TELEFONO = "^(60\\d)\\d{7}$|^(3\\d{9})$";
    public static final String PATRON_CORREO = "^[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";
    private ExpresionRegular() {
    }
}

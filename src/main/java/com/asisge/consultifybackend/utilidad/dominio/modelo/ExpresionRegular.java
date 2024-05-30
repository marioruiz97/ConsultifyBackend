package com.asisge.consultifybackend.utilidad.dominio.modelo;

public abstract class ExpresionRegular {

    public static final String PATRON_TELEFONO = "^(60\\d)\\d{7}$|^(3\\d{9})$";
    public static final String PATRON_CORREO = "^[\\w._%+-]+@[\\w.-]+\\.+[a-zA-Z]{2,}$";
    public static final String PATRON_CONTRASENA = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[/.:;,\"{}+<>@$!%*#?&^_-]).{8,20}$";

    private ExpresionRegular() {
    }

}

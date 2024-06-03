package com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.autenticacion.dominio.modelo.TokenVerificacion;
import com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.entidad.EntidadTokenVerificacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.convertidor.ConvertidorUsuario;

public final class ConvertidorToken {

    private ConvertidorToken() {
    }

    public static TokenVerificacion aDominio(EntidadTokenVerificacion entidad) {
        TokenVerificacion token = null;

        if (entidad != null) {
            Usuario usuario = ConvertidorUsuario.aUsuarioDominio(entidad.getUsuario());

            token = new TokenVerificacion(entidad.getIdToken(),
                    entidad.getToken(),
                    usuario,
                    entidad.getCreadoEn(),
                    entidad.getExpiraEn());
        }

        return token;
    }
}

package com.asisge.consultifybackend.infraestructura.adaptador.convertidor;


import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.infraestructura.adaptador.entidad.EntidadUsuario;

public final class ConvertidorUsuario {

    private ConvertidorUsuario() {
    }

    public static Usuario aDominio(EntidadUsuario entidad) {
        Usuario nuevoUsuario = null;
        if (entidad != null) {
            nuevoUsuario = new Usuario(entidad.getIdUsuario(), entidad.getIdentificacion(), entidad.getNombres(), entidad.getApellidos(), entidad.getTelefono(),
                    entidad.getCorreo());
        }
        return nuevoUsuario;
    }

    public static UsuarioAutenticado aDominio(EntidadUsuario entidad, String creadoPor) {
        Usuario nuevoUsuario = aDominio(entidad);
        UsuarioAutenticado usuarioAutenticado = null;
        if (entidad != null) {
            usuarioAutenticado = new UsuarioAutenticado(nuevoUsuario, entidad.getNombreUsuario(), entidad.getContrasena(), entidad.getCreadoEn(), creadoPor,
                    entidad.getUltimoInicio(), entidad.getActivo(), entidad.getVerificado());
        }
        return usuarioAutenticado;
    }

    public static EntidadUsuario aEntidad(Usuario usuario, UsuarioAutenticado auth) {
        EntidadUsuario entidad = new EntidadUsuario();
        entidad.setIdUsuario(usuario.getIdUsuario());
        entidad.setIdentificacion(usuario.getIdentificacion());
        entidad.setNombres(usuario.getNombres());
        entidad.setApellidos(usuario.getApellidos());
        entidad.setTelefono(usuario.getTelefono());
        entidad.setCorreo(usuario.getCorreo());
        entidad.setNombreUsuario(auth.getNombreUsuario());
        entidad.setContrasena(auth.getContrasena());
        entidad.setCreadoEn(auth.getCreadoEn());
        entidad.setCreadoPor(1L);
        entidad.setUltimoInicio(auth.getUltimoInicio());
        entidad.setActivo(auth.getActivo());
        entidad.setVerificado(auth.getVerificado());

        return entidad;
    }

}

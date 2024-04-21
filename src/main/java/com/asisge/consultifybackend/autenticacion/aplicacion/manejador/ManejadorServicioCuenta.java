package com.asisge.consultifybackend.autenticacion.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioCuenta;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;
import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManejadorServicioCuenta implements ServicioCuenta {

    private final RepositorioAutorizacion repositorioAutorizacion;

    @Autowired
    public ManejadorServicioCuenta(RepositorioAutorizacion repositorioAutorizacion) {
        this.repositorioAutorizacion = repositorioAutorizacion;
    }

    @Override
    public MisDatos buscarPorIdUsuario(Long idUsuario) {
        UsuarioAutenticado cuenta = repositorioAutorizacion.buscarPorIdUsuario(idUsuario);
        Usuario usuario = cuenta.getUsuario();
        return new MisDatos(
                usuario.getIdentificacion(),
                usuario.getTipoDocumento(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getTelefono(),
                usuario.getCorreo(),
                cuenta.getNombreUsuario(),
                cuenta.getCreadoPor(),
                cuenta.getRol()
        );
    }
}

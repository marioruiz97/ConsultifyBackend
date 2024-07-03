package com.asisge.consultifybackend.autenticacion.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioUserDetailsImpl implements UserDetailsService {


    private final RepositorioAutorizacion repositorioAutorizacion;

    @Autowired
    public ServicioUserDetailsImpl(RepositorioAutorizacion repositorioAutorizacion) {
        this.repositorioAutorizacion = repositorioAutorizacion;
    }

    @Override
    public UserDetails loadUserByUsername(String correoOUsername) throws UsernameNotFoundException {
        UsuarioAutenticado usuario = repositorioAutorizacion.buscarPorNombreUsuarioOCorreo(correoOUsername);
        if (usuario == null) {
            throw new UsernameNotFoundException("no se encontró el usuario " + correoOUsername);
        }
        GrantedAuthority authorities = new SimpleGrantedAuthority(usuario.getRol().name());
        return new User(correoOUsername, usuario.getContrasena(), usuario.getActivo(), true, true,
                usuario.getVerificado(), List.of(authorities));
    }
}

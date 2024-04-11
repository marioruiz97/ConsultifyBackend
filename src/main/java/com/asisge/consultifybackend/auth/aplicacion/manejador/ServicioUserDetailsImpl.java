package com.asisge.consultifybackend.auth.aplicacion.manejador;

import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.dominio.puerto.RepositorioUsuario;
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


    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUserDetailsImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String correoOUsername) throws UsernameNotFoundException {
        UsuarioAutenticado usuario = repositorioUsuario.buscarPorCorreoOUsername(correoOUsername);
        if (usuario == null) {
            throw new UsernameNotFoundException("no se encontró el usuario " + correoOUsername);
        }
        GrantedAuthority authorities = new SimpleGrantedAuthority(usuario.getRol().name());
        return new User(correoOUsername, usuario.getContrasena(), usuario.getActivo(), true, true,
                usuario.getVerificado(), List.of(authorities));
    }
}

package com.asisge.consultifybackend.auth.dominio.puerto;

import com.asisge.consultifybackend.dominio.modelo.Usuario;

public interface RepositorioAutorizacion {
    Usuario buscarPorCorreo(String correo);
}

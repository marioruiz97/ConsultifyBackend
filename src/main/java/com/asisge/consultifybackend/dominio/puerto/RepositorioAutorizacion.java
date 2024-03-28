package com.asisge.consultifybackend.dominio.puerto;

import com.asisge.consultifybackend.dominio.modelo.Usuario;

public interface RepositorioAutorizacion {
    Usuario buscarPorCorreo(String correo);
}

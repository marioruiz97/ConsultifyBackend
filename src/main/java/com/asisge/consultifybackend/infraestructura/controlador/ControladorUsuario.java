package com.asisge.consultifybackend.infraestructura.controlador;

import com.asisge.consultifybackend.aplicacion.manejador.ManejadorServicioUsuario;
import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios/")
public class ControladorUsuario {

    private final ManejadorServicioUsuario manejadorServicioUsuario;

    @Autowired
    public ControladorUsuario(ManejadorServicioUsuario manejadorServicioUsuario) {
        this.manejadorServicioUsuario = manejadorServicioUsuario;
    }

    @GetMapping
    public List<Usuario> buscarUsuarios() {
        return manejadorServicioUsuario.buscarTodos();
    }

    @GetMapping("autenticados/")
    public List<UsuarioAutenticado> buscarUsuariosAutenticados() {
        return manejadorServicioUsuario.buscarTodosAutenticados();
    }

    @GetMapping("{identificacion}")
    public UsuarioAutenticado obternerPorIdentificacion(@PathVariable("identificacion") String identificacion) {
        return manejadorServicioUsuario.buscarUsuarioPorIdentificacion(identificacion);
    }
}

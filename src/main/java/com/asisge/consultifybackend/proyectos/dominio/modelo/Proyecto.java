package com.asisge.consultifybackend.proyectos.dominio.modelo;

import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Proyecto {

    private Long idProyecto;

    private String nombreProyecto;

    private Cliente clienteProyecto;

    private String descripcionProyecto;

    private List<UsuarioAutenticado> miembros;

    public void agregarMiembro(UsuarioAutenticado usuario) {
        if (this.miembros != null) this.miembros.add(usuario);
        else this.miembros = List.of(usuario);
    }

    public void validarProyecto() {
        boolean esValido = nombreProyecto != null && !nombreProyecto.trim().equals("") &&
                descripcionProyecto != null && !descripcionProyecto.trim().equals("") &&
                clienteProyecto != null;
        if (!esValido) throw new IllegalArgumentException("Verifica que los campos obligatorios están diligenciados");
    }
}

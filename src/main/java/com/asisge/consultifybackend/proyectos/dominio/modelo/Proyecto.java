package com.asisge.consultifybackend.proyectos.dominio.modelo;

import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(setterPrefix = "con")
@AllArgsConstructor
@ToString(exclude = "miembros")
public class Proyecto {

    private Long idProyecto;

    private String nombreProyecto;

    private Cliente clienteProyecto;

    private String descripcionProyecto;

    private LocalDateTime creadoEn;

    private LocalDate cierreEsperado;

    private List<UsuarioAutenticado> miembros;

    public Proyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public void agregarMiembro(UsuarioAutenticado usuario) {
        if (this.miembros != null) this.miembros.add(usuario);
        else this.miembros = List.of(usuario);
    }

    public void validarProyecto() {
        boolean esValido = nombreProyecto != null && !nombreProyecto.trim().isEmpty() &&
                descripcionProyecto != null && !descripcionProyecto.trim().isEmpty() &&
                clienteProyecto != null;
        if (!esValido) throw new IllegalArgumentException("Verifica que los campos obligatorios están diligenciados");
    }
}

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

}

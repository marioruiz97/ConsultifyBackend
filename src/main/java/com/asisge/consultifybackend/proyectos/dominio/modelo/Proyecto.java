package com.asisge.consultifybackend.proyectos.dominio.modelo;

import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Proyecto {

    private Long idProyecto;

    private String nombreProyecto;

    private ClienteProyecto clienteProyecto;

    private List<Actividad> actividades;

    private List<UsuarioAutenticado> miembros;

}

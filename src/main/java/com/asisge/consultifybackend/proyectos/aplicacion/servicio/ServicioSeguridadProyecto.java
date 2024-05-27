package com.asisge.consultifybackend.proyectos.aplicacion.servicio;

public interface ServicioSeguridadProyecto {

    boolean esMiembroProyecto(Long idProyecto, String username);

    boolean esAdmin();
}

package com.asisge.consultifybackend.proyectos.aplicacion.manejador;

import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioProyecto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioProyecto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManejadorServicioProyecto implements ServicioProyecto {

    private final RepositorioProyecto repositorioProyecto;

    @Autowired
    public ManejadorServicioProyecto(RepositorioProyecto repositorioProyecto) {
        this.repositorioProyecto = repositorioProyecto;
    }

    @Override
    public List<Proyecto> obtenerTodos() {
        return repositorioProyecto.obtenerTodos();
    }

    @Override
    public Proyecto obtenerProyectoPorId(Long idProyecto) {
        return repositorioProyecto.obtenerProyectoPorId(idProyecto);
    }

    @Override
    public Proyecto crearProyecto(Proyecto proyecto) {
        return repositorioProyecto.crearProyecto(proyecto);
    }

    @Override
    public Proyecto editarProyecto(Long idProyecto, Proyecto proyecto) {
        if (proyecto.getIdProyecto() == null || !proyecto.getIdProyecto().equals(idProyecto))
            throw new IllegalArgumentException("El id del proyecto es null o no corresponde al del objeto enviado en la peticion");
        if (!repositorioProyecto.existeProyectoPorId(idProyecto))
            throw new EntityNotFoundException("No se encontro el proyecto en la base de datos");

        return repositorioProyecto.editarProyecto(idProyecto, proyecto);
    }
}

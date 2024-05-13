package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioActividad;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioActividadJPA extends JpaRepository<EntidadActividad, Long>, RepositorioActividad {
}

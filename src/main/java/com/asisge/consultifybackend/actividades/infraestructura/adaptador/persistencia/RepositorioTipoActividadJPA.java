package com.asisge.consultifybackend.actividades.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.actividades.dominio.modelo.TipoActividad;
import com.asisge.consultifybackend.actividades.dominio.puerto.RepositorioTipoActividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioTipoActividadJPA extends JpaRepository<TipoActividad, Long>, RepositorioTipoActividad {
}

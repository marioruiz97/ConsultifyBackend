package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad.EntidadActividad;
import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;
import com.asisge.consultifybackend.clientes.infraestructura.adaptador.convertidor.ConvertidorCliente;
import com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad.EntidadCliente;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadProyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;

import java.util.List;

public final class ConvertidorProyecto {

    private ConvertidorProyecto() {
    }

    public static Proyecto aDominio(EntidadProyecto entidad) {
        Proyecto proyecto = null;
        if (entidad != null) {
            Cliente cliente = ConvertidorCliente.aDominio(entidad.getClienteProyecto());
            List<UsuarioAutenticado> miembros = entidad.getMiembros().stream()
                    .map(ConvertidorUsuario::aDominio)
                    .toList();
            miembros.forEach(UsuarioAutenticado::limpiarContrasena);
            proyecto = new Proyecto(
                    entidad.getIdProyecto(),
                    entidad.getNombreProyecto(),
                    cliente,
                    entidad.getDescripcionProyecto(),
                    entidad.getCreadoEn(),
                    entidad.getCierreEsperado(),
                    miembros
            );
        }
        return proyecto;
    }

    public static EntidadProyecto aEntidad(Proyecto proyecto) {
        EntidadProyecto entidad = new EntidadProyecto();
        entidad.setIdProyecto(proyecto.getIdProyecto());
        entidad.setNombreProyecto(proyecto.getNombreProyecto());
        entidad.setDescripcionProyecto(proyecto.getDescripcionProyecto());
        entidad.setCierreEsperado(proyecto.getCierreEsperado());

        EntidadCliente cliente = new EntidadCliente(proyecto.getClienteProyecto().getIdCliente());
        entidad.setClienteProyecto(cliente);

        List<EntidadUsuario> miembros = proyecto.getMiembros().stream().map(miembro -> new EntidadUsuario(miembro.getUsuario().getIdUsuario())).toList();
        entidad.setMiembros(miembros);

        return entidad;
    }

    public static EntidadProyecto aActualizarEntidad(EntidadProyecto actual, Proyecto proyecto) {
        if (actual != null) {
            actual.setIdProyecto(proyecto.getIdProyecto());
            actual.setDescripcionProyecto(proyecto.getDescripcionProyecto());
            actual.setNombreProyecto(proyecto.getNombreProyecto());
            actual.setCierreEsperado(proyecto.getCierreEsperado());
        }
        return actual;
    }


    public static Proyecto construirProyecto(EntidadActividad entidad) {
        EntidadProyecto proyecto = entidad.getProyecto();
        return Proyecto.builder()
                .conIdProyecto(proyecto.getIdProyecto())
                .conDescripcionProyecto(proyecto.getDescripcionProyecto())
                .conNombreProyecto(proyecto.getNombreProyecto())
                .conCreadoEn(proyecto.getCreadoEn())
                .conCierreEsperado(proyecto.getCierreEsperado())
                .build();
    }


}

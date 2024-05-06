package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.convertidor;

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
                    .map(miembro -> ConvertidorUsuario.aDominio(miembro, miembro.getCreadoPor().toString()))
                    .toList();
            proyecto = new Proyecto(
                    entidad.getIdProyecto(),
                    entidad.getNombreProyecto(),
                    cliente,
                    entidad.getDescripcionProyecto(),
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

        EntidadCliente cliente = new EntidadCliente(proyecto.getClienteProyecto().getIdCliente());
        entidad.setClienteProyecto(cliente);

        List<EntidadUsuario> miembros = proyecto.getMiembros().stream().map(miembro -> new EntidadUsuario(miembro.getUsuario().getIdUsuario())).toList();
        entidad.setMiembros(miembros);

        return entidad;
    }
}

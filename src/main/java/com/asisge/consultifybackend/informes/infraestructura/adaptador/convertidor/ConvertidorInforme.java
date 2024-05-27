package com.asisge.consultifybackend.informes.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad.EntidadCliente;
import com.asisge.consultifybackend.informes.dominio.modelo.ClienteInforme;
import com.asisge.consultifybackend.informes.dominio.modelo.InformeProyecto;
import com.asisge.consultifybackend.informes.dominio.modelo.MiembroInforme;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadProyecto;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;

import java.util.List;

public final class ConvertidorInforme {

    private ConvertidorInforme() {
    }


    public static InformeProyecto aInfoProyecto(EntidadProyecto entidad) {
        InformeProyecto dominio = null;

        if (entidad != null) {

            EntidadCliente cliente = entidad.getClienteProyecto();
            ClienteInforme clienteInforme = new ClienteInforme(
                    cliente.getIdCliente(),
                    cliente.getTipoDocumento(),
                    cliente.getNumeroIdentificacion(),
                    cliente.getNombreComercial(),
                    cliente.getRazonSocial());

            List<MiembroInforme> miembros = entidad.getMiembros().stream().map(ConvertidorInforme::mapearMiembro).toList();

            dominio = new InformeProyecto(
                    entidad.getIdProyecto(),
                    entidad.getNombreProyecto(),
                    clienteInforme,
                    false,
                    miembros,
                    null
            );
        }

        return dominio;
    }

    private static MiembroInforme mapearMiembro(EntidadUsuario usuario) {
        MiembroInforme miembro = null;

        if (usuario != null) {
            miembro = new MiembroInforme(
                    usuario.getIdUsuario(),
                    usuario.getNombres() + " " + usuario.getApellidos()
            );
        }
        return miembro;
    }

}

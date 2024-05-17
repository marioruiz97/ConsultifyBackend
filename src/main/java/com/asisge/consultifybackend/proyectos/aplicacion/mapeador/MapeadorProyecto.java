package com.asisge.consultifybackend.proyectos.aplicacion.mapeador;

import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;
import com.asisge.consultifybackend.proyectos.aplicacion.dto.ProyectoDto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MapeadorProyecto {

    public Proyecto aProyecto(ProyectoDto dto) {
        Proyecto proyecto = null;
        if (dto != null && dto.validarDto()) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(dto.getIdClienteProyecto());

            proyecto = new Proyecto(
                    dto.getIdProyecto(),
                    dto.getNombreProyecto(),
                    cliente,
                    dto.getDescripcionProyecto(),
                    null,
                    new ArrayList<>()
            );
        }
        return proyecto;
    }
}

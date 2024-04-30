package com.asisge.consultifybackend.clientes.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;
import com.asisge.consultifybackend.clientes.dominio.modelo.ContactoCliente;
import com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad.EntidadCliente;
import com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad.EntidadContactoCliente;

import java.util.List;

public final class ConvertidorCliente {

    private ConvertidorCliente() {
    }

    public static Cliente aDominio(EntidadCliente entidad) {
        Cliente cliente = null;
        if (entidad != null) {
            List<ContactoCliente> contactos = entidad.getContactos().stream().map(ConvertidorContactoCliente::aDominio).toList();
            cliente = new Cliente(
                    entidad.getIdCliente(),
                    entidad.getNumeroIdentificacion(),
                    entidad.getNombreComercial(),
                    entidad.getRazonSocial(),
                    entidad.getTipoDocumento(),
                    contactos
            );
        }
        return cliente;
    }

    public static EntidadCliente aEntidad(Cliente dominio) {
        EntidadCliente entidad = new EntidadCliente();
        entidad.setIdCliente(dominio.getIdCliente());
        entidad.setNumeroIdentificacion(dominio.getNumeroIdentificacion());
        entidad.setTipoDocumento(dominio.getTipoDocumento());
        entidad.setRazonSocial(dominio.getRazonSocial());
        entidad.setNombreComercial(dominio.getNombreComercial());
        if (!dominio.getContactos().isEmpty()) {
            List<EntidadContactoCliente> contactos = dominio.getContactos().stream().map(ConvertidorContactoCliente::aEntidad).toList();
            entidad.setContactos(contactos);
        }
        return entidad;
    }

}

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

    public static EntidadCliente aCrearEntidad(Cliente dominio) {
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

    /**
     * Método usado para actualizar los datos de un cliente existente. el manejo de los contactos requiere una lógica especial.
     * <p>El dominio siempre debe enviar la lista completa de contactos, lo que deja lugar a 2 casos:</p>
     * <p>1. El dominio manda la lista vacía, quiere decir que se eliminará la lista actual si la hay y no se dejará ningún contacto</p>
     * <p>2. El dominio manda la lista, lo que requiere actualizar la lista, para esto se mantendrán los contactos repetidos en las 2 listas, y se agregaran los nuevos</p>
     * @param entidad objeto entidad existente en la base de datos
     * @param dominio objeto dominio que sirve para actualizar la entidad
     * @return devuelve la entidad actualizada para guardarse en base de datos
     */
    public static EntidadCliente aActualizarEntidad(EntidadCliente entidad, Cliente dominio) {
        entidad.setRazonSocial(dominio.getRazonSocial());
        entidad.setNombreComercial(dominio.getNombreComercial());
        entidad.setTipoDocumento(dominio.getTipoDocumento());
        entidad.setNumeroIdentificacion(dominio.getNumeroIdentificacion());

        List<ContactoCliente> contactosDominio = dominio.getContactos();
        // caso 1: lista vacia
        if(contactosDominio.isEmpty()) entidad.eliminarContactos();
        // caso 2: actualizar contactos
        if (!contactosDominio.isEmpty()) {
            List<EntidadContactoCliente> contactos = dominio.getContactos().stream().map(ConvertidorContactoCliente::aEntidad).toList();
            entidad.actualizarContactos(contactos);
        }

        return entidad;
    }
}

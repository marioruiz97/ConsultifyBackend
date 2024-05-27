package com.asisge.consultifybackend.clientes.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.clientes.dominio.modelo.ContactoCliente;
import com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad.EntidadContactoCliente;

public final class ConvertidorContactoCliente {

    private ConvertidorContactoCliente() {
    }


    public static ContactoCliente aDominio(EntidadContactoCliente entidad) {
        ContactoCliente contacto = null;
        if (entidad != null) {
            contacto = new ContactoCliente(
                    entidad.getId(),
                    entidad.getNombreCompleto(),
                    entidad.getCargo(),
                    entidad.getTelefono(),
                    entidad.getCorreo()
            );
        }
        return contacto;
    }

    public static EntidadContactoCliente aEntidad(ContactoCliente dominio) {
        EntidadContactoCliente contacto = new EntidadContactoCliente();
        String id = dominio.getId() != null ? dominio.getId() : null;
        contacto.setId(id);
        contacto.setNombreCompleto(dominio.getNombreCompleto());
        contacto.setCargo(dominio.getCargo());
        contacto.setTelefono(dominio.getTelefono());
        contacto.setCorreo(dominio.getCorreo());
        return contacto;
    }
}

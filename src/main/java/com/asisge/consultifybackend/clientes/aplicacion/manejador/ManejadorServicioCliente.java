package com.asisge.consultifybackend.clientes.aplicacion.manejador;

import com.asisge.consultifybackend.clientes.aplicacion.servicio.ServicioCliente;
import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;
import com.asisge.consultifybackend.clientes.dominio.modelo.ContactoCliente;
import com.asisge.consultifybackend.clientes.dominio.puerto.RepositorioCliente;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.AccionNoPermitidaException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManejadorServicioCliente implements ServicioCliente {

    private final Logger logger = LoggerFactory.getLogger(ManejadorServicioCliente.class);
    private final RepositorioCliente repositorioCliente;

    @Autowired
    public ManejadorServicioCliente(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    @Override
    public List<Cliente> buscarTodos() {
        return repositorioCliente.buscarTodosClientes();
    }

    @Override
    public Cliente buscarClientePorId(Long idCliente) {
        return repositorioCliente.buscarClientePorId(idCliente);
    }

    @Override
    public Cliente crearCliente(Cliente nuevoCliente) {
        nuevoCliente.validarCampos();
        nuevoCliente.setIdCliente(null);
        validarContactos(nuevoCliente.getContactos());
        alistarNuevosContactos(nuevoCliente.getContactos());

        String mensaje = Mensajes.getString("clientes.info.crear.cliente");
        logger.info(mensaje, nuevoCliente);

        return repositorioCliente.crearCliente(nuevoCliente);
    }

    @Override
    public Cliente editarCliente(Long idCliente, Cliente editarCliente) {
        editarCliente.validarCampos();
        validarContactos(editarCliente.getContactos());
        if (editarCliente.getIdCliente() == null || !editarCliente.getIdCliente().equals(idCliente))
            throw new AccionNoPermitidaException(Mensajes.getString("clientes.error.id.cliente.no.coincide"));
        if (!repositorioCliente.existeClientePorId(idCliente))
            throw new EntityNotFoundException(Mensajes.getString("clientes.error.cliente.no.encontrado", idCliente));

        String mensaje = Mensajes.getString("clientes.info.editar.cliente", idCliente);
        logger.info(mensaje, editarCliente);
        return repositorioCliente.editarCliente(editarCliente);
    }

    @Secured("ROLE_ADMIN")
    @Override
    public Boolean eliminarCliente(Long idCliente) {
        if (!repositorioCliente.existeClientePorId(idCliente))
            return Boolean.FALSE;
        repositorioCliente.eliminarCliente(idCliente);

        String mensaje = Mensajes.getString("clientes.info.eliminar.cliente.exito", idCliente);
        logger.info(mensaje);
        return Boolean.TRUE;
    }

    private void validarContactos(List<ContactoCliente> contactos) {
        if (contactos != null && !contactos.isEmpty()) {
            boolean contactosValidos = contactos.stream()
                    .allMatch(ContactoCliente::validarContacto);
            if (!contactosValidos)
                throw new IllegalArgumentException(Mensajes.getString("clientes.error.contacto.invalido"));
        }
    }

    private void alistarNuevosContactos(List<ContactoCliente> contactos) {
        if (contactos != null && !contactos.isEmpty())
            contactos.forEach(contacto -> contacto.setId(null));
    }
}

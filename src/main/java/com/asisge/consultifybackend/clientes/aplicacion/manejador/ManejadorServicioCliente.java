package com.asisge.consultifybackend.clientes.aplicacion.manejador;

import com.asisge.consultifybackend.clientes.aplicacion.servicio.ServicioCliente;
import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;
import com.asisge.consultifybackend.clientes.dominio.puerto.RepositorioCliente;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManejadorServicioCliente implements ServicioCliente {

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
        return repositorioCliente.guardarCliente(nuevoCliente);
    }

    @Override
    public Cliente editarCliente(Long idCliente, Cliente editarCliente) {
        editarCliente.validarCampos();
        if (editarCliente.getIdCliente() == null || !editarCliente.getIdCliente().equals(idCliente))
            throw new IllegalArgumentException("El id del cliente a editar no puede ser null");
        if (!repositorioCliente.existeClientePorId(idCliente))
            throw new EntityNotFoundException("No se encontro el cliente en la base de datos");
        return repositorioCliente.guardarCliente(editarCliente);
    }
}

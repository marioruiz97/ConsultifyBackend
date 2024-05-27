package com.asisge.consultifybackend.clientes.aplicacion.servicio;

import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;

import java.util.List;

public interface ServicioCliente {

    List<Cliente> buscarTodos();

    Cliente buscarClientePorId(Long idCliente);

    Cliente crearCliente(Cliente nuevoCliente);

    Cliente editarCliente(Long idCliente, Cliente editarCliente);

    Boolean eliminarCliente(Long idCliente);
}

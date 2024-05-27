package com.asisge.consultifybackend.clientes.dominio.puerto;

import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;

import java.util.List;

public interface RepositorioCliente {

    List<Cliente> buscarTodosClientes();

    Cliente buscarClientePorId(Long idCliente);

    boolean existeClientePorId(Long idCliente);

    Cliente crearCliente(Cliente nuevoCliente);

    Cliente editarCliente(Cliente editarCliente);

    void eliminarCliente(Long idCliente);

}

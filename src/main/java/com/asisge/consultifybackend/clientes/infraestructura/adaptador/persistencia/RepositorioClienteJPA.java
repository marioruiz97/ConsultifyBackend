package com.asisge.consultifybackend.clientes.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;
import com.asisge.consultifybackend.clientes.dominio.puerto.RepositorioCliente;
import com.asisge.consultifybackend.clientes.infraestructura.adaptador.convertidor.ConvertidorCliente;
import com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad.EntidadCliente;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioClienteJPA extends JpaRepository<EntidadCliente, Long>, RepositorioCliente {

    @Override
    default List<Cliente> buscarTodosClientes() {
        return this.findAll().stream().map(ConvertidorCliente::aDominio).toList();
    }

    @Override
    default Cliente buscarClientePorId(Long idCliente) {
        EntidadCliente entidad = this.findById(idCliente)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el cliente en base de datos"));
        return ConvertidorCliente.aDominio(entidad);
    }

    @Override
    default boolean existeClientePorId(Long idCliente) {
        return this.existsById(idCliente);
    }

    @Override
    default Cliente crearCliente(Cliente nuevoCliente) {
        EntidadCliente entidad = ConvertidorCliente.aCrearEntidad(nuevoCliente);
        return ConvertidorCliente.aDominio(this.save(entidad));
    }

    @Override
    default Cliente editarCliente(Cliente editarCliente) {
        EntidadCliente actual = this.findById(editarCliente.getIdCliente()).orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente en base de datos"));
        EntidadCliente entidad = ConvertidorCliente.aActualizarEntidad(actual, editarCliente);
        return ConvertidorCliente.aDominio(this.save(entidad));
    }

    @Override
    default void eliminarCliente(Long idCliente) {
        deleteById(idCliente);
    }

}

package com.asisge.consultifybackend.clientes.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad.EntidadContactoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioContactoClienteJPA extends JpaRepository<EntidadContactoCliente, String> {
}

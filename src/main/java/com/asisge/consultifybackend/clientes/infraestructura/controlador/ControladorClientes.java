package com.asisge.consultifybackend.clientes.infraestructura.controlador;

import com.asisge.consultifybackend.clientes.aplicacion.servicio.ServicioCliente;
import com.asisge.consultifybackend.clientes.dominio.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ControladorClientes {

    private final ServicioCliente servicioCliente;

    @Autowired
    public ControladorClientes(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @GetMapping
    public List<Cliente> buscarClientes() {
        return servicioCliente.buscarTodos();
    }

    @GetMapping("/{idCliente}")
    public Cliente buscarClientePorId(@PathVariable Long idCliente) {
        return servicioCliente.buscarClientePorId(idCliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente nuevoCliente) {
        return new ResponseEntity<>(servicioCliente.crearCliente(nuevoCliente), HttpStatus.CREATED);
    }

    @PatchMapping("/{idCliente}")
    public ResponseEntity<Cliente> editarInformacionBasica(@PathVariable Long idCliente, @RequestBody Cliente editarCliente) {
        return new ResponseEntity<>(servicioCliente.editarCliente(idCliente, editarCliente), HttpStatus.CREATED);
    }

}

package com.asisge.consultifybackend.autenticacion.infraestructura.controlador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioCuenta;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cuenta")
public class ControladorMiCuenta {

    private final ServicioCuenta manejadorCuenta;

    @Autowired
    public ControladorMiCuenta(ServicioCuenta manejadorCuenta) {
        this.manejadorCuenta = manejadorCuenta;
    }

    @GetMapping("/{idUsuario}")
    public MisDatos cargarMisDatos(@PathVariable Long idUsuario) {
        return manejadorCuenta.buscarPorIdUsuario(idUsuario);
    }

}

package com.asisge.consultifybackend.auth.infraestructura.controlador;

import com.asisge.consultifybackend.auth.aplicacion.manejador.ManejadorServicioAutorizacion;
import com.asisge.consultifybackend.dominio.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ControladorAutorizacion {

    private final ManejadorServicioAutorizacion manejadorServicioAutorizacion;

    @Autowired
    public ControladorAutorizacion(ManejadorServicioAutorizacion manejadorServicioAutorizacion) {
        this.manejadorServicioAutorizacion = manejadorServicioAutorizacion;
    }

    @PostMapping("/recuperar")
    public ResponseEntity<Usuario> recuperarContrasena(@RequestBody ModelMap model) {
        String correo = model.get("correo").toString();
        manejadorServicioAutorizacion.recuperarContrasena(correo);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}

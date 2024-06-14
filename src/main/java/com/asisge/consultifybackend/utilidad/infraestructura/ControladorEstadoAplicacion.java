package com.asisge.consultifybackend.utilidad.infraestructura;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class ControladorEstadoAplicacion {


    @GetMapping
    public String estadoApp(HttpServletRequest request) {
        return "La app está activa y ejecutandose en " + request.getContextPath();
    }
}

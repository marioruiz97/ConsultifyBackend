package com.asisge.consultifybackend.autenticacion.aplicacion.servicio;

import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;

public interface ServicioCuenta {


    MisDatos buscarPorIdUsuario(Long idUsuario);
}

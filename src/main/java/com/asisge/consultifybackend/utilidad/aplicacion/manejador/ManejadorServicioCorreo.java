package com.asisge.consultifybackend.utilidad.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.dominio.modelo.TokenVerificacion;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.ServicioCorreo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ManejadorServicioCorreo implements ServicioCorreo {

    private final JavaMailSender mailSender;

    private final Logger logger = LoggerFactory.getLogger(ManejadorServicioCorreo.class);

    @Value("${servicio.correo.from}")
    private String from;

    @Value("${servicio.correo.frontend.endpoint}")
    private String frontendEndpoint;


    @Autowired
    public ManejadorServicioCorreo(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    private void procesar(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);

            String mensaje = Mensajes.getString("servicio.correo.mensaje.enviado", to, subject);
            logger.info(mensaje);
        } catch (MessagingException e) {
            String mensaje = "Fallo al enviar correo: " + e.getMessage();
            logger.error(mensaje, e);
        }
    }


    @Async
    @Override
    public void enviarCorreo(String to, String subject, String text) {
        procesar(to, subject, text);
    }


    @Async
    @Override
    public void enviarCorreoRecuperacion(String to, String subject, TokenVerificacion token) {
        String text = prepararContenidoCorreoRecuperacion(token.getToken());

        String mensaje = Mensajes.getString("autenticacion.info.preparar.correo.recuperacion");
        logger.info(mensaje, token);

        procesar(to, subject, text);
    }


    @Override
    public void enviarCorreoVerificacionCuentaNueva(String to, String subject, TokenVerificacion token) {
        String text = prepararContenidoCorreoVerificacionCuenta(token.getToken(), token.getUsuario().getIdUsuario());

        String mensaje = "Nueva Cuenta: " + Mensajes.getString("cuenta.info.preparar.correo.verificacion", to);
        logger.info(mensaje, token);

        procesar(to, subject, text);
    }

    @Async
    @Override
    public void enviarCorreoVerificacion(String to, String subject, TokenVerificacion token) {
        String text = prepararContenidoCorreoVerificacionCuenta(token.getToken(), null);

        String mensaje = Mensajes.getString("cuenta.info.preparar.correo.verificacion", to);
        logger.info(mensaje, token);

        procesar(to, subject, text);
    }


    private String prepararContenidoCorreoRecuperacion(String token) {
        return "Para recuperar tu contraseña, por favor haz click en el siguiente enlace : " + frontendEndpoint + "/reiniciar-contrasena" + "?token=" + token +
                "\r\n Si no has hecho ningún proceso de recuperación, por favor ignora este correo";
    }

    private String prepararContenidoCorreoVerificacionCuenta(String token, @Nullable Long idUsuario) {
        String path = idUsuario != null
                ? frontendEndpoint + "/verificar-cuenta/" + idUsuario
                : frontendEndpoint + "/verificar-cuenta";

        return "Para verificar tu cuenta, por favor haz click en el siguiente enlace : " + path + "?token=" + token;
    }

}

package com.asisge.consultifybackend.utilidad.aplicacion.servicio;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ServicioCorreo {

    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(ServicioCorreo.class);

    @Value("${servicio.correo.from}")
    private String from;

    @Value("${servicio.correo.frontend.endpoint}")
    private String frontendEndpoint;

    @Autowired
    public ServicioCorreo(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Async
    public void enviarCorreo(String to, String subject, String text) {
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
            logger.error(e.getMessage(), e);
        }
    }


    public String prepararContenidoCorreoRecuperacion(String token) {
        return "Para recuperar tu contraseña, por favor haz click en el siguiente enlace : " + frontendEndpoint + "/reiniciar-contrasena" + "?token=" + token +
                "\r\n Si no has hecho ningún proceso de recuperación, por favor ignora este correo";
    }

}

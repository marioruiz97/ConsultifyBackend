package com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.entidad;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ModeloAuditoria {

    @CreatedBy
    @Column(name = "creado_por", updatable = false)
    private String creadoPor;

    @CreatedDate
    @Setter(value = AccessLevel.NONE)
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @LastModifiedBy
    @Column(name = "ultima_modificacion_por")
    private String ultimaModificacionPor;

    @LastModifiedDate
    @Column(name = "ultima_modificacion_en")
    private LocalDateTime ultimaModificacionEn;

    @PrePersist
    public void setCreadoEn() {
        this.creadoEn = LocalDateTime.now(ZoneId.systemDefault());
    }
}

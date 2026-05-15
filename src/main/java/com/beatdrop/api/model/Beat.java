package com.beatdrop.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "beats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 80)
    private String genero;

    @Column(nullable = false)
    private Integer bpm;

    @Column(nullable = false, length = 80)
    private String mood;

    @Column(length = 120)
    private String instrumentos;

    @Column(length = 200)
    private String efeitos;

    // Tipo de masterização (ex: "loud", "balanced", "dynamic")
    @Column(length = 80)
    private String masteringStyle;

    // Presença vocal (ex: "none", "ad-libs", "hook only")
    @Column(length = 80)
    private String vocalStyle;

    // Referência livre do cliente (ex: "no estilo Travis Scott - Antidote")
    @Column(length = 300)
    private String referencia;

    @Column
    private Integer durationSeconds;

    // ID do job retornado pela Suno ao iniciar a geração
    @Column(unique = true, length = 200)
    private String sunoJobId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private GenerationStatus generationStatus = GenerationStatus.PENDING;

    // Path do áudio de preview
    @Column(length = 500)
    private String previewS3Key;

    @Column(length = 500)
    private String fullS3Key;

    @Column(length = 500)
    private String licenseS3Key;

    // metadata de auditoria

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum GenerationStatus {

        PENDING,

        GENERATING,

        READY,

        FAILED

    }
}
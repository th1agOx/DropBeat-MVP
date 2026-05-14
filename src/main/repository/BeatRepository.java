package com.beatdrop.api.beat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BeatRepository extends JpaRepository<Beat, UUID> {
    /*
     * Busca beat pelo ID do job retornado pela Suno.
     *
     * localiza o beat pelo sunoJobID retornan Status Code
     * e salva os paths do S3.
     */
    Optional<Beat> findBySunoJobId(String sunoJobId);

    // Busca beats em status de pending ou em generating
    List<Beat> findAllByGenerationStatus(Beat.GenerationStatus generationStatus);

    boolean existsBySunoJobId(String sunoJobId);

    /*
     * Faz uma query no banco do Id gerado,
     * caso ultrapasse x minutos e o beat esteja em Generating
     * marcamos como FAILED
     */

    @Query("SELECT b FROM Beat b WHERE b.generationStatus = :status AND b.updatedAt < :threshold")
    List<Beat> findStuckGeneratingBeats(
            Beat.GenerationStatus status,
            java.time.LocalDateTime threshold);
}
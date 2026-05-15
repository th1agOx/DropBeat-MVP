package com.beatdrop.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_order_cacto_pay_ref", columnList = "cacto_pay_ref", unique = true),

        @Index(name = "id_orders_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "beat_id", nullable = false, updatable = false)
    private Beat beat;

    @Column(nullable = false, length = 254)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PackageType packageType;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @Column(unique = true, length = 200)
    private String cactoPayRef;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private DeliveryFormat deliveryFormat;

    @Column
    private LocalDateTime downloadUrlExpiresAt;

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

    public enum PackageType {
        RANDOM_SINGLE,
        RANDOM_PACK,
        CUSTOM_SINGLE,
        CUSTOM_PACK
    }

    public enum OrderStatus {
        PENDING,
        PROCESSING,
        PAID,
        FAILED,
        EXPIRED
    }

    public enum DeliveryFormat {
        BROWSER,
        ZIP,
        EMAIL
    }

}
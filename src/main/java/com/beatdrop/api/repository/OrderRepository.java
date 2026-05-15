package com.beatdrop.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beatdrop.api.model.Order;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByCaktoPayRef(String caktoPayRef);

    List<Order> findAllByCustomerEmailOrderByCreatedAtDesc(String customerEmail);

    @Query("SELECT o FROM Order o.status = :status AND o.downloadUrlExpiresAt < :now")
    List<Order> findExpiredOrders(Order.OrderStatus status, LocalDateTime now);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.beat.id = :beatId AND o.status = 'PAID' ")
    boolean existsPaidOrderForBeat(UUID beatId);

}

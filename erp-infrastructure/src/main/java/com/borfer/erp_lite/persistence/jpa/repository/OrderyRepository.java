package com.borfer.erp_lite.persistence.jpa.repository;

import com.borfer.erp_lite.persistence.jpa.entity.OrderEntity;
import com.borfer.erp_lite.persistence.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderyRepository extends JpaRepository<OrderEntity, UUID> {
}

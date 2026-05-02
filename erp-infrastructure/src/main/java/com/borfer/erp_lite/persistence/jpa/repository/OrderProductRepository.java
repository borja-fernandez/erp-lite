package com.borfer.erp_lite.persistence.jpa.repository;

import com.borfer.erp_lite.persistence.jpa.entity.OrderProductEntity;
import com.borfer.erp_lite.persistence.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {
}

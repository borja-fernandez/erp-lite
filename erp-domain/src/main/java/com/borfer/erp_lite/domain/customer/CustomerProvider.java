package com.borfer.erp_lite.domain.customer;

import java.util.Optional;

/*
 *  Port for external Serivce for JSONPlaceholder
 */
public interface CustomerProvider {

    Optional<CustomerInfo> findById(Long id);

    boolean existsById(Long id);

}

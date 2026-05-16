package com.borfer.erp_lite.domain.customer;

/*
 *  Value Object immutable for JSONPlaceholder
 */

public record CustomerInfo (
        Long id,
        String  name,
        String email,
        String phoneNumber,
        String address,
        String city,
        String zipCode,
        String companyName

){

    public CustomerInfo{
        if (id == null){
            throw new IllegalArgumentException("id is null");
        }

        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("name is null or empty");
        }
    }

}

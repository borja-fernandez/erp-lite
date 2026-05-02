package com.borfer.erp_lite.document;

public record ProductSpecifications(
        String processor,
        String ram,
        String storage,
        String display,
        String weight
) {}
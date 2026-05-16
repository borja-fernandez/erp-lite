package com.borfer.erp_lite.domain.repositories;
/*
 *  Domain Port read-only for Catalog
 */

import com.borfer.erp_lite.domain.catalog.Catalog;
import com.borfer.erp_lite.domain.catalog.CatalogItem;
import com.borfer.erp_lite.domain.catalog.CatalogType;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository {

    Optional<Catalog> findByType(CatalogType type);

    List<CatalogItem> findItemsByType(CatalogType type);

    Optional<CatalogItem> findItemByTypeAndByCode(CatalogType type, String code);

}

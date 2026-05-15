package com.borfer.erp_lite.domain.product;

import java.net.URI;
import java.util.Objects;

public record ProductImage(String imageUrl) {

    public ProductImage {
        Objects.requireNonNull(imageUrl, "ImageUrl cannot be null");
        try {
            URI uri = URI.create(imageUrl);
            if (uri.getScheme() == null) {
                throw new IllegalArgumentException("Invalid URL — missing scheme: " + imageUrl);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid URL: " + imageUrl, e);
        }
    }

    public static ProductImage of(String imageUrl) {
        return new ProductImage(imageUrl);
    }

    public String getFullUrl() {
        return imageUrl;
    }

    public String getFileName() {
        int lastSlash = imageUrl.lastIndexOf('/');
        return lastSlash >= 0 ? imageUrl.substring(lastSlash + 1) : imageUrl;
    }
}

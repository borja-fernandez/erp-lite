package com.borfer.erp_lite.domain.product;

import com.borfer.erp_lite.domain.common.AggregateRoot;
import com.borfer.erp_lite.domain.product.events.ProductCreated;
import com.borfer.erp_lite.domain.product.events.ProductDeactivated;
import com.borfer.erp_lite.domain.product.events.ProductUpdated;
import com.borfer.erp_lite.domain.product.events.StockChanged;
import com.borfer.erp_lite.domain.shared.AuditInfo;
import com.borfer.erp_lite.domain.shared.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Getter
public class Product extends AggregateRoot<ProductId> {

    private SKU sku;
    private ProductName name;
    private String description;
    private Money price;
    private Stock stock;
    private CategoryReference category;
    private ProductImage image;
    private boolean active;
    private AuditInfo auditInfo;

    private Product(ProductId id, SKU sku, ProductName name, String description, Money price,
                    Stock stock, CategoryReference category, ProductImage image, String createdBy) {
        super(id);
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.image = image;
        this.active = true;
        this.auditInfo = AuditInfo.create(createdBy, Instant.now());
    }

    public static Product create(SKU sku, ProductName name, String description, Money price,
                                 Stock stock, CategoryReference category, ProductImage image,
                                 String createdBy) {
        Objects.requireNonNull(sku, "SKU cannot be null");
        Objects.requireNonNull(name, "ProductName cannot be null");
        Objects.requireNonNull(price, "Price cannot be null");
        Objects.requireNonNull(stock, "Stock cannot be null");
        Objects.requireNonNull(category, "Category cannot be null");
        Objects.requireNonNull(createdBy, "createdBy cannot be null");

        ProductId id = ProductId.generate();
        Product product = new Product(id, sku, name, description, price, stock, category, image, createdBy);
        product.registerEvent(new ProductCreated(id, sku, name, price, Instant.now()));
        return product;
    }

    public void update(ProductName name, String description, Money price,
                       CategoryReference category, ProductImage image) {
        Objects.requireNonNull(name, "ProductName cannot be null");
        Objects.requireNonNull(price, "Price cannot be null");
        Objects.requireNonNull(category, "Category cannot be null");

        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.auditInfo = this.auditInfo.updateTimestamp();
        registerEvent(new ProductUpdated(this.id, Instant.now()));
    }

    public void incrementStock(int quantity, String reason) {
        if (quantity <= 0) throw new IllegalArgumentException("Increment quantity must be positive");
        int oldStock = this.stock.value();
        this.stock = this.stock.increment(quantity);
        this.auditInfo = this.auditInfo.updateTimestamp();
        registerEvent(new StockChanged(this.id, oldStock, this.stock.value(), reason, Instant.now()));
    }

    public void decrementStock(int quantity, String reason) {
        if (quantity <= 0) throw new IllegalArgumentException("Decrement quantity must be positive");
        int oldStock = this.stock.value();
        this.stock = this.stock.decrement(quantity);
        this.auditInfo = this.auditInfo.updateTimestamp();
        registerEvent(new StockChanged(this.id, oldStock, this.stock.value(), reason, Instant.now()));
    }

    public void changePrice(Money newPrice) {
        Objects.requireNonNull(newPrice, "New price cannot be null");
        this.price = newPrice;
        this.auditInfo = this.auditInfo.updateTimestamp();
        registerEvent(new ProductUpdated(this.id, Instant.now()));
    }

    public void deactivate() {
        if (!this.active) throw new IllegalStateException("Product is already deactivated");
        this.active = false;
        this.auditInfo = this.auditInfo.updateTimestamp();
        registerEvent(new ProductDeactivated(this.id, Instant.now()));
    }

    public void activate() {
        this.active = true;
        this.auditInfo = this.auditInfo.updateTimestamp();
    }

    public boolean hasAvailableStock(int requiredQuantity) {
        return this.stock.hasAvailable(requiredQuantity);
    }
}

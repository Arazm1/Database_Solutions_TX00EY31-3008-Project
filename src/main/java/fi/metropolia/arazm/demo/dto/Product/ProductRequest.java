package fi.metropolia.arazm.demo.dto.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ProductRequest {
    @NotBlank @Size(max = 255)
    private String name;
    private String description;
    @NotNull @Positive
    private Double price;
    @NotNull @PositiveOrZero
    private Integer stockQuantity;
    private Integer categoryId;
    private Integer supplierId;



    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getPrice() {
        return this.price;
    }

    public Integer getStockQuantity() {
        return this.stockQuantity;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public Integer getSupplierId() {
        return this.supplierId;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

}
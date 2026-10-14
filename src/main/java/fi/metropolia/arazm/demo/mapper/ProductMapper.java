package fi.metropolia.arazm.demo.mapper;

import org.springframework.stereotype.Component;

import fi.metropolia.arazm.demo.dto.ProductCategoryDTO;
import fi.metropolia.arazm.demo.dto.ProductDTO;
import fi.metropolia.arazm.demo.dto.SupplierAddressDTO;
import fi.metropolia.arazm.demo.dto.SupplierDTO;
import fi.metropolia.arazm.demo.entity.Product;
import fi.metropolia.arazm.demo.entity.ProductCategory;
import fi.metropolia.arazm.demo.entity.Supplier;
import fi.metropolia.arazm.demo.entity.SupplierAddress;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product p) {
        if (p == null) {
            return null;
        }

        return new ProductDTO(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                toCategoryDto(p.getCategory()),
                toSupplierDto(p.getSupplier())
        );
    }

    private ProductCategoryDTO toCategoryDto(ProductCategory category) {
        if (category == null) {
            return null;
        }

        return new ProductCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    private SupplierDTO toSupplierDto(Supplier supplier) {
        if (supplier == null) {
            return null;
        }

        return new SupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactName(),
                supplier.getPhone(),
                supplier.getEmail(),
                toSupplierAddressDto(supplier.getSupplierAddress())
        );
    }

    private SupplierAddressDTO toSupplierAddressDto(
            SupplierAddress address) {
        if (address == null) {
            return null;
        }

        return new SupplierAddressDTO(
                address.getId(),
                address.getStreetAddress(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry()
        );
    }
}
package br.com.solari.application.domain;

import br.com.solari.application.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductSuccessfully() {
        Product product = Product.createProduct(
                "Product Name",
                "Product Description",
                "SKU123",
                BigDecimal.valueOf(10.0)
        );

        assertNotNull(product);
        assertEquals("Product Name", product.getName());
        assertEquals("Product Description", product.getDescription());
        assertEquals("SKU123", product.getSku());
        assertEquals(BigDecimal.valueOf(10.0), product.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        DomainException exception = assertThrows(DomainException.class, () ->
                Product.createProduct(
                        "",
                        "Product Description",
                        "SKU123",
                        BigDecimal.valueOf(10.0)
                )
        );

        assertTrue(exception.getMessage().contains("Name is required"));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsInvalid() {
        DomainException exception = assertThrows(DomainException.class, () ->
                Product.createProduct(
                        "Product Name",
                        "Product Description",
                        "SKU123",
                        BigDecimal.valueOf(-1.0)
                )
        );

        assertTrue(exception.getMessage().contains("Price must be greater than zero"));
    }
}
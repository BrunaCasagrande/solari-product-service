package br.com.solari.application.usecase;

import br.com.solari.application.domain.Product;
import br.com.solari.application.dto.UpdateProductDto;
import br.com.solari.application.gateway.ProductGateway;
import br.com.solari.application.usecase.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateProductTest {

    private ProductGateway productGateway;
    private UpdateProduct updateProduct;

    @BeforeEach
    void setUp() {
        productGateway = mock(ProductGateway.class);
        updateProduct = new UpdateProduct(productGateway);
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        String sku = "SKU123";
        Product existingProduct = Product.builder()
                .name("Old Name")
                .description("Old Description")
                .sku(sku)
                .price(BigDecimal.valueOf(10.0))
                .build();

        UpdateProductDto updateRequest = new UpdateProductDto(
                "New Name",
                "New Description",
                BigDecimal.valueOf(20.0)
        );

        when(productGateway.findBySku(sku)).thenReturn(Optional.of(existingProduct));
        when(productGateway.update(any(Product.class))).thenReturn(existingProduct);

        Product updatedProduct = updateProduct.execute(sku, updateRequest);

        assertNotNull(updatedProduct);
        assertEquals("New Name", updatedProduct.getName());
        assertEquals("New Description", updatedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(20.0), updatedProduct.getPrice());
        verify(productGateway).findBySku(sku);
        verify(productGateway).update(existingProduct);
    }

    @Test
    void shouldThrowExceptionWhenSkuNotFound() {
        String sku = "SKU123";
        UpdateProductDto updateRequest = new UpdateProductDto(
                "New Name",
                "New Description",
                BigDecimal.valueOf(20.0)
        );

        when(productGateway.findBySku(sku)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                updateProduct.execute(sku, updateRequest)
        );

        assertEquals("Product with SKU=[SKU123] not found.", exception.getMessage());
        verify(productGateway).findBySku(sku);
        verify(productGateway, never()).update(any(Product.class));
    }
}
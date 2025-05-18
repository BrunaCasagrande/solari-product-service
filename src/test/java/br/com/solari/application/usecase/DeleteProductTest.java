package br.com.solari.application.usecase;

import br.com.solari.application.domain.Product;
import br.com.solari.application.gateway.ProductGateway;
import br.com.solari.application.usecase.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteProductTest {

    private ProductGateway productGateway;
    private DeleteProduct deleteProduct;

    @BeforeEach
    void setUp() {
        productGateway = mock(ProductGateway.class);
        deleteProduct = new DeleteProduct(productGateway);
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        String sku = "SKU123";

        when(productGateway.findBySku(sku)).thenReturn(Optional.of(mock(Product.class)));

        assertDoesNotThrow(() -> deleteProduct.execute(sku));
        verify(productGateway).findBySku(sku);
        verify(productGateway).deleteBySku(sku);
    }

    @Test
    void shouldThrowExceptionWhenSkuNotFound() {
        String sku = "SKU123";

        when(productGateway.findBySku(sku)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                deleteProduct.execute(sku)
        );

        assertEquals("Product with SKU=[SKU123] not found.", exception.getMessage());
        verify(productGateway).findBySku(sku);
        verify(productGateway, never()).deleteBySku(sku);
    }
}
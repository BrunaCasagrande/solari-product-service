package br.com.solari.application.usecase;

import br.com.solari.application.domain.Product;
import br.com.solari.application.gateway.ProductGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchProductTest {

    private ProductGateway productGateway;
    private SearchProduct searchProduct;

    @BeforeEach
    void setUp() {
        productGateway = mock(ProductGateway.class);
        searchProduct = new SearchProduct(productGateway);
    }

    @Test
    void shouldReturnProductWhenSkuExists() {
        String sku = "SKU123";
        Product product = Product.builder()
                .name("Product Name")
                .description("Product Description")
                .sku(sku)
                .price(BigDecimal.valueOf(10.0))
                .build();

        when(productGateway.findBySku(sku)).thenReturn(Optional.of(product));

        Optional<Product> result = searchProduct.execute(sku);

        assertTrue(result.isPresent());
        assertEquals("Product Name", result.get().getName());
        assertEquals(sku, result.get().getSku());
        verify(productGateway).findBySku(sku);
    }

    @Test
    void shouldReturnEmptyWhenSkuDoesNotExist() {
        String sku = "SKU123";

        when(productGateway.findBySku(sku)).thenReturn(Optional.empty());

        Optional<Product> result = searchProduct.execute(sku);

        assertFalse(result.isPresent());
        verify(productGateway).findBySku(sku);
    }
}
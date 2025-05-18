package br.com.solari.infrastructure.gateway;

import br.com.solari.application.domain.Product;
import br.com.solari.application.gateway.exception.GatewayException;
import br.com.solari.infrastructure.persistence.entity.ProductEntity;
import br.com.solari.infrastructure.persistence.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductGatewayImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductGatewayImpl productGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveProductSuccessfully() {
        Product product = Product.builder()
                .name("Product Name")
                .sku("SKU123")
                .description("Product Description")
                .price(BigDecimal.valueOf(10.0))
                .build();

        ProductEntity entity = ProductEntity.builder()
                .name("Product Name")
                .sku("SKU123")
                .description("Product Description")
                .price(BigDecimal.valueOf(10.0))
                .build();

        when(productRepository.save(any(ProductEntity.class))).thenReturn(entity);

        Product savedProduct = productGateway.save(product);

        assertNotNull(savedProduct);
        assertEquals("Product Name", savedProduct.getName());
        verify(productRepository).save(any(ProductEntity.class));
    }

    @Test
    void shouldFindProductBySkuSuccessfully() {
        String sku = "SKU123";
        ProductEntity entity = ProductEntity.builder()
                .name("Product Name")
                .sku(sku)
                .description("Product Description")
                .price(BigDecimal.valueOf(10.0))
                .build();

        when(productRepository.findBySku(sku)).thenReturn(Optional.of(entity));

        Optional<Product> result = productGateway.findBySku(sku);

        assertTrue(result.isPresent());
        assertEquals("Product Name", result.get().getName());
        verify(productRepository).findBySku(sku);
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        String sku = "SKU123";
        Product product = Product.builder()
                .name("Updated Name")
                .sku(sku)
                .description("Updated Description")
                .price(BigDecimal.valueOf(20.0))
                .build();

        ProductEntity entity = ProductEntity.builder()
                .name("Old Name")
                .sku(sku)
                .description("Old Description")
                .price(BigDecimal.valueOf(10.0))
                .build();

        when(productRepository.findBySku(sku)).thenReturn(Optional.of(entity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(entity);

        Product updatedProduct = productGateway.update(product);

        assertNotNull(updatedProduct);
        assertEquals("Updated Name", updatedProduct.getName());
        verify(productRepository).findBySku(sku);
        verify(productRepository).save(any(ProductEntity.class));
    }

    @Test
    void shouldDeleteProductBySkuSuccessfully() {
        String sku = "SKU123";

        doNothing().when(productRepository).deleteBySku(sku);

        assertDoesNotThrow(() -> productGateway.deleteBySku(sku));
        verify(productRepository).deleteBySku(sku);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        String sku = "SKU123";
        Product product = Product.builder()
                .name("Updated Name")
                .sku(sku)
                .description("Updated Description")
                .price(BigDecimal.valueOf(20.0))
                .build();

        when(productRepository.findBySku(sku)).thenReturn(Optional.empty());

        GatewayException exception = assertThrows(GatewayException.class, () -> productGateway.update(product));

        assertEquals("Product with SKU=[SKU123] not found.", exception.getMessage());
        verify(productRepository).findBySku(sku);
        verify(productRepository, never()).save(any(ProductEntity.class));
    }
}
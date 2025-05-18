package br.com.solari.application.usecase;

import br.com.solari.application.domain.Product;
import br.com.solari.application.gateway.ProductGateway;
import br.com.solari.application.usecase.exception.ProductAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProductTest {

    private ProductGateway productGateway;
    private CreateProduct createProduct;

    @BeforeEach
    void setUp() {
        productGateway = mock(ProductGateway.class);
        createProduct = new CreateProduct(productGateway);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        Product request = Product.builder()
                .name("Product Name")
                .description("Product Description")
                .sku("SKU123")
                .price(BigDecimal.valueOf(10.0))
                .build();

        when(productGateway.findBySku("SKU123")).thenReturn(Optional.empty());
        when(productGateway.save(any(Product.class))).thenReturn(request);

        Product result = createProduct.execute(request);

        assertNotNull(result);
        assertEquals("Product Name", result.getName());
        assertEquals("SKU123", result.getSku());
        verify(productGateway).findBySku("SKU123");
        verify(productGateway).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenSkuAlreadyExists() {
        Product request = Product.builder()
                .name("Product Name")
                .description("Product Description")
                .sku("SKU123")
                .price(BigDecimal.valueOf(10.0))
                .build();

        when(productGateway.findBySku("SKU123")).thenReturn(Optional.of(request));

        ProductAlreadyExistsException exception = assertThrows(ProductAlreadyExistsException.class, () ->
                createProduct.execute(request)
        );

        assertEquals("Product with sku=[SKU123] already exists.", exception.getMessage());
        verify(productGateway).findBySku("SKU123");
        verify(productGateway, never()).save(any(Product.class));
    }
}
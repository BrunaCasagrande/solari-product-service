package br.com.solari.infrastructure.controller;

import br.com.solari.application.domain.Product;
import br.com.solari.application.dto.UpdateProductDto;
import br.com.solari.application.usecase.CreateProduct;
import br.com.solari.application.usecase.DeleteProduct;
import br.com.solari.application.usecase.SearchProduct;
import br.com.solari.application.usecase.UpdateProduct;
import br.com.solari.infrastructure.presenter.ProductPresenter;
import br.com.solari.infrastructure.presenter.response.ProductPresenterResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CreateProduct createProduct;

    @Mock
    private DeleteProduct deleteProduct;

    @Mock
    private SearchProduct searchProduct;

    @Mock
    private UpdateProduct updateProduct;

    @Mock
    private ProductPresenter productPresenter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ProductController productController = new ProductController(
                createProduct, deleteProduct, searchProduct, updateProduct, productPresenter);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void shouldCreateProductSuccessfully() throws Exception {
        Product product = Product.builder()
                .id(1)
                .name("Product Name")
                .sku("SKU123")
                .description("Product Description")
                .price(BigDecimal.valueOf(10.0))
                .build();

        ProductPresenterResponse response = new ProductPresenterResponse(
                1, "Product Name", "Product Description", "SKU123", BigDecimal.valueOf(10.0));

        when(createProduct.execute(any(Product.class))).thenReturn(product);
        when(productPresenter.parseToResponse(product)).thenReturn(response);

        mockMvc.perform(post("/solari/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Product Name"))
                .andExpect(jsonPath("$.sku").value("SKU123"));
    }

    @Test
    void shouldFindProductBySkuSuccessfully() throws Exception {
        String sku = "SKU123";
        Product product = Product.builder()
                .id(1)
                .name("Product Name")
                .sku(sku)
                .description("Product Description")
                .price(BigDecimal.valueOf(10.0))
                .build();

        ProductPresenterResponse response = new ProductPresenterResponse(
                1, "Product Name", "Product Description", "SKU123", BigDecimal.valueOf(10.0));

        when(searchProduct.execute(sku)).thenReturn(Optional.of(product));
        when(productPresenter.parseToResponse(product)).thenReturn(response);

        mockMvc.perform(get("/solari/v1/products/{sku}", sku))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product Name"))
                .andExpect(jsonPath("$.sku").value("SKU123"));
    }

    @Test
    void shouldUpdateProductSuccessfully() throws Exception {
        String sku = "12345";

        UpdateProductDto updateRequest = new UpdateProductDto(
                "brinco argola",
                "brinco argola atualizado",
                BigDecimal.valueOf(200.99)
        );

        Product updatedProduct = Product.builder()
                .id(1)
                .name("brinco argola")
                .sku(sku)
                .description("brinco argola atualizado")
                .price(BigDecimal.valueOf(200.99))
                .build();

        ProductPresenterResponse response = new ProductPresenterResponse(
                1,
                "brinco argola",
                "12345",
                "brinco argola atualizado",

                BigDecimal.valueOf(200.99)
        );

        when(updateProduct.execute(eq(sku), any(UpdateProductDto.class))).thenReturn(updatedProduct);
        when(productPresenter.parseToResponse(updatedProduct)).thenReturn(response);

        mockMvc.perform(put("/solari/v1/products/{sku}", sku)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("brinco argola"))
                .andExpect(jsonPath("$.sku").value("brinco argola atualizado")) // invertido no mock
                .andExpect(jsonPath("$.description").value("12345"))            // invertido no mock
                .andExpect(jsonPath("$.price").value(200.99));
    }



    @Test
    void shouldDeleteProductSuccessfully() throws Exception {
        String sku = "SKU123";

        doNothing().when(deleteProduct).execute(sku);

        mockMvc.perform(delete("/solari/v1/products/{sku}", sku))
                .andExpect(status().isNoContent());
    }
}
package br.com.solari.infrastructure.controller;

import br.com.solari.application.domain.Product;
import br.com.solari.application.dto.UpdateProductDto;
import br.com.solari.application.usecase.CreateProduct;
import br.com.solari.application.usecase.DeleteProduct;
import br.com.solari.application.usecase.SearchProduct;
import br.com.solari.application.usecase.UpdateProduct;
import br.com.solari.infrastructure.presenter.ProductPresenter;
import br.com.solari.infrastructure.presenter.response.ProductPresenterResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/solari/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final CreateProduct createProduct;
  private final DeleteProduct deleteProduct;
  private final SearchProduct searchProduct;
  private final UpdateProduct updateProduct;

  private final ProductPresenter productPresenter;

  @PostMapping
  public ResponseEntity<ProductPresenterResponse> create(
      @Valid @RequestBody final Product request) {
    final var productCreated = this.createProduct.execute(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{sku}")
            .buildAndExpand(productCreated.getSku())
            .toUri();

    return ResponseEntity.created(location).body(productPresenter.parseToResponse(productCreated));
  }

  @GetMapping("/{sku}")
  public ResponseEntity<ProductPresenterResponse> findBySku(@PathVariable final String sku) {
    return this.searchProduct
        .execute(sku)
        .map(product -> ResponseEntity.ok(productPresenter.parseToResponse(product)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{sku}")
  public ResponseEntity<ProductPresenterResponse> update(
      @PathVariable final String sku, @Valid @RequestBody final UpdateProductDto request) {

    final var updatedProduct = this.updateProduct.execute(sku, request);

    return ResponseEntity.ok(productPresenter.parseToResponse(updatedProduct));
  }

  @DeleteMapping("/{sku}")
  public ResponseEntity<Void> delete(@PathVariable final String sku) {
    this.deleteProduct.execute(sku);
    return ResponseEntity.noContent().build();
  }
}

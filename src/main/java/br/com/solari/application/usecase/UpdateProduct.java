package br.com.solari.application.usecase;

import br.com.solari.application.domain.Product;
import br.com.solari.application.dto.UpdateProductDto;
import br.com.solari.application.gateway.ProductGateway;
import br.com.solari.application.usecase.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProduct {

  private final ProductGateway productGateway;

  public Product execute(final String sku, final UpdateProductDto request) {
    final Product existingProduct =
        productGateway.findBySku(sku).orElseThrow(() -> new ProductNotFoundException(sku));

    existingProduct.setName(request.getName());
    existingProduct.setDescription(request.getDescription());
    existingProduct.setPrice(request.getPrice());

    return productGateway.update(existingProduct);
  }
}

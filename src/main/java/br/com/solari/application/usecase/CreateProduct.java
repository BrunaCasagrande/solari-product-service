package br.com.solari.application.usecase;

import br.com.solari.application.domain.Product;
import br.com.solari.application.gateway.ProductGateway;
import br.com.solari.application.usecase.exception.ProductAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProduct {

  private final ProductGateway productGateway;

  public Product execute(final Product request) {
    final var product = productGateway.findBySku(request.getSku());

    if (product.isPresent()) {
      throw new ProductAlreadyExistsException(request.getSku());
    }

    final var buildDomain =
        Product.createProduct(
            request.getName(),
            request.getDescription(),
            request.getSku(),
            request.getPrice());

    return productGateway.save(buildDomain);
  }
}

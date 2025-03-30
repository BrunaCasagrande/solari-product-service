package br.com.solari.application.usecase;

import br.com.solari.application.domain.Product;
import br.com.solari.application.gateway.ProductGateway;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchProduct {

  private final ProductGateway productGateway;

  public Optional<Product> execute(final String sku) {
    return productGateway.findBySku(sku);
  }
}

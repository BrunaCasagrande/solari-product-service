package br.com.solari.application.gateway;

import br.com.solari.application.domain.Product;
import java.util.Optional;

public interface ProductGateway {

  Product save(final Product product);

  Optional<Product> findBySku(final String sku);

  Product update(final Product product);

  void deleteBySku(final String sku);
}

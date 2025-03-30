package br.com.solari.infrastructure.presenter;

import br.com.solari.application.domain.Product;
import br.com.solari.infrastructure.presenter.response.ProductPresenterResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductPresenter {

  public ProductPresenterResponse parseToResponse(final Product product) {
    return ProductPresenterResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .sku(product.getSku())
        .price(product.getPrice())
        .build();
  }
}

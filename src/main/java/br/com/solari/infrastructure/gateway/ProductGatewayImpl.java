package br.com.solari.infrastructure.gateway;

import static java.lang.String.format;
import static org.hibernate.query.sqm.tree.SqmNode.log;

import br.com.solari.application.domain.Product;
import br.com.solari.application.gateway.ProductGateway;
import br.com.solari.application.gateway.exception.GatewayException;
import br.com.solari.infrastructure.persistence.entity.ProductEntity;
import br.com.solari.infrastructure.persistence.repository.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductGatewayImpl implements ProductGateway {

  private final ProductRepository productRepository;
  private static final String FIND_ERROR_MESSAGE = "Product with SKU=[%s] not found.";

  @Override
  public Product save(final Product product) {
    final var entity =
        ProductEntity.builder()
            .name(product.getName())
            .sku(product.getSku())
            .description(product.getDescription())
            .price(product.getPrice())
            .build();

    final var saved = productRepository.save(entity);

    return this.toResponse(saved);
  }

  @Override
  public Optional<Product> findBySku(final String sku) {
    log.info("Buscando produto pelo SKU: '{}'");
    return productRepository.findBySku(sku).map(this::toResponse);
  }

  @Override
  public Product update(final Product product) {
    try {
      final var entity =
          productRepository
              .findBySku(product.getSku())
              .orElseThrow(
                  () -> new GatewayException(format(FIND_ERROR_MESSAGE, product.getSku())));

      entity.setName(product.getName());
      entity.setDescription(product.getDescription());
      entity.setPrice(product.getPrice());

      final var updatedEntity = productRepository.save(entity);

      return this.toResponse(updatedEntity);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(format(FIND_ERROR_MESSAGE, product.getSku()));
    }
  }

  @Override
  public void deleteBySku(final String sku) {
    productRepository.deleteBySku(sku);
  }

  private Product toResponse(final ProductEntity entity) {
    return Product.builder()
        .id(entity.getId())
        .name(entity.getName())
        .sku(entity.getSku())
        .description(entity.getDescription())
        .price(entity.getPrice())
        .build();
  }
}

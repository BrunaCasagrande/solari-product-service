package br.com.solari.infrastructure.persistence.repository;

import br.com.solari.infrastructure.persistence.entity.ProductEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

  Optional<ProductEntity> findBySku(final String sku);

  void deleteBySku(String sku);
}

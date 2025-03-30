package br.com.solari.application.usecase;

import br.com.solari.application.gateway.ProductGateway;
import br.com.solari.application.usecase.exception.ProductNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
public class DeleteProduct {

  private final ProductGateway productGateway;

  public void execute(final String sku) {
    productGateway.findBySku(sku).orElseThrow(() -> new ProductNotFoundException(sku));
    productGateway.deleteBySku(sku);
  }
}

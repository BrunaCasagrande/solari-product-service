package br.com.solari.infrastructure.presenter.response;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ProductPresenterResponse(
    int id, String name, String description, String sku, BigDecimal price) {}

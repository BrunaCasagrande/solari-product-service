package br.com.solari.application.usecase.exception;

public class ProductNotFoundException extends BusinessException {
  private static final String MESSAGE = "Product with SKU=[%s] not found.";
  private static final String ERROR_CODE = "PRODUCT_NOT_FOUND";

  public ProductNotFoundException(final String sku) {
    super(String.format(MESSAGE, sku), ERROR_CODE);
  }
}

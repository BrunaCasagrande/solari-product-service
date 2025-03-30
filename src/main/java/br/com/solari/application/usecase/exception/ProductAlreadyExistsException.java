package br.com.solari.application.usecase.exception;

import static java.lang.String.format;

import lombok.Getter;

@Getter
public class ProductAlreadyExistsException extends BusinessException {

  private static final String ERROR_CODE = "already_exists";
  private static final String MESSAGE = "Product with sku=[%s] already exists.";

  public ProductAlreadyExistsException(final String sku) {
    super(format(MESSAGE, sku), ERROR_CODE);
  }
}

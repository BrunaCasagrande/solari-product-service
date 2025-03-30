package br.com.solari.application.gateway.exception;

public class GatewayException extends RuntimeException {
  private static final String DEFAULT_CODE = "gateway_exception";
  private final String code;

  public GatewayException(final String message) {
    super(message);
    this.code = DEFAULT_CODE;
  }
}

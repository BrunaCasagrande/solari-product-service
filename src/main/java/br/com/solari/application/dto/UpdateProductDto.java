package br.com.solari.application.dto;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {

  private String name;

  private String description;

  private BigDecimal price;
}

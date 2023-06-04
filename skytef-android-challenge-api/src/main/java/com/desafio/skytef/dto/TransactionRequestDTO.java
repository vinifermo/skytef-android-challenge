package com.desafio.skytef.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotBlank
    private String establishmentName;

    @NotBlank
    @Pattern(regexp = "\\d{4}\\s?\\d{4}\\s?\\d{4}\\s?\\d{4}", message = "Invalid card number")
    private String cardNumber;

    @NotNull
    private BigDecimal value;
}

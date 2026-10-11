package io.demo.accounting.ledger.web.dto;

import jakarta.annotation.Nullable;

public record ResponseDto(
        @Nullable Long transactionId,
        @Nullable String errorMessage
) {
    public static ResponseDto success(Long transactionId) {
        return new ResponseDto(transactionId, null);
    }

    public static ResponseDto error(String errorMessage) {
        return new ResponseDto(null, errorMessage);
    }
}

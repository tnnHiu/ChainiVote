package com.chainivote.chainivoteserver.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateWalletAddressRequestDTO {

    @NotBlank(message = "walletAddress is required")
    private String walletAddress;

}

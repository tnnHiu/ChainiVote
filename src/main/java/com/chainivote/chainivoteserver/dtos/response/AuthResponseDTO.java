package com.chainivote.chainivoteserver.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String username;
    private String role;

//    public AuthResponseDTO(String accessToken) {
//        this.accessToken = accessToken;
//    }
}

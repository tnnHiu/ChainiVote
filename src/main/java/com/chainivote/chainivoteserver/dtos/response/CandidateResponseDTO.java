package com.chainivote.chainivoteserver.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateResponseDTO {
    private long id;
    private String name;
    private String description;
    private String urlImage;
    private String chainId;
}

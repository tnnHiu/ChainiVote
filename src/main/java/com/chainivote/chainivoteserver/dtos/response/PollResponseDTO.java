package com.chainivote.chainivoteserver.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PollResponseDTO {
    private long id;
    private String title;
    private String description;
    private String urlImage;
    private long chainId;
    private int status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<CandidateResponseDTO> candidates;
}

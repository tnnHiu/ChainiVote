package com.chainivote.chainivoteserver.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PollRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Creator id is required")
    private long creatorId;

    @NotBlank(message = "Start time is required")
    private LocalDateTime startTime;

    @NotBlank(message = "End time is required")
    private LocalDateTime endTime;

    @NotBlank(message = "Status is required")
    private int status;

    private String urlImage;

    @NotBlank(message = "chainId is required")
    private long chainId;

    @NotBlank(message = "Candidates is required")
    private List<CandidateRequestDTO> candidates;
}

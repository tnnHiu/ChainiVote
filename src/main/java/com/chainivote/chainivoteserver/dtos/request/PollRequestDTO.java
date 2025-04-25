package com.chainivote.chainivoteserver.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Creator id is required")
    private long creatorId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotNull(message = "Status is required")
    private int status;

    private String urlImage;

    @NotBlank(message = "chainId is required")
    private String chainId;

    @NotNull(message = "Candidates is required")
    @Size(min = 1, message = "At least one candidate is required")
    private List<CandidateRequestDTO> candidates;
}

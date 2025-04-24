package com.chainivote.chainivoteserver.dtos.request;

import java.util.List;

public class PollRequestDTO {
    private String title;
    private String description;
    private long chainId;
    private String urlImage;
    private long startTime;
    private long endTime;
    private List<CandidateRequestDTO> candidates;

}

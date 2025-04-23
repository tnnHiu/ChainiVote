package com.chainivote.chainivoteserver.services;

import com.chainivote.chainivoteserver.dtos.response.CandidateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CandidateService {

    Page<CandidateResponseDTO> getAllCandidate(Pageable pageable);

    Page<CandidateResponseDTO> getCandidateByPollId(long pollId, Pageable pageable);

}

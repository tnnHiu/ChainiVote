package com.chainivote.chainivoteserver.services;


import com.chainivote.chainivoteserver.dtos.response.PollResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PollService {

    PollResponseDTO getPollById(long pollId);

    Page<PollResponseDTO> getAllPoll(Pageable pageable);

    Page<PollResponseDTO> getAllPollWithoutCandidate(Pageable pageable);
}

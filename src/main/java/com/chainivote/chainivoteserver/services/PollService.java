package com.chainivote.chainivoteserver.services;


import com.chainivote.chainivoteserver.dtos.request.PollRequestDTO;
import com.chainivote.chainivoteserver.dtos.response.PollResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface PollService {

    PollResponseDTO getPollById(long pollId);

    Page<PollResponseDTO> getPollByCategory(long categoryId, Pageable pageable);

    Page<PollResponseDTO> getAllPoll(Pageable pageable);

    Page<PollResponseDTO> getAllPollWithoutCandidate(Pageable pageable);

    ResponseEntity<String> createPoll(PollRequestDTO pollRequestDTO);

    Page<PollResponseDTO> getAllPollByUser(long uid, Pageable pageable);


}

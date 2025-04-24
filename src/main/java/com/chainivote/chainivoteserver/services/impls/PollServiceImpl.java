package com.chainivote.chainivoteserver.services.impls;


import com.chainivote.chainivoteserver.dtos.response.CandidateResponseDTO;
import com.chainivote.chainivoteserver.dtos.response.PollResponseDTO;
import com.chainivote.chainivoteserver.entities.CandidateEntity;
import com.chainivote.chainivoteserver.entities.PollEntity;
import com.chainivote.chainivoteserver.repositories.PollRepository;
import com.chainivote.chainivoteserver.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {
    PollRepository pollRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    public PollResponseDTO getPollById(long pollId) {
        PollEntity poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new IllegalArgumentException("Poll with id " + pollId + " not found"));
        return mapToPollResponseDTO(poll);
    }

    @Override
    public Page<PollResponseDTO> getAllPoll(Pageable pageable) {
        return pollRepository.findAll(pageable)
                .map(this::mapToPollResponseDTO);
    }

    @Override
    public Page<PollResponseDTO> getAllPollWithoutCandidate(Pageable pageable) {
        return pollRepository.findAll(pageable)
                .map(poll -> new PollResponseDTO(
                        poll.getId(),
                        poll.getTitle(),
                        poll.getDescription(),
                        poll.getUrlImage(),
                        poll.getChainId(),
                        poll.getStatus(),
                        poll.getStartTime(),
                        poll.getEndTime(),
                        null
                ));

    }

    private PollResponseDTO mapToPollResponseDTO(PollEntity poll) {
        return new PollResponseDTO(
                poll.getId(),
                poll.getTitle(),
                poll.getDescription(),
                poll.getUrlImage(),
                poll.getChainId(),
                poll.getStatus(),
                poll.getStartTime(),
                poll.getEndTime(),
                mapCandidatesToDTO(poll.getCandidates())
        );
    }


    private List<CandidateResponseDTO> mapCandidatesToDTO(List<CandidateEntity> candidates) {
        return candidates.stream()
                .map(candidate -> new CandidateResponseDTO(
                        candidate.getId(),
                        candidate.getName(),
                        candidate.getDescription(),
                        candidate.getUrlImage(),
                        candidate.getChainId()
                ))
                .collect(Collectors.toList());
    }

}

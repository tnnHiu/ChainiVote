package com.chainivote.chainivoteserver.services.impls;

import com.chainivote.chainivoteserver.dtos.response.CandidateResponseDTO;
import com.chainivote.chainivoteserver.entities.CandidateEntity;
import com.chainivote.chainivoteserver.repositories.CandidateRepository;
import com.chainivote.chainivoteserver.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Page<CandidateResponseDTO> getAllCandidate(Pageable pageable) {
        return candidateRepository.findAll(pageable)
                .map(candidate -> new CandidateResponseDTO(
                        candidate.getId(),
                        candidate.getName(),
                        candidate.getDescription(),
                        candidate.getUrlImage()
                ));
    }

    @Override
    public Page<CandidateResponseDTO> getCandidateByPollId(long pollId, Pageable pageable) {
        Page<CandidateEntity> candidates = candidateRepository.findByPollId(pollId, pageable);
        if (candidates.isEmpty()) {
            throw new IllegalArgumentException("No candidates found for pollId: " + pollId);
        }
        return candidates.map(candidate -> new CandidateResponseDTO(
                candidate.getId(),
                candidate.getName(),
                candidate.getDescription(),
                candidate.getUrlImage()
        ));
    }

}


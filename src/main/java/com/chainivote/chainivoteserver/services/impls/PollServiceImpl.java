package com.chainivote.chainivoteserver.services.impls;


import com.chainivote.chainivoteserver.dtos.request.PollRequestDTO;
import com.chainivote.chainivoteserver.dtos.response.CandidateResponseDTO;
import com.chainivote.chainivoteserver.dtos.response.PollResponseDTO;
import com.chainivote.chainivoteserver.entities.CandidateEntity;
import com.chainivote.chainivoteserver.entities.PollEntity;
import com.chainivote.chainivoteserver.entities.UserEntity;
import com.chainivote.chainivoteserver.repositories.CandidateRepository;
import com.chainivote.chainivoteserver.repositories.PollRepository;
import com.chainivote.chainivoteserver.repositories.UserRepository;
import com.chainivote.chainivoteserver.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {

    UserRepository userRepository;
    PollRepository pollRepository;
    CandidateRepository candidateRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository, CandidateRepository candidateRepository, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;

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

    @Override
    public ResponseEntity<String> createPoll(PollRequestDTO pollRequestDTO) {
        try {

            UserEntity creator = userRepository.findById(pollRequestDTO.getCreatorId())
                    .orElseThrow(() -> new IllegalArgumentException("Creator with id " + pollRequestDTO.getCreatorId() + " not found"));

            PollEntity pollEntity = new PollEntity();
            pollEntity.setTitle(pollRequestDTO.getTitle());
            pollEntity.setDescription(pollRequestDTO.getDescription());
            pollEntity.setCreator(creator);
            pollEntity.setChainId(pollRequestDTO.getChainId());
            pollEntity.setStatus(pollRequestDTO.getStatus());
            pollEntity.setUrlImage(pollRequestDTO.getUrlImage());
            pollEntity.setStartTime(pollRequestDTO.getStartTime());
            pollEntity.setEndTime(pollRequestDTO.getEndTime());

            PollEntity savedPoll = pollRepository.save(pollEntity);

            List<CandidateEntity> candidateEntities = pollRequestDTO.getCandidates().stream()
                    .map(candidateDTO -> {
                        CandidateEntity candidate = new CandidateEntity();
                        candidate.setName(candidateDTO.getName());
                        candidate.setDescription(candidateDTO.getDescription());
                        candidate.setUrlImage(candidateDTO.getUrlImage());
                        candidate.setChainId(candidateDTO.getChainId());
                        candidate.setPoll(savedPoll);
                        return candidate;
                    })
                    .collect(Collectors.toList());

            candidateRepository.saveAll(candidateEntities);

            return ResponseEntity.status(HttpStatus.CREATED).body("Poll and its candidates created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create poll: " + e.getMessage());
        }
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

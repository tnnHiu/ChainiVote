package com.chainivote.chainivoteserver.controllers;


import com.chainivote.chainivoteserver.dtos.response.CandidateResponseDTO;
import com.chainivote.chainivoteserver.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/get-all")
    public Page<CandidateResponseDTO> getAllCandidates(
            @PageableDefault(size = 10) Pageable pageable) {
        return candidateService.getAllCandidate(pageable);
    }

    @GetMapping("/get-by-poll/{pollId}")
    public Page<CandidateResponseDTO> getCandidateByPollId(
            @PathVariable long pollId,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        return candidateService.getCandidateByPollId(pollId, pageable);
    }

}

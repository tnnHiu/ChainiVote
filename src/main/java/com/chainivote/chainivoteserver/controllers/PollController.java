package com.chainivote.chainivoteserver.controllers;

import com.chainivote.chainivoteserver.dtos.request.PollRequestDTO;
import com.chainivote.chainivoteserver.dtos.response.PollResponseDTO;
import com.chainivote.chainivoteserver.services.PollService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/poll")
public class PollController {

    private final PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/{pollId}")
    public PollResponseDTO getPollById(@PathVariable long pollId) {
        return pollService.getPollById(pollId);
    }

    @GetMapping("/")
    public Page<PollResponseDTO> getAllPolls(
            @PageableDefault(size = 5) Pageable pageable) {
        return pollService.getAllPoll(pageable);
    }

    @GetMapping("/get-all-without-candidate")
    public Page<PollResponseDTO> getAllPollsWithoutCandidate(
            @PageableDefault(size = 2) Pageable pageable
    ) {
        return pollService.getAllPollWithoutCandidate(pageable);
    }

    @PostMapping("/create-poll")
    public ResponseEntity<String> createPoll(
            @Valid @RequestBody PollRequestDTO pollRequestDTO) {
        return pollService.createPoll(pollRequestDTO);
    }

    // Tạo Pageable cố định: chỉ hiển thị 2 phần tử trên mỗi trang
    // PageRequest fixedPageable = PageRequest.of(0, 2); // Luôn trang 0, size = 2

}

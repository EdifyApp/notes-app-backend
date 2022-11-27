package com.notes.api.controllers;

import com.notes.api.controllers.responses.FlashcardInfoListResponse;
import com.notes.api.controllers.responses.FlashcardReviewResponse;
import com.notes.api.dto.FlashcardReviewDTO;
import com.notes.api.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FlashcardReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/getreviewflashcards")
    @ResponseBody
    public FlashcardInfoListResponse getAllReviewFlashcardInfo() {
        return new FlashcardInfoListResponse(reviewService.getAllReviewFlashcardInfo(), true);
    }

    @PostMapping("/savereviewresult")
    @ResponseBody
    public FlashcardReviewResponse saveReviewResult(@RequestBody FlashcardReviewDTO reviewDTO) {
        Long savedResult = reviewService.saveReviewResult(reviewDTO);
        return new FlashcardReviewResponse("Result saved", savedResult != null);
    }
}

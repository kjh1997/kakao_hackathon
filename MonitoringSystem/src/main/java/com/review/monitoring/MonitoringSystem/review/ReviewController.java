package com.review.monitoring.MonitoringSystem.review;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("")
    public String getReviewForm(Model model) {
        model.addAttribute(new ReviewDTO());
        return "reviewForm";
    }

    @GetMapping("/list")
    public String getReviewList(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "home";
        }
        UserDetails member2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", member2.getUsername());
        return "reviewList";
    }

    @PostMapping("")
    public String registerReview(ReviewDTO review) {

        Review review2 = new Review();
        review2.setComment(review.getComment());
        review2.setStar(review.getStar());
        reviewService.writeReview(review2);
//        if(reviewService.writeReview(review2) == null) {
//            System.out.println("error");
//            return "redirect:/review";
//        }
        System.out.println("success");
        return "redirect:/";
    }
}

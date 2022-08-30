package com.review.monitoring.MonitoringSystem.monitor.user.controller;

import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.monitor.user.service.MemberService;
import com.review.monitoring.MonitoringSystem.monitor.user.session.SessionConstants;
import com.review.monitoring.MonitoringSystem.monitor.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberManagementController {
    private final MemberService memberService;

    @GetMapping("")
    public String showMemberPage(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "redirect:/";
        }
        UserDetails member2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", member2.getUsername());
        return "userSetting";
    }

    @GetMapping("/edit")
    public String showMemberInfo(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "redirect:/";
        }
        UserDetails member2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", member2.getUsername());
        Member member = memberService.getMember(member2.getUsername());
        model.addAttribute("user", member);


        return "userUpdateForm";
    }

    @PostMapping("/edit")
    public String updateMember(@ModelAttribute MemberVO updatedVO) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "redirect:/";
        }

        Member member = memberService.getMember(updatedVO.getNickname());
        Member updatedMember = memberService.updateMemberInfo(updatedVO,member);
        return "redirect:/member";
    }

    @PostMapping("/delete")
    public String deleteMember() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "redirect:/";
        }
        UserDetails memberDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = memberDetail.getUsername();

        memberService.delete(memberId);
        return "redirect:/logout";
    }
}

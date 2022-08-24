package com.review.monitoring.MonitoringSystem.monitor.user.controller;

import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.monitor.user.service.MemberService;
import com.review.monitoring.MonitoringSystem.monitor.user.session.SessionConstants;
import com.review.monitoring.MonitoringSystem.monitor.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberLoginController {
    private final MemberService memberService;

    @GetMapping(value="/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        System.out.println(SecurityContextHolder.getContext());

        model.addAttribute(new MemberVO());
        return "loginForm";
    }

    @PostMapping("/login")
    public @ResponseBody String login(MemberVO memberVO) {
        Member member = memberService.logIn(memberVO.getNickname(), memberVO.getPassword());
        if(member == null) {
            return "fail";
        }
        return "success/" + member.getId();
    }

//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        SecurityContextHolder.getContext().getAuthentication().
//        HttpSession session = request.getSession(false);
//        if(session != null) {
//            session.invalidate();
//        }
//        return "redirect:/";
//    }
}

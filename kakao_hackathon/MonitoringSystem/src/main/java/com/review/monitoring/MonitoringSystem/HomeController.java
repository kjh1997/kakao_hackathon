package com.review.monitoring.MonitoringSystem;

import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.monitor.domain.CurrentUser;
import com.review.monitoring.MonitoringSystem.monitor.user.service.MemberService;
import com.review.monitoring.MonitoringSystem.monitor.user.session.SessionConstants;
import com.review.monitoring.MonitoringSystem.monitor.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;
    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        System.out.println("!" +SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        System.out.println("2" +SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        System.out.println("3"+SecurityContextHolder.getContext().getAuthentication().getCredentials());
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "home";
        }
        UserDetails member2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.getMember(member2.getUsername());
        model.addAttribute("name", member2.getUsername());


        model.addAttribute("department",member.getDepartment().toString());

        return "dashboard";
    }
    @GetMapping("/test")
    public String test() {
        return "index";
    }
    @GetMapping("/")
    public String getHome(Model model) {

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "home";
        }
        UserDetails member2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", member2.getUsername());

        return "redirect:/dashboard";
    }
}

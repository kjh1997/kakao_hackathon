package com.review.monitoring.MonitoringSystem.monitor.user.controller;

import com.review.monitoring.MonitoringSystem.monitor.domain.Department;
import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.monitor.user.service.MemberService;
import com.review.monitoring.MonitoringSystem.monitor.user.session.SessionConstants;
import com.review.monitoring.MonitoringSystem.monitor.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberRegistrationController {
    private final MemberService memberService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberVO", new MemberVO());
        return "registerForm";
    }



    @PostMapping("/register")
    public String register(MemberVO memberVO) {
        System.out.println(memberVO.getDepartment());
        Member member = new Member(memberVO.getNickname(), memberVO.getPassword(), memberVO.getEmail(), Department.valueOf(memberVO.getDepartment()));

        memberService.register(member);
        System.out.println("register success");
        return "redirect:/";
    }


    @PostMapping("/memberIdCheck")
    public @ResponseBody String memberIdCheck(@Valid Member member) {
        int result = memberService.checkDuplicateMember(member.getNickname());
        if(result != 0) {
            return "fail";
        }
        return "success";
    }
//<<<<<<< HEAD
//=======
//
//    @PostMapping("/register")
//    public String register(HttpServletRequest request,
//                           @Validated @ModelAttribute MemberVO memberVO,
//                           BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            log.info("errors = {}",bindingResult);
//            return "redirect:/registerForm";
//        }
//
//        Member member = new Member(memberVO.getId(), memberVO.getPassword(), memberVO.getEmail(), Department.valueOf(memberVO.getDepartment()));
//        memberService.register(member);
//        memberVO.setPassword(null);
//        HttpSession httpSession = request.getSession();
//        httpSession.setAttribute(SessionConstants.LOGIN_MEMBER, memberVO);
//        return "redirect:/";
//    }
//>>>>>>> 6a4fd382bdc4ed348a5200769db1f2d5a7e827cb
}

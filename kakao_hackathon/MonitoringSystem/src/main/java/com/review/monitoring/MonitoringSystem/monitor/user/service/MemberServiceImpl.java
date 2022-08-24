package com.review.monitoring.MonitoringSystem.monitor.user.service;

import com.review.monitoring.MonitoringSystem.monitor.domain.Department;
import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.review.MemberRepository;
import com.review.monitoring.MonitoringSystem.monitor.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {
//    private final JPAMemberRepository jpaMemberRepository;
    private final MemberRepository memberRepository;

    @Override
    public int checkDuplicateMember(String id) {
        return memberRepository.existById(id);
    }

    @Override
    @Transactional
    public void register(Member member) {
        memberRepository.insert(member);
    }

    @Override
    public Member getMember(String id) {
        Member member = memberRepository.selectOne(id);
        return member;
    }


    @Override
    public Member logIn(String id, String password) {
        Member member = memberRepository.selectOne(id);
        if ( member == null){
            return null;
        }
        if (!member.getPassword().equals(password)) {
            return null;
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(member),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(token);
        return member;
    }
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        System.out.println(id);
        Member member = memberRepository.selectOne(id);

        if (member == null) {
            throw new UsernameNotFoundException(id);
        }

        return new UserAccount(member);
    }
//    @Override
//    public Member logIn(String id, String password) {
//        Member member = memberRepository.selectOne(id);
//        if(member == null) {
//            return null;
//        }
//        if(!member.getPassword().equals(password)) {
//            return null;
//        }
//        return member;
//    }
//    @Transactional(readOnly = true)
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Member member = jpaMemberRepository.findByEmail(email);
//        if (member == null) {
//            member = accountRepository.findByNickname(emailOrNickname);
//        }
//
//        if (account == null) {
//            throw new UsernameNotFoundException(emailOrNickname);
//        }
//
//        return new UserAccount(account);
//    }


    @Override
    @Transactional
    public Member updateMemberInfo(MemberVO updatedVO, Member member) {
        if(updatedVO.getEmail().equals(member.getEmail()) &&
                updatedVO.getDepartment().equals(member.getDepartment())) {
            return null;
        }

        if(!updatedVO.getEmail().equals(member.getEmail())) {
            member.setEmail(updatedVO.getEmail());
        }
        if(!updatedVO.getDepartment().equals(member.getDepartment())) {
            member.setDepartment(Department.valueOf(updatedVO.getDepartment()));
        }

        return memberRepository.update(member);
    }

    @Override
    @Transactional
    public void delete(String memberId) {
        memberRepository.delete(memberId);
    }


}

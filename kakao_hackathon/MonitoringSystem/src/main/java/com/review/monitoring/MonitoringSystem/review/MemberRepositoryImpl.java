package com.review.monitoring.MonitoringSystem.review;

import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final EntityManager em;

    @Override
    public void insert(Member member) {
        em.persist(member);
    }

    @Override
    public int existById(String id) {
        return em.createQuery("select m from Member m where m.nickname = :memberNickname")
                .setParameter("memberNickname",id)
                .setMaxResults(1)
                .getResultList().size();
    }

    @Override
    public Member selectOne(String memberId) {
        System.out.println("memberId = " + memberId);
        List<Member> data =  em.createQuery("select m from Member m where m.nickname = :memberId ")
                .setParameter("memberId",memberId).setMaxResults(1).getResultList();
        if (data.size() == 0) {
            return null;
        }

        return data.get(0);
//=======
//        return em.find(Member.class, memberId);
//>>>>>>> 6a4fd382bdc4ed348a5200769db1f2d5a7e827cb
    }

    @Override
    public Member update(Member member) {

        return em.merge(member);
    }

    @Override
    public void delete(String memberId) {
        em.remove(memberId);
    }
}

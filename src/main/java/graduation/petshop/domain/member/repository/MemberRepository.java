package graduation.petshop.domain.member.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import graduation.petshop.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //멤버 저장
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    //멤버 수정
    public Member update(Member member) {
        em.merge(member);
        em.flush();
        return member;
    }

    //아이디로 찾기
    public Optional<Member> findById(Long id){
        try {
            return Optional.of(em.find(Member.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    //로그인 아이디로 찾기
    public Optional<Member> findByLoginId(String loginId){
        try {
            return Optional.ofNullable(em.createQuery("select m from Member m where m.loginId = :loginId", Member.class).setParameter("loginId", loginId).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    //이메일로 찾기
    public Optional<Member>  findByEmail(String email){
        try {
            return Optional.ofNullable(em.createQuery("select m from Member m where m.email = :email", Member.class).setParameter("email", email).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    // 유저 이름으로 찾기
    public Optional<Member>  findByUsername(String username){
        try {
            Member member = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    //전체 찾기
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}

package graduation.petshop.domain.walk.repository;

import graduation.petshop.domain.profile.entity.Profile;
import graduation.petshop.domain.walk.entity.Walk;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WalkRepository {

    private final EntityManager em;

    //산책기록 저장
    public void save(Work work){

        em.persist(work);
    }

    //profileId로 산책 찾기
    public Optional<Walk> findWalk(Long id){
        Walk walk = em.find(Walk.class, id);
        return Optional.ofNullable(walk);
    }


}

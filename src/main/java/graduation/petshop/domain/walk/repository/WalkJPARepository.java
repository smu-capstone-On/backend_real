package graduation.petshop.domain.walk.repository;

import graduation.petshop.domain.walk.entity.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalkJPARepository extends JpaRepository<Walk, Long> {

    Optional<Walk> findTopByCheckDateOrderByIdDesc(final String checkDate);

    List<Walk> findWalksByCheckDateBetween(final String startDate, final String endDate);
}

package graduation.petshop.domain.waltkmate.repository;

import graduation.petshop.domain.waltkmate.entity.SexType;
import graduation.petshop.domain.waltkmate.entity.WalkMate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;

@Repository
public interface WalkMateRepository extends JpaRepository<WalkMate, Long> {

    List<WalkMate> findAllBySexTypeAndHasPetAndAgeBetween(SexType sexType, Boolean hasPet, Integer minAge, Integer maxAge);

    @Query("SELECT wm FROM WalkMate wm WHERE " +
            "wm.latitude BETWEEN :latitudeMin AND :latitudeMax AND " +
            "wm.longitude BETWEEN :longitudeMin AND :longitudeMax")
    List<WalkMate> findAllByLatitudeAndLongitude(@Param(value = "latitudeMin") BigDecimal latitudeMin,
                                                 @Param(value = "latitudeMax") BigDecimal latitudeMax,
                                                 @Param(value = "longitudeMin") BigDecimal longitudeMin,
                                                 @Param(value = "longitudeMax") BigDecimal longitudeMax);
}

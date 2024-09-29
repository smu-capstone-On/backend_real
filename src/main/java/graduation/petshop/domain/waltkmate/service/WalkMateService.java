package graduation.petshop.domain.waltkmate.service;

import graduation.petshop.domain.waltkmate.dto.request.WalkMateRequest;
import graduation.petshop.domain.waltkmate.entity.SexType;
import graduation.petshop.domain.waltkmate.entity.WalkMate;
import graduation.petshop.domain.waltkmate.repository.WalkMateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WalkMateService {

    private final WalkMateRepository walkMateRepository;
    private static final BigDecimal A = BigDecimal.valueOf(0.03);

    @Transactional
    public WalkMate save(WalkMateRequest request) {
        return walkMateRepository.save(request.of());
    }

    public List<WalkMate> getWalkMates(SexType sexType, Integer minAge, Integer maxAge, Boolean hasPet) {
        if (sexType != null && minAge != null && hasPet != null) {
            return walkMateRepository.findAllBySexTypeAndHasPetAndAgeBetween(sexType, hasPet, minAge, maxAge);
        } else {
            return walkMateRepository.findAll();
        }
    }

    /**
     * 위/경도를 파라미터로 받고 범위 내 조건 값을 리턴
     * a=0.03을 기본으로 하는데 현재 파라미터로 받을지는 모르겠음
     * @param latitude
     * @param longitude
     * @return
     */
    public List<WalkMate> getWalkMatesForBetweenLocation(BigDecimal latitude, BigDecimal longitude) {
        BigDecimal latitudeMin = latitude.subtract(A);
        BigDecimal latitudeMax = latitude.add(A);
        BigDecimal longitudeMin = longitude.subtract(A);
        BigDecimal longitudeMax = longitude.add(A);
        return walkMateRepository.findAllByLatitudeAndLongitude(latitudeMin, latitudeMax, longitudeMin, longitudeMax);
    }
}

package graduation.petshop.domain.walk.service;

import graduation.petshop.domain.profile.entity.Profile;
import graduation.petshop.domain.profile.repository.ProfileRepository;
import graduation.petshop.domain.walk.dto.request.JoinWalk;
import graduation.petshop.domain.walk.dto.request.StatisticsWalkRequest;
import graduation.petshop.domain.walk.dto.response.StatisticsWalkResponse;
import graduation.petshop.domain.walk.entity.Walk;
import graduation.petshop.domain.walk.repository.WalkJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WalkService {

    private final ProfileRepository profileRepository;
    private final WalkJPARepository walkJPARepository;

    public Walk getWalkAt(final String checkDate) {
        return walkJPARepository.findTopByCheckDateOrderByIdDesc(checkDate).orElseThrow(() -> new IllegalArgumentException("해당 날짜에는 산책 기록이 없어요!"));
    }

    @Transactional
    public Walk joinWalk(final JoinWalk joinWalk) {
        final LocalDateTime walkTime = joinWalk.getWalkTime();
        final LocalDateTime realWalkTime = LocalDateTime.of(walkTime.getYear(), walkTime.getMonth(), walkTime.getDayOfMonth(), walkTime.getHour(), walkTime.getMinute(), walkTime.getSecond());

        final Profile profile = profileRepository.findById(joinWalk.getProfileId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로필 ID"));

        return walkJPARepository.save(Walk.createWalk(profile, joinWalk, realWalkTime));
    }

    public List<Walk> getWalkStatistics(StatisticsWalkRequest request) {

        final List<Walk> walks = walkJPARepository.findWalksByCheckDateBetween(request.startDate(), request.endDate());

        if (walks.isEmpty()) {
            return Collections.emptyList();
            //return new StatisticsWalkResponse(0, (double) 0, (double) 0);
        } else {

//            // 평균 산책 시간
//            final int sumHours = walks.stream().map(Walk::getWalkTime).mapToInt(LocalDateTime::getHour).sum();
//            final int sumMinutes = walks.stream().map(Walk::getWalkTime).mapToInt(LocalDateTime::getMinute).sum();
//            final int averageWalkTime = ((sumHours * 60) + sumMinutes) / walks.size();
//
//            // 평균 산책 거리
//            final double averageDistance = walks.stream().mapToDouble(Walk::getDistance).average().orElse(0);
//
//            // 평균 속도
//            final double averageSpeed = walks.stream().mapToDouble(Walk::getSpeed).average().orElse(0);

            return walks;
        }
    }
}

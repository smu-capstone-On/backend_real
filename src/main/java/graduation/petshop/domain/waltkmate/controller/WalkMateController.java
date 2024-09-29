package graduation.petshop.domain.waltkmate.controller;

import graduation.petshop.domain.waltkmate.dto.request.WalkMateRequest;
import graduation.petshop.domain.waltkmate.entity.SexType;
import graduation.petshop.domain.waltkmate.service.WalkMateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/walkmate")
public class WalkMateController {

    private final WalkMateService walkMateService;

    @PostMapping
    public ResponseEntity<?> saveWalkMate(@RequestBody WalkMateRequest request) {
        return ResponseEntity.ok(walkMateService.save(request));
    }

    @GetMapping
    public ResponseEntity<?> getWalkMates(@RequestParam(value = "sexType", required = false) SexType sexType,
                                          @RequestParam(value = "minAge", required = false) Integer minAge,
                                          @RequestParam(value = "maxAge", required = false) Integer maxAge,
                                          @RequestParam(value = "hasPet", required = false) Boolean hasPet) {
        return ResponseEntity.ok(walkMateService.getWalkMates(sexType, minAge, maxAge, hasPet));
    }

    /**
     * 산책 메이트 위/경도를 파라미터로 받고 범위 내에 있는 조건 값 리턴
     * @param latitude
     * @param logitude
     * @return
     */
    @GetMapping(value = "/localtion")
    public ResponseEntity<?> getWalkMatesForBetweenLocation(@RequestParam(value = "latitude") BigDecimal latitude,
                                          @RequestParam(value = "logitude") BigDecimal logitude ) {
        return ResponseEntity.ok(walkMateService.getWalkMatesForBetweenLocation(latitude, logitude));
    }
}

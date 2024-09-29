package graduation.petshop.domain.walk.controller;

import graduation.petshop.domain.walk.dto.request.JoinWalk;
import graduation.petshop.domain.walk.dto.request.StatisticsWalkRequest;
import graduation.petshop.domain.walk.dto.response.ResponseFindWalkDto;
import graduation.petshop.domain.walk.service.WalkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/walk")
public class WalkController {

    private final WalkService walkService;

    @GetMapping
    public ResponseEntity<?> getWalk(@RequestParam(value = "checkDate") final String checkDate) {
        log.info("checkDate[{}]", checkDate);
        return ResponseEntity.ok(walkService.getWalkAt(checkDate));
    }

    @PostMapping
    public ResponseEntity<?> joinWalk(@RequestBody final JoinWalk joinWalk) {
        log.info("joinWalk[{}]", joinWalk);
        return ResponseEntity.ok(new ResponseFindWalkDto(walkService.joinWalk(joinWalk)));
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getWalkStatistics(StatisticsWalkRequest request) {
        log.info("request[{}]", request);
        return ResponseEntity.ok(walkService.getWalkStatistics(request));
    }
}

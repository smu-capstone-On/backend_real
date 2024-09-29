package graduation.petshop.domain.like.controller;

import graduation.petshop.domain.like.dto.request.LikeRequest;
import graduation.petshop.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<?> saveLike(@RequestBody LikeRequest likeRequest) {
        return ResponseEntity.ok(likeService.save(likeRequest));
    }
}

package graduation.petshop.domain.board.controller;

import graduation.petshop.domain.board.dto.request.CommentRequest;
import graduation.petshop.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> saveComment(@RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.save(request));
    }
}

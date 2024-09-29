package graduation.petshop.domain.member.controller;

import graduation.petshop.domain.board.service.BoardService;
import graduation.petshop.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<?> getComments(@RequestParam(value = "memberId") Long memberId) {
        return ResponseEntity.ok(commentService.getMyComments(memberId));
    }

    @GetMapping("/boards")
    public ResponseEntity<?> getBoards(@RequestParam(value = "memberId") Long memberId) {
        return ResponseEntity.ok(boardService.getMyBoards(memberId));
    }
}

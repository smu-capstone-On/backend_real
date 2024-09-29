package graduation.petshop.domain.board.controller;

import graduation.petshop.domain.board.dto.request.BoardRequest;
import graduation.petshop.domain.board.dto.request.BoardFilter;
import graduation.petshop.domain.board.entity.TagType;
import graduation.petshop.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<?> getAllBoard(Pageable pageable) {
        return ResponseEntity.ok(boardService.getAllBoard(pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getBoardsFilter(BoardFilter boardFilter) {
        return ResponseEntity.ok(boardService.getBoardFilter(boardFilter));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable(name = "boardId") Long boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveBoard(@RequestParam("file") MultipartFile file,
                                       @RequestParam("userId") Long userId,
                                       @RequestParam("title") String title,
                                       @RequestParam("body") String body,
                                       @RequestParam("tagTypes") List<TagType> tagTypes ) {

        BoardRequest boardRequest = new BoardRequest();
        boardRequest.setFile(file);
        boardRequest.setUserId(userId);
        boardRequest.setTitle(title);
        boardRequest.setBody(body);
        boardRequest.setTagTypes(tagTypes);

        return ResponseEntity.ok(boardService.save(boardRequest));
    }
}

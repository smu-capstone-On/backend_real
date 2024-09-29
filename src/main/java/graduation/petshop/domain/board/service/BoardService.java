package graduation.petshop.domain.board.service;

import graduation.petshop.domain.board.dto.request.BoardRequest;
import graduation.petshop.domain.board.dto.request.BoardFilter;
import graduation.petshop.domain.board.dto.response.BoardResponse;
import graduation.petshop.domain.board.entity.Board;
import graduation.petshop.domain.board.entity.BoardTag;
import graduation.petshop.domain.board.entity.TagType;
import graduation.petshop.domain.board.repository.BoardRepository;
import graduation.petshop.domain.board.repository.BoardTagRepository;
import graduation.petshop.domain.file.entity.FileInfo;
import graduation.petshop.domain.file.service.FileService;
import graduation.petshop.domain.file.service.ImageType;
import graduation.petshop.domain.member.entity.Member;
import graduation.petshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    @Autowired
    private final FileService fileService;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardTagRepository boardTagRepository;

    public List<BoardResponse> getAllBoard(Pageable pageable) {
        return boardRepository.findAll(pageable)
                .map(BoardResponse::of)
                .toList();
    }

    public List<BoardResponse> getBoardFilter(BoardFilter boardFilter) {
        List<Board> boardList1 = boardRepository.getBoardFilter(boardFilter.getBoardTags(), boardFilter.getTitle());
        //List<Board> boardList2 = boardRepository.findAllByTitleLike(boardFilter.getTitle());
        //boardList1.addAll(boardList2);

        return boardList1.stream().map(BoardResponse::of).distinct().toList();
    }

    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글"));
        return BoardResponse.of(board);
    }

    @Transactional
    public BoardResponse save(BoardRequest boardRequest) {
        final Member member = memberRepository.findById(boardRequest.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        final List<FileInfo> fileInfos = fileService.saveFile(List.of(boardRequest.getFile()), ImageType.BOARD);

        final Board board = new Board(
                boardRequest.getTitle(),
                boardRequest.getBody(),
                fileInfos.get(0),
                member
        );
        final Board savedBoard = boardRepository.save(board);

        final List<BoardTag> boardTagList = new ArrayList<>();
        final List<TagType> tagTypes = boardRequest.getTagTypes();
        tagTypes.forEach(boardTagType -> {

            BoardTag boardTag = new BoardTag(savedBoard, boardTagType);
            boardTagList.add(boardTagRepository.save(boardTag));
        });

        savedBoard.setBoardTagList(boardTagList);
        return BoardResponse.of(boardRepository.save(savedBoard));
    }

    public List<BoardResponse> getMyBoards(Long memberId) {
        return boardRepository.findAllByMemberId(memberId).stream().map(BoardResponse::of).toList();
    }
}

package graduation.petshop.domain.board.service;

import graduation.petshop.domain.board.dto.request.CommentRequest;
import graduation.petshop.domain.board.entity.Board;
import graduation.petshop.domain.board.entity.Comment;
import graduation.petshop.domain.board.repository.BoardRepository;
import graduation.petshop.domain.board.repository.CommentRepository;
import graduation.petshop.domain.member.entity.Member;
import graduation.petshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment save(CommentRequest request) {
        final Board board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글"));
        final Member member = memberRepository.findById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));

        final Comment comment = new Comment(board, member, request.getBody());
        return commentRepository.save(comment);
    }

    public List<Comment> getMyComments(Long memberId) {
        return commentRepository.findAllByMemberId(memberId);
    }
}

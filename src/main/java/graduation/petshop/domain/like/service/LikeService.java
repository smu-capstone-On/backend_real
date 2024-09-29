package graduation.petshop.domain.like.service;

import graduation.petshop.domain.board.entity.Board;
import graduation.petshop.domain.board.repository.BoardRepository;
import graduation.petshop.domain.like.dto.request.LikeRequest;
import graduation.petshop.domain.like.entity.Like;
import graduation.petshop.domain.like.repository.LikeRepository;
import graduation.petshop.domain.member.entity.Member;
import graduation.petshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Like save(final LikeRequest request) {

        final Board board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글"));
        final Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));

        final Like like = new Like(board, member);
        return likeRepository.save(like);
    }
}

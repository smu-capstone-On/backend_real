package graduation.petshop.domain.board.repository;

import graduation.petshop.domain.board.entity.Board;
import graduation.petshop.domain.board.entity.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b JOIN FETCH b.boardTagList bt WHERE b.member.id = bt.board.member.id and (bt.tagType IN :boardTags or b.title like '%:title%')")
    List<Board> getBoardFilter(@Param(value = "boardTags") List<TagType> boardTags, @Param(value = "title") String title);

    List<Board> findAllByTitleLike(@Param(value = "title") String title);

    List<Board> findAllByMemberId(@Param(value = "memberId") Long memberId);
}

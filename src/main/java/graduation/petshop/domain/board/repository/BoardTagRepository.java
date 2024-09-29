package graduation.petshop.domain.board.repository;

import graduation.petshop.domain.board.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
}

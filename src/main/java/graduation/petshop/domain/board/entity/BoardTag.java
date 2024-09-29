package graduation.petshop.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_tag_type")
    @Enumerated(value = EnumType.STRING)
    private TagType tagType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Board board;

    public BoardTag(Board board, TagType tagType) {
        this.tagType = tagType;
        this.setBoard(board);
    }

    private void setBoard(Board board) {
        this.board = board;
        board.getBoardTagList().add(this);
    }
}

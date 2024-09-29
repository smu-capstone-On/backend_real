package graduation.petshop.domain.like.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graduation.petshop.domain.board.entity.Board;
import graduation.petshop.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    public void setBoard(Board board) {
        this.board = board;
        board.getLikeList().add(this);
    }

    public Like(Board board, Member member) {
        this.setBoard(board);
        this.member = member;
    }
}

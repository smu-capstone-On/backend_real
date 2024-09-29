package graduation.petshop.domain.board.dto.request;

import lombok.Getter;

@Getter
public class CommentRequest {

    private Long boardId;
    private Long userId;
    private String body;
}

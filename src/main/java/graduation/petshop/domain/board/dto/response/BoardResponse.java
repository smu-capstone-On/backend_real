package graduation.petshop.domain.board.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import graduation.petshop.domain.board.entity.Board;
import graduation.petshop.domain.board.entity.BoardTag;
import graduation.petshop.domain.board.entity.TagType;
import graduation.petshop.domain.board.entity.Comment;
import graduation.petshop.domain.file.entity.FileInfo;
import graduation.petshop.domain.member.entity.Member;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardResponse {

    private Long id;
    private String title;
    private String body;
    private int likeCount;
    private List<TagType> boardTags;
    private List<Comment> comments;
    private FileInfo fileInfo;
    private Long memberId;

    public static BoardResponse of(Board board) {

        board.getMember().getId();

        BoardResponse response = new BoardResponse();
        response.id = board.getId();
        response.title = board.getTitle();
        response.body = board.getBody();
        response.likeCount = board.getLikeList().size();
        response.boardTags = board.getBoardTagList().stream().map(BoardTag::getTagType).toList();
        response.fileInfo = board.getFileInfo();
        response.comments = board.getComments();
        response.memberId = board.getMember() != null ? board.getMember().getId() : null;

        return response;
    }
}

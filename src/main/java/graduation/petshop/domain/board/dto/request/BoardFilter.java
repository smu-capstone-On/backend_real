package graduation.petshop.domain.board.dto.request;

import graduation.petshop.domain.board.entity.TagType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BoardFilter {
    private List<TagType> boardTags;
    private String title;
}

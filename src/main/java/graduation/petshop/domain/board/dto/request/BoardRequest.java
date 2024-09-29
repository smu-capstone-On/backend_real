package graduation.petshop.domain.board.dto.request;

import graduation.petshop.domain.board.entity.TagType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class BoardRequest {

    private MultipartFile file;
    private Long userId;
    private String title;
    private String body;
    private List<TagType> tagTypes;
}

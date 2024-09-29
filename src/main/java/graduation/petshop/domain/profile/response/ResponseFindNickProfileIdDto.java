package graduation.petshop.domain.profile.response;

import graduation.petshop.domain.profile.entity.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseFindNickProfileIdDto {

    private Long id ;

    public ResponseFindNickProfileIdDto(Profile p){
        this.id = p.getId();
    }
}

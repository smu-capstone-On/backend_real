package graduation.petshop.domain.profile.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestFindNicknameDto {

    @NotEmpty
    private String nickname ;

}
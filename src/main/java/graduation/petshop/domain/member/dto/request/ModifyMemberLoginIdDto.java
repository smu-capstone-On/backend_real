package graduation.petshop.domain.member.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModifyMemberLoginIdDto {

    private String loginId;
    private String modifyLoginId;
}
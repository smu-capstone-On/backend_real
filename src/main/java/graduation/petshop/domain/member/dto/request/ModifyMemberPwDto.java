package graduation.petshop.domain.member.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModifyMemberPwDto {

    private String loginId;
    private String modifyPassword;
}
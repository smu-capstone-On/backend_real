package graduation.petshop.domain.profile.dto.request;

import graduation.petshop.domain.profile.entity.PetStatus;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModifyProfileDto {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{4,10}$", message = "닉네임는 특수문자를 제외한 4~10자리여야 합니다.")
    private String nickName;

    private PetStatus petStatus;
}
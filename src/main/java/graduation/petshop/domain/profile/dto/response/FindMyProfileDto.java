package graduation.petshop.domain.profile.dto.response;

import graduation.petshop.domain.profile.entity.Gender;
import graduation.petshop.domain.profile.entity.PetStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMyProfileDto {
    private String nickName;
    private Gender sex;
    private Integer age;
    private PetStatus petStatus;
}
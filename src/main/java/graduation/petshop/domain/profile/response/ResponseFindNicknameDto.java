package graduation.petshop.domain.profile.response;

import graduation.petshop.domain.member.entity.Member;
import graduation.petshop.domain.profile.entity.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseFindNicknameDto {

    private String nickname;

    public ResponseFindNicknameDto(String nickname) {
        this.nickname = nickname;
    }
}

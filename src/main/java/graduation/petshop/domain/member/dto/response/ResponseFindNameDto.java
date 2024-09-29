package graduation.petshop.domain.member.dto.response;

import graduation.petshop.domain.member.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseFindNameDto {

    private String username;

    public ResponseFindNameDto(Member m){
        this.username = m.getUsername();
    }
}

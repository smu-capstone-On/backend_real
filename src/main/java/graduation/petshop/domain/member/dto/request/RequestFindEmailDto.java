package graduation.petshop.domain.member.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestFindEmailDto {

    @NotEmpty
    private String email;

}
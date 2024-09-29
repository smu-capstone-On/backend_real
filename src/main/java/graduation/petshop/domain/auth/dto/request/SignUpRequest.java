package graduation.petshop.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
        @NotBlank
        String id,

        @NotBlank
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{1,20}",
                message = "비밀번호는 영문과 숫자가 포함된 1자 ~ 20자의 비밀번호여야 합니다.")
        String password,

        @NotBlank
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{1,20}",
                message = "비밀번호는 영문과 숫자가 포함된 1자 ~ 20자의 비밀번호여야 합니다.")
        String confirmPassword
) {
}

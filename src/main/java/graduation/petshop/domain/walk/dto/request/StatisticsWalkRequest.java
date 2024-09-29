package graduation.petshop.domain.walk.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record StatisticsWalkRequest(
        @NotBlank
        String startDate,
        @NotBlank
        String endDate
) {
}

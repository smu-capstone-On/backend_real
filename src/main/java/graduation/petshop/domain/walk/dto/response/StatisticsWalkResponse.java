package graduation.petshop.domain.walk.dto.response;

public record StatisticsWalkResponse(
        Integer averageWalkTime,
        Double averageDistance,
        Double averageSpeed
) {
}

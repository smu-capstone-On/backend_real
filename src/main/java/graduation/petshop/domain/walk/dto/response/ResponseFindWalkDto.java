package graduation.petshop.domain.walk.dto.response;

import graduation.petshop.domain.profile.entity.Profile;
import graduation.petshop.domain.walk.entity.Walk;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseFindWalkDto {

    private Long id;
    private Long profileId;

    private String checkDate; // yyyyMMdd;

    private LocalDateTime walkTime;

    private double speed;

    private double distance;
    private Profile profile;

    public ResponseFindWalkDto(Walk walk) {
        this.id = walk.getId();
        this.profileId = walk.getProfile().getId();
        this.checkDate = walk.getCheckDate();
        this.walkTime = walk.getWalkTime();
        this.speed = walk.getSpeed();
        this.distance = walk.getDistance();
        this.profile = walk.getProfile();
    }


}

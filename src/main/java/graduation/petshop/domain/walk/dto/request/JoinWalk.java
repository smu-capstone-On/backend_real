package graduation.petshop.domain.walk.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JoinWalk {

    private Long profileId;

    private String checkDate; // yyyyMMdd;

    private LocalDateTime walkTime;

    private double speed;

    private double distance;

}

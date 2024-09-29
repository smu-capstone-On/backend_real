package graduation.petshop.domain.waltkmate.dto.request;

import graduation.petshop.domain.waltkmate.entity.SexType;
import graduation.petshop.domain.waltkmate.entity.WalkMate;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class WalkMateRequest {

    private Long memberId;
    private SexType sexType;
    private int age;
    private Boolean hasPet;

    //private String location; // 위치
    private BigDecimal latitude; // 위도
    private BigDecimal longitude; // 경도
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String memo;

    public WalkMate of() {
        WalkMate walkMate = new WalkMate();
        walkMate.setMemberId(memberId);
        walkMate.setSexType(sexType);
        walkMate.setAge(age);
        walkMate.setHasPet(hasPet);
        //walkMate.setLocation(location);
        walkMate.setLatitude(latitude);
        walkMate.setLongitude(longitude);
        walkMate.setStartDateTime(startDateTime);
        walkMate.setEndDateTime(endDateTime);
        walkMate.setMemo(memo);

        return walkMate;
    }
}

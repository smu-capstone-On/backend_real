package graduation.petshop.domain.walk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import graduation.petshop.common.entity.Base;
import graduation.petshop.domain.member.entity.Member;
import graduation.petshop.domain.profile.entity.Profile;
import graduation.petshop.domain.walk.dto.request.JoinWalk;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@AllArgsConstructor
public class Walk extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "walk_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @JsonBackReference // 부모참조
    private Profile profile;

    private String checkDate;        // 측정 날짜 (yyMMdd)

    private LocalDateTime walkTime;  // 산책 시간

    private double speed;            // 속도

    private double distance;         // 거리

    //연관관계 메소드
    public void setProfile(Profile profile) {
        this.profile = profile;
        profile.getWalk().add(this);
    }

    //산책 생성 메소드
    public static Walk createWalk(Profile profile, JoinWalk joinWalk, LocalDateTime realWalkDateTime) {
        Walk walk = new Walk();
        walk.setProfile(profile);
        walk.setSpeed(joinWalk.getSpeed());
        walk.setWalkTime(realWalkDateTime);
        walk.setDistance(joinWalk.getDistance());
        walk.setCheckDate(joinWalk.getCheckDate());

        return walk;
    }
}

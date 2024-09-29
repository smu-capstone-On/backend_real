package graduation.petshop.domain.profile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import graduation.petshop.common.entity.Base;
import graduation.petshop.domain.member.entity.Member;
import graduation.petshop.domain.profile.dto.request.JoinProfileDto;
import graduation.petshop.domain.walk.entity.Walk;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.mapping.Join;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Profile extends Base {


    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender sex; // 성별 [FEMALE, MALE]

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetStatus petStatus; // 산책반려동물 유무 [PETYES, PETNO]

    @Column(nullable = false)
    private Integer age;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profileImage_id")
    private ProfileImage profileImage;

    // Member 엔티티와의 양방향 일대일 관계 설정
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @JsonManagedReference // 자식참조
    private List<Walk> walk = new ArrayList<>();


    /* 닉네임 수정 로직*/
    public void modify(String nickName, PetStatus petStatus) {
        this.nickName = nickName;
        this.petStatus = petStatus;
    }

    public void setMember(Member member) {
        this.member = member;
        member.setProfile(this);
    }

    /**
     * 프로필 생성 메소드
     */
    public static Profile createProfile(Member member, JoinProfileDto joinProfileDto){
        Profile profile = new Profile();

        profile.setMember(member);
        profile.setNickName(joinProfileDto.getNickName());
        profile.setSex(joinProfileDto.getSex());
        profile.setAge(joinProfileDto.getAge());
        profile.setPetStatus(joinProfileDto.getPetStatus());

        return profile;
    }


}

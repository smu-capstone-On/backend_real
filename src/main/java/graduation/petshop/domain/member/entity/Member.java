package graduation.petshop.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import graduation.petshop.common.entity.Base;
import graduation.petshop.domain.profile.entity.PetStatus;
import graduation.petshop.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
public class Member extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String email;

    @Column(name = "login_id")
    private String loginId;

    private String password;

    //카카오 로그인 시 인증을 위함
    private String username;

    private String emailCode;

    private boolean emailEnabled;

    // Profile 엔티티와의 일대일 양방향 관계 설정
    @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private Profile profile;

    //비밀번호 encoder
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    /* 아이디 수정 로직*/
    public void modifyByLoginId(String loginId) {
        this.loginId = loginId;
    }

    /* 비밀번호 수정 로직*/
    public void modifyByPw(String password) {
        this.password = password;
    }
}

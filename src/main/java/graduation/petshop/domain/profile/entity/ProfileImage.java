package graduation.petshop.domain.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProfileImage {

    @Id
    @GeneratedValue
    private Long id;

    private String originalFilename; //원본 파일명
    private String saveFilename; //서버에 저장된 파일명

    @OneToOne(mappedBy = "profileImage",fetch = FetchType.LAZY)
    private Profile profile;
}

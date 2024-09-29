package graduation.petshop.domain.profile.controller;


import graduation.petshop.common.util.ApiResponse;
import graduation.petshop.domain.profile.dto.request.JoinProfileDto;
import graduation.petshop.domain.profile.dto.request.ModifyProfileDto;
import graduation.petshop.domain.profile.dto.response.FindMyProfileDto;
import graduation.petshop.domain.profile.entity.Profile;
import graduation.petshop.domain.profile.request.RequestFindNicknameDto;
import graduation.petshop.domain.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    // POST -> 프로필 생성 메소드
    @PostMapping("/{memberId}")
    public ResponseEntity<Object> createProfile(@PathVariable("memberId") Long memberId, @RequestBody @Validated JoinProfileDto joinProfileDto) {
        log.info("프로필 생성 {}", memberId);

        // 프로필 생성 메소드 호출하여 프로필 저장 후 ID 반환
        ApiResponse profileId = profileService.join(memberId,joinProfileDto);

        // 생성된 프로필의 ID를 반환
        return ResponseEntity.ok(profileId);
    }

    // PUT -> 프로필 수정 메소드
    @PutMapping("/{profileId}")
    public ResponseEntity<Object> modifyProfile(@PathVariable(name = "profileId") Long profileId, @RequestBody @Validated ModifyProfileDto modifyProfileDto) {
        log.info("프로필 수정 {}", profileId);

        // 프로필 수정 메소드 호출
        profileService.modifyProfile(profileId, modifyProfileDto);

        // 수정 성공 시 204 No Content 상태코드 반환
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 마이페이지로 이동예정 GET -> 프로필 조회 메소드

    @GetMapping("/{profileId}")//이것도 마이페이지로 이동 예정
    public ResponseEntity<FindMyProfileDto> findMyProfile(@PathVariable(name = "profileId") Long profileId) {

        Profile myProfile = profileService.findMyProfile(profileId);

        if (myProfile != null) {
            FindMyProfileDto findMyProfileDto = new FindMyProfileDto(
                    myProfile.getNickName(),
                    myProfile.getSex(),
                    myProfile.getAge(),
                    myProfile.getPetStatus()
            );
            return ResponseEntity.ok(findMyProfileDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 닉네임 중복 조회
     * @param findNicknameDto
     * @return
     */
    @GetMapping("/join/nickname")
    public ResponseEntity<Object> findMyProfileByNickname(RequestFindNicknameDto findNicknameDto) {
        ApiResponse profile = profileService.validateDuplicateProfile(findNicknameDto.getNickname());
        return ResponseEntity.ok(profile);
    }

}

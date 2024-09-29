package graduation.petshop.domain.profile.service;


import graduation.petshop.common.util.ApiResponse;
import graduation.petshop.domain.member.repository.MemberRepository;
import graduation.petshop.domain.profile.dto.request.JoinProfileDto;
import graduation.petshop.domain.profile.dto.request.ModifyProfileDto;
import graduation.petshop.domain.profile.entity.Profile;
import graduation.petshop.domain.profile.repository.ProfileRepository;
import graduation.petshop.domain.profile.response.ResponseFindNickProfileIdDto;
import graduation.petshop.domain.profile.response.ResponseFindNicknameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    /*프로필 생성*/
    @Transactional
    public ApiResponse join(Long memberId,JoinProfileDto joinProfileDto) {
        // 중복 닉네임 검증.
        if(!validateDuplicateProfile(joinProfileDto.getNickName()).isSuccess()) {
            return validateDuplicateProfile(joinProfileDto.getNickName());
        }
        Profile profile = Profile.createProfile(memberRepository.findById(memberId).get(), joinProfileDto);
        profileRepository.save(profile);
        return new ApiResponse(true, new ResponseFindNickProfileIdDto(profile), "프로필 등록을 성공했습니다.");
    }

    /* 프로필 수정 더티체킹 - 닉네임과 팻 상태만 수정할 수 있도록. */
    @Transactional
    public void modifyProfile(Long profileId, ModifyProfileDto modifyProfileDto) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 프로필이 존재하지 않습니다."));
        profile.modify(modifyProfileDto.getNickName(), modifyProfileDto.getPetStatus());
        profileRepository.update(profile);
    }

    /**
     * 닉네임 중복 찾기
     *
     * @param nickName
     * @return
     */
    public ApiResponse validateDuplicateProfile(String nickName) {
        List<Profile> findNickName = profileRepository.findByNickName(nickName);
        if (!findNickName.isEmpty()) {
            return new ApiResponse(false, null ,new IllegalStateException("이미 존재하는 닉네임입니다.").getMessage());
        }
        return new ApiResponse(true, new ResponseFindNicknameDto(nickName),"사용가능한 닉네임입니다.");
    }

    /*프로필 조회 -> 나중에 마이페이지에서 찾을 수 있도록*/
    @Transactional(readOnly = true)
    public Profile findMyProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 프로필이 존재하지 않습니다."));
    }


}

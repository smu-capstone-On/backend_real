package graduation.petshop.domain.auth.service;

import graduation.petshop.domain.auth.dto.CustomOAuth2User;
import graduation.petshop.domain.auth.dto.UserDto;
import graduation.petshop.domain.auth.dto.response.KakaoResponse;
import graduation.petshop.domain.member.entity.Member;
import graduation.petshop.domain.member.repository.MemberRepository;
import graduation.petshop.security.jwt.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOauth2UserService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User :" + oAuth2User);

        KakaoResponse oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        if(memberRepository.findByUsername(username).isPresent()){
            Optional<Member> existData = memberRepository.findByUsername(username);
            UserDto userDTO = new UserDto();
            userDTO.setUsername(existData.get().getUsername());
            userDTO.setName(oAuth2Response.getNickname());
            //이 부분 이상함
            userDTO.setRole("ROLE_USER");

            return new UserPrincipal(userDTO);
        }
        else  {
            log.info("null");
            Member member = Member.builder()
                    .username(username)
                            .build();

            memberRepository.save(member);

            UserDto userDTO = new UserDto();
            userDTO.setUsername(username);
            userDTO.setName(oAuth2Response.getNickname());
            userDTO.setRole("ROLE_USER");

            return new UserPrincipal(userDTO);
        }
    }
}

package graduation.petshop.domain.member.service;

import graduation.petshop.common.util.ApiResponse;
import graduation.petshop.domain.member.dto.request.*;
import graduation.petshop.domain.member.dto.response.ResponseFindIdDto;
import graduation.petshop.domain.member.dto.response.ResponseFindPwdDto;
import graduation.petshop.domain.member.entity.Member;
import graduation.petshop.domain.member.repository.MemberRepository;
import graduation.petshop.security.jwt.dto.CustomUserDetails;

import graduation.petshop.security.jwt.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;
    private final JwtUtil jwtUtil;
    /**
     * 회원가입
     * 여기서 중복조회 하는게 더 낫나?
     */
    @Transactional
    public ApiResponse join(JoinDto joinDto) {
        Member member = joinDto.toEntity(
                joinDto.getLoginId(),
                joinDto.getPassword(),
                joinDto.getEmail()
        );

        //회원 아이디 중복 확인
        ApiResponse apiResponse = checkLoginId(member.getLoginId());

        // true : 사용가능한 ID , false: 이미 존재하는 ID
        if(!apiResponse.isSuccess()) {
            return new ApiResponse(false, null, "이미 등록된 ID 입니다");
        }

        member.passwordEncode(passwordEncoder);
        Optional<Member> signupMember = Optional.ofNullable(memberRepository.save(member));

        if(!signupMember.isPresent()) {
            return new ApiResponse(false, null, "회원가입을 실패했습니다.");
        }

        // 이메일 토큰 생성
        String token = jwtUtil.createJwt(member.getEmail(), 24 * 60 * 60 * 1000L); // 24시간 유효

        // 이메일 인증 전송
        //sendEmail(member.getEmail(), token);
        return new ApiResponse(true, new ResponseFindIdDto(member), "회원가입이 완료되었습니다. 이메일을 확인해주세요.");
    }


    /**
     * 이메일로 아이디 찾기
     */
    public ApiResponse findIdByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if(!member.isPresent()) {
            return new ApiResponse(false, null, "회원정보가 존재하지 않습니다");
        } else {
            return new ApiResponse(true, new ResponseFindIdDto(member.get()), "null");
        }
    }

    /**
     * 로그인아이디로 비밀번호 찾기
     */
    public void resetPassword(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

        // 임시 비밀번호 생성
        String tempPassword = generateTemporaryPassword();

        // 비밀번호 암호화
        member.modifyByPw(tempPassword);
        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);

        // 이메일 전송
        sendTempPasswordEmail(email, tempPassword);
    }

    private String generateTemporaryPassword() {
        return UUID.randomUUID().toString().substring(0, 8); // 8자리 임시 비밀번호
    }
//    public ApiResponse findPwdByLoginId(String loginId) {
//
//        Optional<Member> member = memberRepository.findByLoginId(loginId);
//
//        if(!member.isPresent()) {
//            return new ApiResponse(false, null, "회원정보가 존재하지 않습니다");
//        } else {
//            return new ApiResponse(true, new ResponseFindPwdDto(member.get()), "null");
//        }
//    }

    /**
     * 회원가입 아이디 중복 확인
     */
    public ApiResponse checkLoginId(String loginId) {
        Optional<Member> member = memberRepository.findByLoginId(loginId);

        if (!member.isPresent()) {
            return new ApiResponse(true, loginId, "사용가능한 아이디입니다.");
        } else {
            return new ApiResponse(false, new ResponseFindIdDto(member.get()), "중복입니다.");
        }
    }

    /**
     * 아이디 수정
     *
     * @param
     * @param
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class}, readOnly = false)
    public ApiResponse updateLoginId(ModifyMemberLoginIdDto modifyMemberDto) {

        Member member = memberRepository.findByLoginId(modifyMemberDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 멤버가 존재하지 않습니다."));
        member.modifyByLoginId(modifyMemberDto.getModifyLoginId());

        Optional<Member> updateMember = Optional.ofNullable(memberRepository.update(member));
        if(!updateMember.isPresent()) {
            return new ApiResponse(true, updateMember, "정상처리되었습니다.");
        }
        return null;
    }

    /**
     * 비밀번호 수정
     *
     * @param
     * @param
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class}, readOnly = false)
    public ApiResponse updateLoginPw(ModifyMemberPwDto modifyMemberPwDto) {

        Member member = memberRepository.findByLoginId(modifyMemberPwDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 멤버가 존재하지 않습니다."));

        member.modifyByPw(modifyMemberPwDto.getModifyPassword());
        member.passwordEncode(passwordEncoder);

        Optional<Member> updateMember = Optional.ofNullable(memberRepository.update(member));
        if(!updateMember.isPresent()) {
            return new ApiResponse(true, updateMember, "정상처리되었습니다.");
        }
        return null;
    }


    /**
     * 이메일 인증코드 전송
     * @param requestFindEmailAndIdDto
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class}, readOnly = false)
    public void sendConfirmedCode (RequestFindEmailAndIdDto requestFindEmailAndIdDto) {
        // 인증코드 생성
        String tempPassword = generateTemporaryPassword();

        Optional<Member> byLoginId = memberRepository.findByLoginId(requestFindEmailAndIdDto.getLoginId());

        // 인증코드가 발송하고 해당 계정에 코드를 저장한다.
        if(byLoginId.isPresent()) {
            byLoginId.get().setEmailCode(tempPassword);
            memberRepository.update(byLoginId.get());

            String subject = "이메일 인증코드 전송";
            String text = "인증코드 : " + tempPassword;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            try {
                helper.setTo(requestFindEmailAndIdDto.getEmail());
                helper.setSubject(subject);
                helper.setText(text, true);
                mailSender.send(message);


            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send email", e);
            }
        }
    }

    /**
     * 이메일 인증코드 전송
     * @param to
     * @param token
     * @return
     */
    private void sendEmail(String to, String token) {
        String subject = "이메일 인증 요청";
        String text = "다음 링크를 클릭하여 이메일 인증을 완료해주세요: " +
                "http://localhost:8080/member/confirm-email?token=" + token;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * 임시비밀번호 등록 이메일로 전송
     * @param to
     * @param tempPassword
     */
    public void sendTempPasswordEmail(String to, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("임시 비밀번호 안내");
        message.setText("임시 비밀번호는 " + tempPassword + " 입니다. 로그인 후 비밀번호를 변경해 주세요.");
        mailSender.send(message);
    }

    /**
     * 이메일 인증 완료처리
     * @param
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class}, readOnly = false)
    public void confirmEmail(String email, String emailCode) {
        //String email = jwtUtil.getUsername(token);
        Optional<Member> memberOpt = memberRepository.findByEmail(email);

//        if (!memberOpt.isPresent() || jwtUtil.isExpired(token)) {
//            throw new IllegalStateException("유효하지 않은 토큰입니다.");
//        }

        Member member = memberOpt.get();

        if(!emailCode.equals(member.getEmailCode())) {
            throw new IllegalStateException("유효하지 않은 인증코드 입니다.");
        }


        member.setEmailEnabled(true);  // 이메일 인증 완료 상태로 변경
        memberRepository.update(member);
    }

    /**
     * 이메일 인증 유무 확인
     * @param email
     * @return
     */
    public Member isEmailEnabled(String email) {
        Optional<Member> memberOpt = memberRepository.findByEmail(email);

        if(!memberOpt.isPresent()) {
            throw new IllegalStateException("유효하지 않은 계정 입니다.");
        }

        return memberOpt.get();
    }


    /**
     * userdetailservice의
     * username은 DaoAuthenticationProvider가 토큰에서 꺼내서 설정해줌
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));

        return new CustomUserDetails(member);
    }

}

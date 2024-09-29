package graduation.petshop.domain.member.controller;

import graduation.petshop.common.util.ApiResponse;
import graduation.petshop.domain.member.dto.request.*;
import graduation.petshop.domain.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    /**
     * 회원가입
     * 분리가 필요하다 생각함
     */
    @PostMapping("/member/join")
    public ResponseEntity<Object> memberJoin(@RequestBody @Valid JoinDto joinDto){
        log.info("회원가입 완료");
        ApiResponse joined = memberService.join(joinDto);
        return ResponseEntity.ok(joined);
    }

    /**
     * 이메일로 아이디찾기
     *
     */
    @GetMapping("/member/find-id")
    public ResponseEntity<Object> findByEmail(RequestFindEmailDto requestFindEmailDto){
        ApiResponse member = memberService.findIdByEmail(requestFindEmailDto.getEmail());
        return ResponseEntity.ok(member);
    }

    /**
     * 비밀번호찾기
     *
     */
    @GetMapping("/member/reset-password")
    public ResponseEntity<Object> findByLoginId(RequestFindEmailDto requestFindEmailDto){
        memberService.resetPassword(requestFindEmailDto.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
//    @GetMapping("/member/find-pwd")
//    public ResponseEntity<Object> findByLoginId(RequestFindPwdDto requestFindPwdDto){
//        ApiResponse member = memberService.findPwdByLoginId(requestFindPwdDto.getLoginId());
//        return ResponseEntity.ok(member);
//    }



    /**
     * 회원가입 아이디 중복 확인
     */
    @GetMapping("/member/join/loginid")
    public ResponseEntity<Object> memberJoinLoginId(RequestFindPwdDto requestFindPwdDto){
        ApiResponse member = memberService.checkLoginId(requestFindPwdDto.getLoginId());
        return ResponseEntity.ok(member);
    }

    /**
     * 아이디 수정
     * @param
     * @return
     */
    @PostMapping("/member/updateLoginId")
    public ResponseEntity<Object> updateLoginId(@RequestBody @Valid ModifyMemberLoginIdDto modifyMemberDto){
        memberService.updateLoginId(modifyMemberDto);
        // 수정 성공 시 204 No Content 상태코드 반환
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 비밀번호 수정
     * @param
     * @return
     */
    @PostMapping("/member/updateLoginPw")
    public ResponseEntity<Object> updateLoginPw(@RequestBody @Valid ModifyMemberPwDto modifyMemberDto){
        memberService.updateLoginPw(modifyMemberDto);
        // 수정 성공 시 204 No Content 상태코드 반환
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 이메일 인증코드 발송
     * @param requestFindEmailAndIdDto
     * @return
     */
    @GetMapping("/member/sendCode")
    public ResponseEntity<?> sendConfirmedCode(RequestFindEmailAndIdDto requestFindEmailAndIdDto) {
        memberService.sendConfirmedCode(requestFindEmailAndIdDto);
        return ResponseEntity.ok().body("인증코드가 발송되었습니다.");
    }


    /**
     * 이메일 인증
     * @param email, emailCode
     * @return
     */
    @GetMapping("/member/confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestParam("email") String email, @RequestParam("emailCode") String emailCode) {
        memberService.confirmEmail(email, emailCode);
        return ResponseEntity.ok().body("계정이 활성화되었습니다.");
    }

    /**
     * 이메일 인증여부
     * @param email, emailCode
     * @return
     */
    @GetMapping("/member/isEmailEnabled")
    public ResponseEntity<?> isEmailEnabled(@RequestParam("email") String email) {
        return ResponseEntity.ok().body(memberService.isEmailEnabled(email));
    }

}

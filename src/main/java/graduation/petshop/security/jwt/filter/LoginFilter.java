package graduation.petshop.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import graduation.petshop.common.util.ApiResponse;
import graduation.petshop.domain.member.dto.request.LoginDto;
import graduation.petshop.security.jwt.dto.CustomUserDetails;
import graduation.petshop.security.jwt.dto.UserPrincipal;
import graduation.petshop.security.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager,JwtUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        if(request.getContentType() == null || !request.getContentType().equals("application/json")  ) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        LoginDto loginDto = new LoginDto();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginDto = objectMapper.readValue(messageBody, LoginDto.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //클라이언트 요청에서 username, password 추출
        String loginId = loginDto.getLoginId();
        String password = loginDto.getPassword();

        //검증을 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginId, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("로그인 성공");
        String loginId = customUserDetails.getUsername();
        Long id = customUserDetails.getId();

        String token = jwtUtil.createJwt(loginId, 60 * 60 * 1000L);

        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(new ApiResponse(true, new LoginSuccessResponse(token, loginId, id), null));
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            log.error("Response write error", e);
        }

    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(new ApiResponse(false, null, failed.getMessage()));
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            log.error("Response write error", e);
        }
    }

    private Cookie createCookie(String key, String value){

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    /**
     * 로그인 성공 시 반환할 응답 객체
     */
    public static class LoginSuccessResponse {
        private String token;
        private String userId;
        private Long id;

        public LoginSuccessResponse(String token, String userId, Long id) {
            this.token = token;
            this.userId = userId;
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public String getUserId() {
            return userId;
        }

        public Long getId() { return id; }
    }
}

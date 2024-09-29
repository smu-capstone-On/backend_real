package graduation.petshop.security.jwt.handler;

import graduation.petshop.domain.auth.dto.CustomOAuth2User;
import graduation.petshop.security.jwt.dto.UserPrincipal;
import graduation.petshop.security.jwt.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 일단 안 씀
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{

        log.info("OAuth2 Login 성공!");
        UserPrincipal customUserDetails = (UserPrincipal) authentication.getPrincipal();

        String username = customUserDetails.getUsernameDto();

        String token = jwtUtil.createJwt(username, 60*60*600L);

        response.addCookie(createCookie("Authorization", token));
        //바꿔야 됨
        response.sendRedirect("http://localhost:8080/kakao");

    }


    private Cookie createCookie(String key, String value){

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

}

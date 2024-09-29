package graduation.petshop.domain.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthContorller {

    @RequestMapping("/test")
    public String kakaoOAuth(){
        return "서버 배포 완료";
    }
}

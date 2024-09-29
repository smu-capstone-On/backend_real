package graduation.petshop.common.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsMvcConfig implements WebMvcConfigurer {

        //프론트단 포트번호가 바뀌면 바꾸면 된다.
        @Override
        public void addCorsMappings(CorsRegistry corsRegistry) {

            corsRegistry.addMapping("/**")
                    .exposedHeaders("Set-Cookie")
                    .allowedOrigins("http://localhost:3000");
        }
}

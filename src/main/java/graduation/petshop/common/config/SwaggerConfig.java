package graduation.petshop.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


public class SwaggerConfig implements WebMvcConfigurer {
    private static final String SERVICE_NAME = "Make Project";
    private static final String API_VERSION = "V1";
    private static final String API_DESCRIPTION = "MakeProject API TEST";
    private static final String API_URL = "https://localhost:8080/";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any()) // RequestMapping의 모든 URL LIST
                .paths(PathSelectors.any()) // .any() -> ant(/api/**") /api/**인 URL만 표시
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(SERVICE_NAME) // 서비스명
                .version(API_VERSION)                   // API 버전
                .description(API_DESCRIPTION)           // API 설명
                .termsOfServiceUrl(API_URL)             // 서비스 url
                .licenseUrl("라이센스 표시할 url")
                .build();

    }// API INFO

    // 아래 부분은 WebMvcConfigure 를 상속받아서 설정하는 Mehtod
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        // -- Static resources
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }
}

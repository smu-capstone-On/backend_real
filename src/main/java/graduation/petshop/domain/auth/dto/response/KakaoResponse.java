package graduation.petshop.domain.auth.dto.response;

import java.util.Map;

public class KakaoResponse {

    private final Map<String, Object> attribute;
    public KakaoResponse(Map<String, Object> attribute){

        this.attribute = attribute;
    }

    public String getProviderId() {
        return String.valueOf(attribute.get("id"));
    }
    public String getProvider() {

        return "kakao";
    }

    //이렇게 Map으로 하는게 훨씬 깔끔하네
    public String getNickname() {
        Map<String, Object> account = (Map<String, Object>) attribute.get("kakao_account");

        if (account == null) {
            return null;
        }

        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (profile == null) {
            return null;
        }

        return (String) profile.get("nickname");
    }

    //안쓰지만 혹시해서 만들어놈
    public String getImageUrl() {
        Map<String, Object> account = (Map<String, Object>) attribute.get("kakao_account");

        if (account == null) {
            return null;
        }

        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (profile == null) {
            return null;
        }

        return (String) profile.get("thumbnail_image_url");
    }
}

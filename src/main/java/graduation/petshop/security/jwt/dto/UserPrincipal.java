package graduation.petshop.security.jwt.dto;

import graduation.petshop.domain.auth.dto.UserDto;
import graduation.petshop.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 일단 안 씀
 */
public class UserPrincipal implements UserDetails, OAuth2User {

    private Member member;
    private UserDto userDto;

    public UserPrincipal(Member member, UserDto userDto){
        this.member = member;
        this.userDto = userDto;
    }

    public UserPrincipal(UserDto userDto){
        this.userDto = userDto;
    }

    public UserPrincipal(Member member){
        this.member = member;
    }

    @Override
    public String getName() {
        return userDto.getName();
    }
    public String getUsernameDto(){
        return userDto.getUsername();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userDto.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        if (member == null) {
            throw new IllegalStateException("Member object is null");
        }
        return member.getLoginId();
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

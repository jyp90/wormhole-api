package com.jypark.coding.common.config.security;

import com.jypark.coding.domain.users.entity.User;
import java.util.Collection;
import java.util.Optional;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RedisHash(value = "userPrincipal", timeToLive = 60 * 60 * 24)
@Getter
public class UserPrincipal implements UserDetails {

    @Id
    @Setter
    private String sessionToken;

    private final Long userId;
    private final String username;

    public static UserDetails of(Optional<User> userOptional) {
        final User user = userOptional.get();
        return new UserPrincipal(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
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

    private final String email;
    private final String phone;
    private final String password;
    private final String code;

    public UserPrincipal(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.password = user.getPhone();
        this.code = user.getCode();
    }
}

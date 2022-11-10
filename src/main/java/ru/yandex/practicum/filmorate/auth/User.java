/*
package ru.yandex.practicum.filmorate.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

*/
/**
 * @author Oleg Khilko
 *//*



public class User implements UserDetails {

    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final boolean isCredentialsNonExpired;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isEnabled;
    private final String password;
    private final String username;

    public User(String username,
                String password,
                Set<? extends GrantedAuthority> grantedAuthorities,
                boolean isCredentialsNonExpired,
                boolean isAccountNonExpired,
                boolean isAccountNonLocked,
                boolean isEnabled
    ) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.grantedAuthorities = grantedAuthorities;
        this.isEnabled = isEnabled;
        this.password = password;
        this.username = username;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
*/

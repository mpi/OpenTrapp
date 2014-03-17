package com.github.mpi.users_and_access.infrastructure.spring;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.github.mpi.users_and_access.domain.User;

public class OpenIDUserService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private List<String> allowedUserEmails;

    public void setAllowedUserEmails(List<String> allowedUserEmails) {
        this.allowedUserEmails = allowedUserEmails;
    }

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {

        List<OpenIDAttribute> attributes = token.getAttributes();
        String email = readAttribute(attributes, "Email");
        String fullName = String.format("%s %s", readAttribute(attributes, "FirstName"), readAttribute(attributes, "LastName"));
//        if (!allowedUserEmails.contains(email)) {
//            throw new UsernameNotFoundException(email);
//        }

        return new OpenIDUserAdapter(email, fullName);
    }

    private String readAttribute(List<OpenIDAttribute> attributes, String name) {
        for (OpenIDAttribute attribute : attributes) {
            if (name.equals(attribute.getName())) {
                return attribute.getValues().get(0);
            }
        }
        return null;
    }

    public static final class OpenIDUserAdapter extends User implements UserDetails {
        
        private static final long serialVersionUID = -6345826126136996788L;

        public OpenIDUserAdapter(String email, String fullName) {
            super(email, fullName);
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public String getUsername() {
            return super.username();
        }

        public String getFullName() {
            return super.displayName();
        }
        
        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Arrays.asList(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }
    }
}
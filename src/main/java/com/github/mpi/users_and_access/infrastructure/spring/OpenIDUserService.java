package com.github.mpi.users_and_access.infrastructure.spring;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.github.mpi.users_and_access.domain.User;

public class OpenIDUserService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private List<String> allowedUserEmailsRegexes = Collections.emptyList();

    public void setAllowedUserEmails(List<String> allowedUserEmails) {
        this.allowedUserEmailsRegexes = allowedUserEmails;
    }

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {

        List<OpenIDAttribute> attributes = token.getAttributes();
        String email = emailFrom(attributes);
        if (isNotAllowed(email)) {
            throw new UsernameNotFoundException(email);
        }

        return new OpenIDUserAdapter(userNameFrom(email), fullNameFrom(attributes));
    }

    private String emailFrom(List<OpenIDAttribute> attributes) {
        String email = readAttribute(attributes, "Email");
        return email;
    }

    private String fullNameFrom(List<OpenIDAttribute> attributes) {
        String fullName = String.format("%s %s", readAttribute(attributes, "FirstName"), readAttribute(attributes, "LastName"));
        return fullName;
    }

    private String userNameFrom(String email) {
        return email.split("@")[0];
    }

    private boolean isNotAllowed(String email) {
        return !isAllowed(email);
    }

    private boolean isAllowed(String email) {
        for (String emailRegex : allowedUserEmailsRegexes) {
            if (Pattern.compile(emailRegex).matcher(email).matches())
                return true;
        }
        return false;
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
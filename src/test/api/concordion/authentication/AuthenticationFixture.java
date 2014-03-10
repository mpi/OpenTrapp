package concordion.authentication;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import support.ApiFixture;

import com.github.mpi.users_and_access.infrastructure.spring.OpenIDUserService;

public class AuthenticationFixture extends ApiFixture {

    public void get(String location){
        response = request.redirects().follow(false).when().get(location);
        response.prettyPrint();
    }

    
    public void loggedInAs(final String displayName, final String username){
        
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            
            private static final long serialVersionUID = -1L;

            @Override
            public String getName() {
                return username;
            }
            
            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }
            
            @Override
            public boolean isAuthenticated() {
                return true;
            }
            
            @Override
            public Object getPrincipal() {
                return new OpenIDUserService.OpenIDUser(username, displayName);
            }
            
            @Override
            public Object getDetails() {
                return null;
            }
            
            @Override
            public Object getCredentials() {
                return null;
            }
            
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Arrays.asList(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
            }
        });
    }
    
}

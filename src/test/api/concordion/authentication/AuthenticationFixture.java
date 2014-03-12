package concordion.authentication;

import org.springframework.beans.factory.annotation.Autowired;

import support.ApiFixture;

import com.github.mpi.users_and_access.domain.User;
import com.github.mpi.users_and_access.infrastructure.global.GlobalSecurityContext;

public class AuthenticationFixture extends ApiFixture {

    @Autowired
    private GlobalSecurityContext securityContext;
    
    public void get(String location){
        response = request.redirects().follow(false).when().get(location);
        response.prettyPrint();
    }
    
    public void loggedInAs(final String displayName, final String username){
        securityContext.set(new User(username, displayName));
    }
    
    public void unauthenticated(){
        securityContext.clear();
    }

    public void authenticated(){
        loggedInAs("Homer Simpson", "homer.simpson@springfield.com");
    }
    
}

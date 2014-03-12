package support;

import static com.jayway.restassured.RestAssured.given;

import org.concordion.api.extension.Extensions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.mpi.time_registration.infrastructure.persistence.PersistenceBoundedContext;
import com.github.mpi.users_and_access.domain.User;
import com.github.mpi.users_and_access.infrastructure.global.GlobalSecurityContext;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith(ApiAcceptanceTestRunner.class)
@Extensions(Template.class)
public class ApiFixture {

    @Autowired
    private PersistenceBoundedContext persistenceContext;
    
    @Autowired
    private GlobalSecurityContext securityContext;
    
    protected RequestSpecification request;
    protected Response response;

    public void clear() {
        
        persistenceContext.clear();
        securityContext.set(new User("Homer Simpson", "homer.simpson@springfield.com"));
        
        request = given()
                    .log().all()
                    .contentType(ContentType.JSON);
        response = null;
    }

    public String status() {
        return response.statusLine();
    }

    public boolean isSuccessful(){
        return response.statusCode() >= 200 && response.statusCode() < 300;
    }
    
    public void body(String body) {
        request.body(body);
    }

    public void contentType(String contentType) {
        request.contentType(contentType);
    }

    public void request(String method, String location) {
        
        if("POST".equals(method)){
            post(location);
        }
        
        if("GET".equals(method)){
            get(location);
        }

        if("PUT".equals(method)){
            put(location);
        }
    }

    public void get(String location) {
        response = request.when().get(location);
        response.prettyPrint();
    }

    public void post(String location) {
        response = request.when().post(location);
        response.prettyPrint();
    }

    public void put(String location) {
        
        
        
        response = request.when().put(location);
        response.prettyPrint();
    }

    public String response() {
        return response.asString();
    }
    
    public String headerContent(String header) {
        
        String headerContent = response.header(header);
        
        if(headerContent != null)
            return header + ": " + headerContent;
        
        return "(no header)";
    }

}
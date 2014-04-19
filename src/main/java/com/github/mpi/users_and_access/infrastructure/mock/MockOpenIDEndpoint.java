package com.github.mpi.users_and_access.infrastructure.mock;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Profile("mock-security")
@Controller
public class MockOpenIDEndpoint {

    @Autowired
    private MockOpenIDServer server;
    
    @Autowired
    private HttpServletRequest request;
    
    @RequestMapping(
            value="/MockOpenID/discover",
            method=GET,
            produces="application/xrds+xml"
            )
    public @ResponseBody String yadis(){
        
        return "<xrds:XRDS xmlns:xrds=\"xri://$xrds\" xmlns=\"xri://$xrd*($v*2.0)\">" +
        		    "<XRD>" +
        		        "<Service priority=\"0\">" +
        		            "<Type>http://specs.openid.net/auth/2.0/server</Type>" +
        		            "<Type>http://openid.net/srv/ax/1.0</Type>" +
        		            "<Type>http://specs.openid.net/extensions/ui/1.0/mode/popup</Type>" +
        		            "<Type>http://specs.openid.net/extensions/ui/1.0/icon</Type>" +
        		            "<Type>http://specs.openid.net/extensions/pape/1.0</Type>" +
        		            "<URI>" + serverUrl() + "/MockOpenID/authenticate</URI>" +
        		        "</Service>" +
        		    "</XRD>" +
        		"</xrds:XRDS>";
    }

    private String serverUrl() {
        return request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");
    }
    
    @RequestMapping(
            value="/MockOpenID/authenticate",
            method=RequestMethod.POST
            )
    public void createAssociationHandle(HttpServletRequest req, HttpServletResponse resp) throws Exception{

        String response = server.processRequest(req, resp);
        resp.getWriter().println(response);
    }
    
    @RequestMapping(
            value="/MockOpenID/authenticate",
            method=GET
            )
    public String authenticate(HttpServletRequest req, HttpServletResponse resp) throws Exception{

        String response = server.processRequest(req, resp);
        return "redirect:" + response;
    }

    @RequestMapping(
            value="/MockOpenID/id/{id:.+}",
            method=GET,
            produces="application/xrds+xml"
            )
    public @ResponseBody String id(@PathVariable("id") String id){

        return "<xrds:XRDS xmlns:xrds=\"xri://$xrds\" xmlns=\"xri://$xrd*($v*2.0)\">" +
                "<XRD>" +
                    "<Service priority=\"0\">" +
                        "<Type>http://specs.openid.net/auth/2.0/signon</Type>" +
                        "<Type>http://openid.net/srv/ax/1.0</Type>" +
                        "<Type>http://specs.openid.net/extensions/ui/1.0/mode/popup</Type>" +
                        "<Type>http://specs.openid.net/extensions/ui/1.0/icon</Type>" +
                        "<Type>http://specs.openid.net/extensions/pape/1.0</Type>" +
                        "<URI>" + serverUrl() + "/MockOpenID/authenticate</URI>" +
                    "</Service>" +
                "</XRD>" +
               "</xrds:XRDS>";
    }
    
}

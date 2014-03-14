package com.github.mpi.users_and_access.infrastructure.global;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Profile("acceptance-tests")
@Controller
public class FixedOpenIDEndpoint {

    @RequestMapping(
            value="/MockOpenID/discover",
            method=GET,
            produces="application/xrds+xml"
            )
    public @ResponseBody String yadis(HttpServletRequest req){
        return "<xrds:XRDS xmlns:xrds=\"xri://$xrds\" xmlns=\"xri://$xrd*($v*2.0)\">" +
        		    "<XRD>" +
        		        "<Service priority=\"0\">" +
        		            "<Type>http://specs.openid.net/auth/2.0/server</Type>" +
        		            "<Type>http://openid.net/srv/ax/1.0</Type>" +
        		            "<Type>http://specs.openid.net/extensions/ui/1.0/mode/popup</Type>" +
        		            "<Type>http://specs.openid.net/extensions/ui/1.0/icon</Type>" +
        		            "<Type>http://specs.openid.net/extensions/pape/1.0</Type>" +
        		            "<!--URI>https://www.google.com/accounts/o8/ud</URI-->" +
        		            "<URI>http://localhost:8080/MockOpenID/authenticate</URI>" +
        		        "</Service>" +
        		    "</XRD>" +
        		"</xrds:XRDS>";
    }
    
    @RequestMapping(
            value="/MockOpenID/authenticate",
            method=GET
            )
    public @ResponseBody String authenticate(HttpServletRequest req){

        System.out.println("Received authentication request:");
        for (Object name : req.getParameterMap().keySet()) {
            
            System.out.println(String.format("%s = %s", name, req.getParameter((String)name)));
        }

        System.out.println("Returing fixed authentication response (homer.simpson):");
        
        StringBuilder redirectUrl = new StringBuilder();
        
//        openid.ns:http://specs.openid.net/auth/2.0
//        openid.mode:id_res
//        openid.op_endpoint:https://www.google.com/accounts/o8/ud
//        openid.response_nonce:2014-03-14T07:36:13ZkBcZ93C-8u0-1g
//        openid.return_to:http://localhost:8080/endpoints/v1/authentication/login
//        openid.assoc_handle:1.AMlYA9Uqw50yWUFWSCYevQslqkzDuDXCmusAtI7rC1SitrZcCLkGROMOfVMz_t7FjMvGZgMuugEA5A
        redirectUrl.append(param("openid.ns", "http://specs.openid.net/auth/2.0"));
        redirectUrl.append(param("openid.mode", "id_res"));
        redirectUrl.append(param("openid.op_endpoint", "http://localhost:8080/MockOpenID/authenticate"));
        redirectUrl.append(param("openid.response_nonce", "2014-03-14T07:36:13ZkBcZ93C-8u0-1g"));
        redirectUrl.append(param("openid.return_to", "http://localhost:8080/endpoints/v1/authentication/login"));
        redirectUrl.append(param("openid.assoc_handle", "1.AMlYA9Uqw50yWUFWSCYevQslqkzDuDXCmusAtI7rC1SitrZcCLkGROMOfVMz_t7FjMvGZgMuugEA5A"));
        
//        openid.signed:op_endpoint,claimed_id,identity,return_to,response_nonce,assoc_handle,ns.ext1,ext1.mode,ext1.type.Email,ext1.value.Email,ext1.type.FirstName,ext1.value.FirstName,ext1.type.LastName,ext1.value.LastName
//        openid.sig:ZTVvcENMV2BttrqyVnBvuwgMMERv7uBt6wXBl6Yom+0=
//        openid.identity:https://www.google.com/accounts/o8/id?id=AItOawm8EYfCHby8IRq1SUgEYMZAguJqugg36VM
//        openid.claimed_id:https://www.google.com/accounts/o8/id?id=AItOawm8EYfCHby8IRq1SUgEYMZAguJqugg36VM
//        openid.ns.ext1:http://openid.net/srv/ax/1.0
//        openid.ext1.mode:fetch_response
//        openid.ext1.type.Email:http://schema.openid.net/contact/email
//        openid.ext1.value.Email:mpi.michal.piotrkowski@gmail.com
//        openid.ext1.type.FirstName:http://axschema.org/namePerson/first
//        openid.ext1.value.FirstName:Michał
//        openid.ext1.type.LastName:http://axschema.org/namePerson/last
//        openid.ext1.value.LastName:Piotrkowski
        
        
        return redirectUrl.toString();
    }

    private String param(String param, String value) {
        return String.format("%s=%s", param, value);
    }
    
//    http://ancient-falls-9449.herokuapp.com/j_spring_openid_security_check?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.mode=id_res&openid.op_endpoint=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fud&openid.response_nonce=2014-03-14T07%3A32%3A50Zk768ZQ1AT30gBQ&openid.return_to=http%3A%2F%2Fancient-falls-9449.herokuapp.com%2Fj_spring_openid_security_check&openid.assoc_handle=1.AMlYA9UH86pR6CDYfDFAWSdf-Qe_-N67Mfqb5L7PE_D1d2VVDh66-17TD2pAmfL0VfhhCObTG9HEjg&openid.signed=op_endpoint%2Cclaimed_id%2Cidentity%2Creturn_to%2Cresponse_nonce%2Cassoc_handle%2Cns.ext1%2Cext1.mode%2Cext1.type.email%2Cext1.value.email&openid.sig=gjv%2FpvzwsQAYSwf5EML2FnPJRpkYoIzqLwmaPbmpoEQ%3D&openid.identity=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawltlUyIHBPxnv018c64bcDJzmvLydpGVBc&openid.claimed_id=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawltlUyIHBPxnv018c64bcDJzmvLydpGVBc&openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0&openid.ext1.mode=fetch_response&openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail&openid.ext1.value.email=mpi.michal.piotrkowski%40gmail.com
    
}

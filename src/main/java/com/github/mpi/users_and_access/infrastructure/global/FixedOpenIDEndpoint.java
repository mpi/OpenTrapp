package com.github.mpi.users_and_access.infrastructure.global;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.openid4java.association.Association;
import org.openid4java.association.AssociationException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Profile("no-security")
@Controller
public class FixedOpenIDEndpoint {

    Association assoc;
    
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
            method=RequestMethod.POST
            )
    public void createAssociationHandle(HttpServletResponse resp) throws IOException{

        //mac: X7IkPFVeWFjiv9q65gD9vhuXdKALIbZjD+O4O/22Jek=
        String handle = "1.AMlYA9Uqw50yWUFWSCYevQslqkzDuDXCmusAtI7rC1SitrZcCLkGROMOfVMz_t7FjMvGZgMuugEA5A";
        assoc = Association.generateHmacSha256(handle, 46800);

        resp.getWriter().println("ns:http://specs.openid.net/auth/2.0");
        resp.getWriter().println("session_type:DH-SHA256");
        resp.getWriter().println("assoc_type:HMAC-SHA256");
        resp.getWriter().println(String.format("assoc_handle:%s", assoc.getHandle()));
        resp.getWriter().println("expires_in:46800");
        resp.getWriter().println("dh_server_public:PFbRxROPTSRZIhC5zjVXQQufXZpAJjS5+Jqx83ubp8r7GLBcvou4vNIQJjLfKrFJZA6f5ORcTbZkiDTRz88oKzfmYN07Yplm15zGNk7e0m1hz1yIh4GJVGV6yjMqktpDwmZZYV04Ya3eKlgSsKwODOzd9PUK4N6b05lDXgyVvJo=");
        
        
        String mac = new String(Base64.encodeBase64(assoc.getMacKey().getEncoded()), "utf-8");
        resp.getWriter().println(String.format("enc_mac_key:%s", mac));
        
    }
    
    @RequestMapping(
            value="/MockOpenID/authenticate",
            method=GET
            )
    public String authenticate(HttpServletRequest req) throws AssociationException{

        System.out.println("Received authentication request:");
        for (Object name : req.getParameterMap().keySet()) {
            System.out.println(String.format("%s = %s", name, req.getParameter((String)name)));
        }

        System.out.println("Returing fixed authentication response (homer.simpson):");
        
        
//        openid.ns:http://specs.openid.net/auth/2.0
//        openid.mode:id_res
//        openid.op_endpoint:https://www.google.com/accounts/o8/ud
//        openid.response_nonce:2014-03-14T07:36:13ZkBcZ93C-8u0-1g
//        openid.return_to:http://localhost:8080/endpoints/v1/authentication/login
//        openid.assoc_handle:1.AMlYA9Uqw50yWUFWSCYevQslqkzDuDXCmusAtI7rC1SitrZcCLkGROMOfVMz_t7FjMvGZgMuugEA5A
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

        StringBuilder toSign = new StringBuilder();
        StringBuilder redirectUrl = new StringBuilder("http://localhost:8080/endpoints/v1/authentication/login?");
        redirectUrl.append(signedParam("openid.op_endpoint", "http://localhost:8080/MockOpenID/authenticate", toSign));
        redirectUrl.append(signedParam("openid.claimed_id", "http://localhost:8080/MockOpenID/id/fake", toSign));
        redirectUrl.append(signedParam("openid.identity", "http://localhost:8080/MockOpenID/id/fake", toSign));
        redirectUrl.append(signedParam("openid.return_to", "http://localhost:8080/endpoints/v1/authentication/login", toSign));
        redirectUrl.append(signedParam("openid.response_nonce", String.format("%sZkBcZ93C-8u0-1g", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())), toSign));
        redirectUrl.append(signedParam("openid.assoc_handle", "1.AMlYA9Uqw50yWUFWSCYevQslqkzDuDXCmusAtI7rC1SitrZcCLkGROMOfVMz_t7FjMvGZgMuugEA5A", toSign));
        redirectUrl.append(signedParam("openid.ns.ext1", "http://openid.net/srv/ax/1.0", toSign));
        redirectUrl.append(signedParam("openid.ext1.mode", "fetch_response", toSign));
        redirectUrl.append(signedParam("openid.ext1.type.Email", "http://schema.openid.net/contact/email", toSign));
        redirectUrl.append(signedParam("openid.ext1.value.Email", "homer.simpson@springfield.com", toSign));
        redirectUrl.append(signedParam("openid.ext1.type.FirstName", "http://axschema.org/namePerson/first", toSign));
        redirectUrl.append(signedParam("openid.ext1.value.FirstName", "Homer", toSign));
        redirectUrl.append(signedParam("openid.ext1.type.LastName", "http://axschema.org/namePerson/last", toSign));
        redirectUrl.append(signedParam("openid.ext1.value.LastName", "Simpson", toSign));
        redirectUrl.append(param("openid.ns", "http://specs.openid.net/auth/2.0"));
        redirectUrl.append(param("openid.mode", "id_res"));
        redirectUrl.append(param("openid.signed", "op_endpoint,claimed_id,identity,return_to,response_nonce,assoc_handle,ns.ext1,ext1.mode,ext1.type.Email,ext1.value.Email,ext1.type.FirstName,ext1.value.FirstName,ext1.type.LastName,ext1.value.LastName"));
        redirectUrl.append(param("openid.sig", assoc.sign(toSign.append("\n").toString())));
        
        System.err.println("Signing:'");
        System.err.print(toSign.toString());
        System.err.println("'");
        
        // "ZTVvcENMV2BttrqyVnBvuwgMMERv7uBt6wXBl6Yom+0="
        
        return "redirect:" + redirectUrl.toString();
    }

    private String param(String param, String value) {
        return String.format("%s=%s&", param, value);
    }
    private String signedParam(String param, String value, StringBuilder textToSign) {
        textToSign.append(String.format("%s:%s\n", param.substring(7), value));
        return String.format("%s=%s&", param, value);
    }

    @RequestMapping(
            value="/MockOpenID/id/{id}",
            method=GET,
            produces="application/xrds+xml"
            )
    public @ResponseBody String id(@PathVariable("id") String id){

        System.out.println("Received identity request:");

        return "<xrds:XRDS xmlns:xrds=\"xri://$xrds\" xmlns=\"xri://$xrd*($v*2.0)\">" +
                "<XRD>" +
                    "<Service priority=\"0\">" +
                        "<Type>http://specs.openid.net/auth/2.0/signon</Type>" +
                        "<Type>http://openid.net/srv/ax/1.0</Type>" +
                        "<Type>http://specs.openid.net/extensions/ui/1.0/mode/popup</Type>" +
                        "<Type>http://specs.openid.net/extensions/ui/1.0/icon</Type>" +
                        "<Type>http://specs.openid.net/extensions/pape/1.0</Type>" +
//                        "<URI>https://www.google.com/accounts/o8/ud</URI>" +
                        "<URI>http://localhost:8080/MockOpenID/authenticate</URI>" +
                    "</Service>" +
                "</XRD>" +
               "</xrds:XRDS>";
    }
    
//    http://ancient-falls-9449.herokuapp.com/j_spring_openid_security_check?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.mode=id_res&openid.op_endpoint=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fud&openid.response_nonce=2014-03-14T07%3A32%3A50Zk768ZQ1AT30gBQ&openid.return_to=http%3A%2F%2Fancient-falls-9449.herokuapp.com%2Fj_spring_openid_security_check&openid.assoc_handle=1.AMlYA9UH86pR6CDYfDFAWSdf-Qe_-N67Mfqb5L7PE_D1d2VVDh66-17TD2pAmfL0VfhhCObTG9HEjg&openid.signed=op_endpoint%2Cclaimed_id%2Cidentity%2Creturn_to%2Cresponse_nonce%2Cassoc_handle%2Cns.ext1%2Cext1.mode%2Cext1.type.email%2Cext1.value.email&openid.sig=gjv%2FpvzwsQAYSwf5EML2FnPJRpkYoIzqLwmaPbmpoEQ%3D&openid.identity=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawltlUyIHBPxnv018c64bcDJzmvLydpGVBc&openid.claimed_id=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid%3Fid%3DAItOawltlUyIHBPxnv018c64bcDJzmvLydpGVBc&openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0&openid.ext1.mode=fetch_response&openid.ext1.type.email=http%3A%2F%2Fschema.openid.net%2Fcontact%2Femail&openid.ext1.value.email=mpi.michal.piotrkowski%40gmail.com

}

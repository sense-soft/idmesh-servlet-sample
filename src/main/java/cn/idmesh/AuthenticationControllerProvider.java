package cn.idmesh;

import cn.idmesh.AuthenticationController;

import javax.servlet.ServletConfig;

public class AuthenticationControllerProvider {


    public static AuthenticationController getInstance(ServletConfig config) {
        String domain = config.getServletContext().getInitParameter("com.idmesh.domain");
        String clientId = config.getServletContext().getInitParameter("com.idmesh.clientId");
        String clientSecret = config.getServletContext().getInitParameter("com.idmesh.clientSecret");

        return AuthenticationController.newBuilder(domain, clientId, clientSecret)
                .build();
    }
}

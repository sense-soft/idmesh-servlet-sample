package cn.idmesh;

import cn.idmesh.exception.IdentityVerificationException;
import org.apache.hc.core5.net.URIBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {

    private String redirectOnSuccess;
    private String redirectOnFail;

    private AuthenticationController authenticationController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.redirectOnFail = "/error";
        this.redirectOnSuccess = "/portal/home";
        this.authenticationController = AuthenticationControllerProvider.getInstance(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理回调逻辑，获取token 写在sdk里
        try {
            Tokens token = authenticationController.handle(req, resp);
            SessionUtils.set(req, "accessToken", token.getAccessToken());
            SessionUtils.set(req, "idToken", token.getIdToken());

            resp.sendRedirect(redirectOnSuccess);
        } catch (IdentityVerificationException e) {
            e.printStackTrace();
            try {
                URIBuilder builder = new URIBuilder(redirectOnFail);
                builder.addParameter("error", e.getMessage());
                resp.sendRedirect(builder.build().toString());
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

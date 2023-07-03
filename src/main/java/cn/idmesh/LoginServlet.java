package cn.idmesh;

import org.apache.hc.core5.net.URIBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet({"/login"})
public class LoginServlet extends HttpServlet {

    private AuthenticationController authenticationController;

    @Override
    public void init(ServletConfig config) {
        authenticationController = AuthenticationControllerProvider.getInstance(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            URIBuilder builder = new URIBuilder(req.getRequestURL().toString());
            builder.setScheme(req.getScheme());
            if (!authenticationController.isDefaultPort(req.getScheme(), req.getServerPort())) {
                builder.setPort(req.getServerPort());
            }
            builder.setPath(req.getContextPath() + "/callback");
            String authorizeUrl = authenticationController.buildAuthorizeUrl(req, resp, builder.toString()).build();
            resp.sendRedirect(authorizeUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

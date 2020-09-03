package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class UrlRestHeaderAuthFilter extends AbstractHeaderAuthFilter {


    public UrlRestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected UsernamePasswordAuthenticationToken getToken(HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        log.debug("Authenticating user: " + username);
        var token = new UsernamePasswordAuthenticationToken(username, password);

        if (!StringUtils.isEmpty(username)) {
            return token;
        } else {
            return null;
        }

    }
}

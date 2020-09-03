package guru.sfg.brewery.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpHeaderAuthFilter extends RestHeaderAuthFilter {

    public HttpHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return super.attemptAuthentication(request, response);
    }

    private String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-secret");
    }

    private String getUsername(HttpServletRequest request) {
        return request.getHeader("Api-key");
    }
}

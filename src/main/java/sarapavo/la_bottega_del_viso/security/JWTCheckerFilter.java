package sarapavo.la_bottega_del_viso.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import sarapavo.la_bottega_del_viso.customer.CustomerService;
import sarapavo.la_bottega_del_viso.exceptions.UnauthorizedException;
import sarapavo.la_bottega_del_viso.tools.JWT;
import sarapavo.la_bottega_del_viso.user.User;
import sarapavo.la_bottega_del_viso.user.UserService;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    @Autowired
    private JWT jwt;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire token nell'Authorization Header nel formato corretto!");

        String accessToken = authHeader.substring(7);

        try {
            jwt.verifyToken(accessToken);

            //autorizzazione
            String userId = jwt.getIdFromToken(accessToken);
            Long userIdLong = Long.parseLong(userId); // Convert String to Long
            User currentUser = this.userService.findById(userIdLong);

            Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (UnauthorizedException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(ex.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/register/**", request.getServletPath());
    }

}

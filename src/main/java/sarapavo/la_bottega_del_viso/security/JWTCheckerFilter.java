package sarapavo.la_bottega_del_viso.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import sarapavo.la_bottega_del_viso.exceptions.UnauthorizedException;
import sarapavo.la_bottega_del_viso.tools.JWT;
import sarapavo.la_bottega_del_viso.user.User;
import sarapavo.la_bottega_del_viso.user.UserService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    @Autowired
    private JWT jwt;
    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            //elimina i primi 7 caratteri "Bearer "
            String accessToken = authHeader.substring(7);
            try {
                jwt.verifyToken(accessToken);

                //autorizzazione
                String userId = jwt.getIdFromToken(accessToken);
                Long userIdLong = Long.parseLong(userId);
                User currentUser = this.userService.findById(userIdLong);


                List<String> roles = jwt.getRolesFromToken(accessToken);
                Collection<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);

            } catch (Exception e) {
                throw new UnauthorizedException("Token non valido");
            }
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}

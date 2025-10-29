package buildweek5.BW_3_BE.security;

import buildweek5.BW_3_BE.entities.Utente;
import buildweek5.BW_3_BE.exceptions.UnauthorizedException;
import buildweek5.BW_3_BE.services.UtenteService;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private UtenteService utentiService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Inserire token nell'Authorization Header nel formato corretto!");
        }

        String accessToken = authHeader.substring(7);

        jwtTools.verifyToken(accessToken);

        String id = jwtTools.extractIdFromToken(accessToken);
        Utente utenteCorrente = this.utentiService.findById(Long.parseLong(id));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                utenteCorrente,
                null,
                utenteCorrente.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        AntPathMatcher matcher = new AntPathMatcher();
        List<String> excludedPaths = Arrays.asList("/auth/**", "/import/**");
        return excludedPaths.stream().anyMatch(p-> matcher.match(p,path));
    }
}

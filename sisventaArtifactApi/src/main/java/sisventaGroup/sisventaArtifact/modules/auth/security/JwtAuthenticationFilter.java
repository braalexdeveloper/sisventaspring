package sisventaGroup.sisventaArtifact.modules.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("========== JWT FILTER ==========");
        System.out.println("URI: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        System.out.println("Authorization: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("NO HAY TOKEN");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        System.out.println("TOKEN RECIBIDO");

        String email = jwtService.extractUsername(token);

        System.out.println("EMAIL EXTRAIDO: " + email);

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(email);

        System.out.println("USUARIO ENCONTRADO: " + userDetails.getUsername());

        if (jwtService.isTokenValid(token, userDetails)) {

            System.out.println("TOKEN VALIDO");

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            System.out.println("AUTHENTICATION CREADA");

        } else {

            System.out.println("TOKEN INVALIDO");
        }

        filterChain.doFilter(request, response);
    }
}

package tech.csm.final_assmnt.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/login");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            forbid(response, "Missing or invalid Authorization header");
            return;
        }

//extracting token
        String token = authHeader.substring(7);

        Claims claims;
        try {
            // Validating token & extracting claims
            claims = jwtUtil.extractClaims(token);
        } catch (Exception e) {
            forbid(response, "Invalid or expired token");
            return;
        }

        // reading required claims
        String role = claims.get("role", String.class);
        Integer userId = claims.get("userId", Integer.class);
        String username = claims.get("username", String.class);

        String uri = request.getRequestURI();
        String method = request.getMethod();

        if (!isAuthorized(role, uri, method)) {
            forbid(response, "Access denied");
            return;
        }

        // marking request as authenticated
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // making claims available to controllers if needed
        request.setAttribute("claims", claims);

        // filter chain
        filterChain.doFilter(request, response);
    }

    private boolean isAuthorized(String role, String uri, String method) {

        if ("ROLE_ADMIN".equals(role)) {
            return true; // admin has full access
        }

        if ("ROLE_MANAGER".equals(role)) {
            return uri.startsWith("/api/assign");
        }

        if ("ROLE_EMPLOYEE".equals(role)) {
            return uri.startsWith("/api/projects/view");
        }

        return false;
    }

    private void forbid(HttpServletResponse response, String message)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter()
                .write("{\"error\":\"" + message + "\"}");
    }
}

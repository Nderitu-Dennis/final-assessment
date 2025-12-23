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
        String uri = request.getRequestURI();
        //skip jwt for login API & jsp(if it doesnt start with /api/)
        return
                uri.equals("/api/login") ||
                        !uri.startsWith("/api/");
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

        // Extracting token
        String token = authHeader.substring(7);

        Claims claims;
        try {
            // Validating token & extracting claims
            claims = jwtUtil.extractClaims(token);
        } catch (Exception e) {
            forbid(response, "Invalid or expired token");
            return;
        }

//        making claims available to controllers
        request.setAttribute("claims", claims);

        // Reading required claims
        String role = claims.get("role", String.class);
        Integer userId = claims.get("userId", Integer.class);
        String username = claims.get("username", String.class);

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // Check authorization based on role
        if (!isAuthorized(role, uri, method)) {
            forbid(response, "Access denied");
            return;
        }

        // Marking request as authenticated
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter chain
        filterChain.doFilter(request, response);
    }

    private boolean isAuthorized(String role, String uri, String method) {

        // ADMIN has full access to all APIs
        if ("ROLE_ADMIN".equals(role)) {
            return true;
        }

        // MANAGER can view employees/projects and assign employees to projects
        if ("ROLE_MANAGER".equals(role)) {
            return uri.equals("/api/assign") ||
                    uri.equals("/api/employees") ||
                    uri.equals("/api/projects");
        }

        // EMPLOYEE can only view their assigned projects
        if ("ROLE_EMPLOYEE".equals(role)) {
            return uri.equals("/api/projects/view")
                     && method.equals("GET");
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
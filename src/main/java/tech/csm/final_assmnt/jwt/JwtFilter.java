package tech.csm.final_assmnt.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.DeclarePrecedence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/api/login"); //skip this
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        log.warn("Filter start...");
        String authHeader = request.getHeader("Authorization");

        log.warn(authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            forbid(response, "Missing or invalid Authorization header");
            return;
        }

        String token1= authHeader;
        String token = authHeader.substring(7);
        System.out.println("token.. " + token1);
        System.out.println("token.. aftr split " + token);


        Claims claims;
        try {
            claims = jwtUtil.extractClaims(token);
        } catch (Exception e) {
            forbid(response, "Invalid or expired token");
            return;
        }

        String role = claims.get("role", String.class);
        String uri = request.getRequestURI();
        String method = request.getMethod();

        log.warn("Authorizing....");

        if (!isAuthorized(role, uri, method)) {
            forbid(response, "Access denied");
            return;
        }

        log.warn("Get claims....");
        request.setAttribute("claims", claims);
        filterChain.doFilter(request, response);
    }

    private boolean isAuthorized(String role, String uri, String method) {

        if ("ROLE_ADMIN".equals(role)) {
            return true;
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

package com.notes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.notes.api.entities.User;
import com.notes.api.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseFilter extends OncePerRequestFilter {

    private final static String TOKEN_HEADER = "Authorization";

    private static final List<String> ALLOWED_URLS = Arrays.asList(
            "/signon",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    private static final Logger logger = LoggerFactory.getLogger(FirebaseFilter.class);

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authToken = request.getHeader(TOKEN_HEADER);

        if (authToken == null || authToken.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "no firebase token");
            return;
        }

        try {
            Authentication authentication = getAndValidateAuthentication(authToken);
            if (authentication.getPrincipal() == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "user is not authorised");
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }


        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return ALLOWED_URLS.stream().anyMatch(p -> new AntPathMatcher().match(p, path));
    }

    private Authentication getAndValidateAuthentication(String authToken) throws Exception {
        FirebaseToken firebaseToken = getFirebaseToken(authToken);
        User user = userService.getUserById(firebaseToken.getUid());

        if (user == null) {
            return new UsernamePasswordAuthenticationToken(null, firebaseToken, new ArrayList<>());
        }

        return new UsernamePasswordAuthenticationToken(user, firebaseToken, new ArrayList<>());
    }

    private FirebaseToken getFirebaseToken(String authToken) throws Exception {
        return FirebaseAuth.getInstance().verifyIdToken(authToken);
    }
}

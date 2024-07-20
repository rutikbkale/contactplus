package com.contactplus.securityConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.contactplus.entities.Provider;
import com.contactplus.entities.User;
import com.contactplus.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthenticationHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        // user.getAttributes().forEach((key, value) -> {
        // System.out.println(key + " " + value);
        // });

        String email = user.getAttribute("email").toString();
        String name = user.getAttribute("name").toString();

        User saveUser = new User();
        saveUser.setEmail(email);
        saveUser.setName(name);
        saveUser.setProvider(Provider.GOOGLE);

        User oldUser = userRepository.findByEmail(email);

        if (oldUser == null) {
            userRepository.save(saveUser);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/contactplus/user/dashboard");

    }

}

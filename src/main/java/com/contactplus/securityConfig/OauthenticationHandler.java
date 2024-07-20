package com.contactplus.securityConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

        // getting oauthtocken from authentication
        var oauthToken = (OAuth2AuthenticationToken) authentication;

        // getting provider
        String authorizationProvider = oauthToken.getAuthorizedClientRegistrationId();

        var user = (DefaultOAuth2User) authentication.getPrincipal();

        // creating user
        User savedUser = new User();

        // if provider was google then add their information to database
        if (authorizationProvider.equalsIgnoreCase("google")) {

            savedUser.setEmail(user.getAttribute("email").toString());
            savedUser.setName(user.getAttribute("name").toString());
            savedUser.setProvider(Provider.GOOGLE);

            // if provider was google then add their information to database
        } else if (authorizationProvider.equalsIgnoreCase("github")) {

            savedUser.setEmail(user.getAttribute("email") != null
                    ? user.getAttribute("email").toString()
                    : user.getAttribute("login").toString() + "@gmail.com");
            savedUser.setName(user.getAttribute("login").toString());
            savedUser.setProvider(Provider.GITHUB);
        }

        // checking if that email having already account or not
        User oldUser = userRepository.findByEmail(savedUser.getEmail());

        if (oldUser == null) {
            userRepository.save(savedUser);
        }

        // redirecting to the user dashboard
        new DefaultRedirectStrategy().sendRedirect(request, response, "/contactplus/user/dashboard");

    }

}

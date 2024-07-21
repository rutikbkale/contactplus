package com.contactplus.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class Helper {

    // getting authenticated email id as username
    public static String getAuthenticateUserName(Authentication authentication) {

        String username = "";

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

            // fetching providers token name to identify whether it is Google or GitHub
            String providerId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            // checking which provider provides this service
            if (providerId.equalsIgnoreCase("google")) {
                username = oAuth2AuthenticationToken.getPrincipal().getAttribute("email");
            } else if (providerId.equalsIgnoreCase("github")) {
                username = oAuth2AuthenticationToken.getPrincipal().getAttribute("email") != null
                        ? oAuth2AuthenticationToken.getPrincipal().getAttribute("email")
                        : oAuth2AuthenticationToken.getPrincipal().getAttribute("login") + "@github.com";
            }

        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        } else {
            username = authentication.getName();
        }

        return username;
    }

}

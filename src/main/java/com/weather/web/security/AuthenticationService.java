package com.weather.web.security;

import com.weather.web.service.UserService;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author ryagudin
 */
public class AuthenticationService implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        try {
            com.weather.web.domain.User user = userService.getUserByName(String.valueOf(auth.getPrincipal()));
            if (user != null && String.valueOf(auth.getPrincipal()).equals(user.getUserName()) && toHexString(String.valueOf(auth.getCredentials()).getBytes("UTF-8")).equals(user.getPassword())) {
                List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
                grantedAuths.add(new SimpleGrantedAuthority(user.isAdministrator() ? "ROLE_ADMIN" : "ROLE_USER"));
                org.springframework.security.core.userdetails.User principalUser = new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuths);
                return new UsernamePasswordAuthenticationToken(principalUser, null, grantedAuths);
            }
        } catch (FileNotFoundException ex) {
            logger.error("FileNotFoundException: " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            logger.error("UnsupportedEncodingException: " + ex.getMessage());
        }
        logger.error("Throwing bad credentials for login: " + String.valueOf(auth.getPrincipal()));
        throw new BadCredentialsException("Trying to fool me? Huh?");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String toHexString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v / 16];
            hexChars[j * 2 + 1] = hexArray[v % 16];
        }
        return new String(hexChars);
    }
}

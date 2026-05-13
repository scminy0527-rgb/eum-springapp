package com.app.springapp.handler;

import com.app.springapp.domain.dto.JwtTokenDTO;
import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (authentication instanceof OAuth2AuthenticationToken authToken) {
            OAuth2User oauth2User = authToken.getPrincipal();
            Map<String, Object> attributes = oauth2User.getAttributes();
            String socialUserProvider = authToken.getAuthorizedClientRegistrationId();

            String userEmail = null;
            String socialUserProviderId = null;
            String userName = null;

            if ("google".equals(socialUserProvider)) {
                userEmail = (String) attributes.get("email");
                socialUserProviderId = (String) attributes.get("sub");
                userName = (String) attributes.get("name");
            } else if ("kakao".equals(socialUserProvider)) {
                socialUserProviderId = String.valueOf(attributes.get("id"));
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                userEmail = (String) kakaoAccount.get("email");
                userName = (String) profile.get("nickname");
            } else if ("naver".equals(socialUserProvider)) {
                Map<String, Object> naverResponse = (Map<String, Object>) attributes.get("response");
                socialUserProviderId = (String) naverResponse.get("id");
                userEmail = (String) naverResponse.get("email");
                userName = (String) naverResponse.get("name");
            }

            UserDTO userDTO = new UserDTO();
            userDTO.setUserEmail(userEmail);
            userDTO.setSocialUserProviderId(socialUserProviderId);
            userDTO.setUserName(userName);
            userDTO.setSocialUserProvider(socialUserProvider);

            JwtTokenDTO jwtTokenDTO = authService.socialLogin(userDTO);

            ResponseCookie accessTokenCookie = ResponseCookie
                    .from("accessToken", jwtTokenDTO.getAccessToken())
                    .httpOnly(true)
                    .sameSite("Lax")
                    .path("/")
                    .secure(false)
                    .maxAge(60 * 60 * 24)
                    .build();

            ResponseCookie refreshTokenCookie = ResponseCookie
                    .from("refreshToken", jwtTokenDTO.getRefreshToken())
                    .httpOnly(true)
                    .sameSite("Lax")
                    .path("/")
                    .secure(false)
                    .maxAge(60 * 60 * 24 * 30)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000");
        }
    }
}

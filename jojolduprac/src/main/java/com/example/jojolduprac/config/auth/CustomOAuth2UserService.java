package com.example.jojolduprac.config.auth;


import com.example.jojolduprac.config.auth.dto.OAuthAttributes;
import com.example.jojolduprac.config.auth.dto.SessionUser;
import com.example.jojolduprac.domain.user.User;
import com.example.jojolduprac.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    // 로그인이 완료되면, endpoint로 CustomOAuth2UserService가 불리게 된다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        
        /**
         * System.out.println("oAuth2User = " + oAuth2User.toString());
         * oAuth2User = Name: [107447746543872541274], Granted Authorities: [[OAUTH2_USER, SCOPE_https://www.googleapis.com/auth/userinfo.email, SCOPE_https://www.googleapis.com/auth/userinfo.profile, SCOPE_openid]], 
         * User Attributes: [{sub=107447746543872541274, name=박성수, given_name=성수, family_name=박, picture=https://lh3.googleusercontent.com/a/ACg8ocIGGnciVvE73V6lbwH6Y33fu79NJQIDctqvdzXv2TJC=s96-c, email=yenkee1219@gmail.com, email_verified=true, locale=ko}]
         */

        // 구글이면 구글로, 네이버면 네이버로
        String registrationId = userRequest.getClientRegistration().getRegistrationId();



        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        // 성공했다면, httpSession에 user의 키로 세션유저를 저장해둠

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    // 구글 사용자 정보가 업데이트 되었을 경우를 대비한 구현체, 사용자 이름 혹은 사진이 변경된다면, User Entity에도 반영해주는 것이다.
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}

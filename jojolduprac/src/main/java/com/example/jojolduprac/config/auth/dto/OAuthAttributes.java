package com.example.jojolduprac.config.auth.dto;

import com.example.jojolduprac.domain.user.Role;
import com.example.jojolduprac.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        /**
         *         System.out.println("registrationId = " + registrationId);
         *         System.out.println("userNameAttributeName = " + userNameAttributeName);
         *         registrationId = google
         *         userNameAttributeName = sub
         */


        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        /**
         * for (String s : attributes.keySet()) {
         *             System.out.println(s +""+attributes.get(s));
         *
         * }
         *
         * sub107447746543872541274
         * name박성수
         * given_name성수
         * family_name박
         * picturehttps://lh3.googleusercontent.com/a/ACg8ocIGGnciVvE73V6lbwH6Y33fu79NJQIDctqvdzXv2TJC=s96-c
         * emailyenkee1219@gmail.com
         * email_verifiedtrue
         * localeko
         */
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST) // 처음 가입하는 시점에는 ROLE을 GUEST로 줌
                .build();
    }
}

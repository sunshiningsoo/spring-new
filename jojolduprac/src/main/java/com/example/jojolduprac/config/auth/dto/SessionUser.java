package com.example.jojolduprac.config.auth.dto;

import com.example.jojolduprac.domain.user.User;
import lombok.Getter;
import org.hibernate.internal.SessionOwnerBehavior;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    // 세션안에서 보내고 받으려면 직렬화가 되어 있어야 한다..!
    // 인증된 사용자 정보 필드만 필요함

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

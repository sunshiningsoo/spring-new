package com.example.jojolduprac.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"); // 스프링 시큐리티에서는 ROLE_이 항상 앞에 있어야 한다.


    // 여기에서 EnumType이 String 값으로 저장될 것이라는 것을 지정하지 않으니까 jpa가 save를 하지 못하는 문제가 발생했음
    @Enumerated(EnumType.STRING)
    private final String key;

    @Enumerated(EnumType.STRING)
    private final String title;
}

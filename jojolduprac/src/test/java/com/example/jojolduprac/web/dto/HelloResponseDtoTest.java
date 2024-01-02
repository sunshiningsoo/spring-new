package com.example.jojolduprac.web.dto;

import org.assertj.core.api.Assertions; // Junit이 아닌 assertj를 활용한다.
import org.junit.Test;


public class HelloResponseDtoTest {

    @Test
    public void lombok_func_test() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        Assertions.assertThat(dto.getName()).isEqualTo(name);
        Assertions.assertThat(dto.getAmount()).isEqualTo(amount);
    }
}

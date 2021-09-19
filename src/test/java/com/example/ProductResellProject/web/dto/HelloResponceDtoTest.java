package com.example.ProductResellProject.web.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HelloResponceDtoTest {

    @Test
    public void 룸북_test() {
        String name = "test";
        int amount = 1000;

        HelloResponceDto dto = new HelloResponceDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
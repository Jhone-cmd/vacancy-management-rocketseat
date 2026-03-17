package br.com.jhonecmd.vacancy_management.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FirstTest {

    @Test
    @DisplayName("should be able possible to calculate two numbers")
    public void should_be_able_possible_to_calculate_two_numbers() {
        var result = calculate(3, 2);
        assertEquals(result, 5);
    }

    public static int calculate(int num1, int num2) {
        return num1 + num2;
    }
}

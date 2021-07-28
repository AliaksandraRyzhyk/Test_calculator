import com.it_academy.practice.junit_basics.Calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.stream.Stream;


public class CalculatorTest {
    Calculator calculator = new Calculator(8, 4);

    //2a.Проверить очевидные значения
    @Test
    void testMinus() {
        assertEquals(4f, calculator.calculate('-'));
    }

    @Test
    void testPlus() {
        assertEquals(12f, calculator.calculate('+'));
    }

    @Test
    void testMultiply() {
        assertEquals(32f, calculator.calculate('*'));
    }

    @Test
    void testDivision() {
        assertEquals(2f, calculator.calculate('/'));
    }

    //2b.Проверить возможность ввода невалидных данных
    @Test
    void testInvalidFormat() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("testInvalidFormat"));
        assertThrows(IllegalArgumentException.class, () -> new Calculator(Integer.parseInt(reader.readLine()), Integer.parseInt(reader.readLine())).calculate('-'));
    }

    //2c.Проверить значения, которые выходят за рамки диапазона выбранного типа данных
    @Test
    void testMaxLength() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("testMaxLength"));
        assertThrows(IllegalArgumentException.class, () -> new Calculator(Integer.parseInt(reader.readLine()), Integer.parseInt(reader.readLine())).calculate('+'));
    }

    //2d.Проверить операцию деления на типичную ошибку
    @Test
    void testException() throws ArithmeticException {
        Exception exception = assertThrows(ArithmeticException.class, () -> new Calculator(8, 0).calculate('/'));
        System.out.println(exception.getMessage());
    }

    //3.Написать отдельный метод, который добавляет возможность отправлять на вход любое количество
    // параметров (либо заранее указанное, либо неопределенное)
    @ParameterizedTest
    @MethodSource("argument")
    void data(float expected, int a, int b, char c) {
        assertEquals(expected, new Calculator(a, b).calculate(c));
    }

    private static Stream<Arguments> argument() {
        return Stream.of(
                Arguments.of(3, 9, 3, '/'),
                Arguments.of(5, 3, 2, '+'),
                Arguments.of(12, 6, 2, '*')
        );
    }

    //4.Добавить операции возведения в степень и взятие корня по аналогии с существующими
    @Test
    void testPower() {
        assertEquals(4096, calculator.calculate('^'));
    }

    @Test
    void testSQRT() {
        assertEquals(2, calculator.calculate('#'));
    }

    //5.Дописать юнит-тесты на новый функционал
    //5a.Корень из отрицательного числа
    Calculator calculator1 = new Calculator(8, -4);

    @Test
    void testSQRTNegativeNumber() {
        assertEquals(Float.NaN, calculator1.calculate('#'));
    }

    //5b.Возведение в степень 1
    Calculator calculator2 = new Calculator(8, 1);

    @Test
    void testPowerOne() {
        assertEquals(8f, calculator2.calculate('^'));
    }

    //5c.Возведение в степень 0
    Calculator calculator3 = new Calculator(8, 0);

    @Test
    void testPowerNull() {
        assertEquals(1, calculator3.calculate('^'));
    }
}

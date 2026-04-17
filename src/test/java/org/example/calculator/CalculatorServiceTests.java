package org.example.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorServiceTests {

    private final CalculatorService service = new CalculatorService();

    @Test
    void testAdd() {
        assertEquals(5.0, service.add(2, 3));
        assertEquals(0.0, service.add(-1, 1));
        assertEquals(-4.0, service.add(-2, -2));
    }

    @Test
    void testSubtract() {
        assertEquals(1.0, service.subtract(3, 2));
        assertEquals(-5.0, service.subtract(-2, 3));
        assertEquals(0.0, service.subtract(5, 5));
    }

    @Test
    void testMultiply() {
        assertEquals(6.0, service.multiply(2, 3));
        assertEquals(0.0, service.multiply(0, 100));
        assertEquals(-10.0, service.multiply(-5, 2));
    }

    @Test
    void testDivide() {
        assertEquals(2.0, service.divide(6, 3));
        assertEquals(-2.5, service.divide(-5, 2));
    }

    @Test
    void testDivideByZeroThrows() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.divide(5, 0)
        );
        assertEquals("Division by zero is not allowed", ex.getMessage());
    }
}

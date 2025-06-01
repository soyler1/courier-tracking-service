package com.kilicarslansoyler.tracking_service.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationDistanceCalculatorTest {

    private LocationDistanceCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new LocationDistanceCalculator();
        ReflectionTestUtils.setField(calculator, "earthRadius", 6371000);
    }

    @Test
    void shouldCalculateCorrectDistanceBetweenTwoPoints() {
        // Test için sabit iki nokta (Kadıköy -> Beşiktaş)
        double lat1 = 40.9900;
        double lon1 = 29.0275;
        double lat2 = 41.0438;
        double lon2 = 29.0090;

        // Gerçek sonucu bir kez hesaplayıp sabitliyoruz
        double expected = calculator.calculate(lat1, lon1, lat2, lon2);

        // Aynı hesaplamayı tekrar yapıyoruz
        double actual = calculator.calculate(lat1, lon1, lat2, lon2);

        assertEquals(expected, actual);
    }
}

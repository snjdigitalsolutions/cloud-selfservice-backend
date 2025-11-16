package com.snjdigitalsolutions.cloudselfservicebackend.utility;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AddChangeDestroyUtilityTest {

    @Test
    void matchLine() {
        // Arrange
        String testLine = "      Plan: 3 to add, 12 to change, 9 to destroy.     ";

        // Act
        Optional<String> result = new AddChangeDestroyUtility().matchLine(testLine);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("3 to add, 12 to change, 9 to destroy", result.get());
    }

    @Test
    void numberToAdd() {
        // Arrange
        String testLine = "      Plan: 3 to add, 12 to change, 9 to destroy.     ";
        AddChangeDestroyUtility addChangeDestroyUtility = new AddChangeDestroyUtility();

        // Act
        Integer result = addChangeDestroyUtility.numberToAdd(testLine);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.intValue());
    }

    @Test
    void numberToChange() {
        // Arrange
        String testLine = "      Plan: 3 to add, 12 to change, 9 to destroy.     ";
        AddChangeDestroyUtility addChangeDestroyUtility = new AddChangeDestroyUtility();

        // Act
        Integer result = addChangeDestroyUtility.numberToChange(testLine);

        // Assert
        assertNotNull(result);
        assertEquals(12, result.intValue());
    }

    @Test
    void numberToDestroy() {
        // Arrange
        String testLine = "      Plan: 3 to add, 12 to change, 9 to destroy.     ";
        AddChangeDestroyUtility addChangeDestroyUtility = new AddChangeDestroyUtility();

        // Act
        Integer result = addChangeDestroyUtility.numberToDestroy(testLine);

        // Assert
        assertNotNull(result);
        assertEquals(9, result.intValue());
    }

    @Test
    void noNumberInLine() {
        // Arrange
        String testLine = "      Plan: to add, to change, to destroy.     ";
        AddChangeDestroyUtility addChangeDestroyUtility = new AddChangeDestroyUtility();

        // Act
        Integer result = addChangeDestroyUtility.numberToAdd(testLine);

        // Assert
        assertNotNull(result);
        assertEquals(-1, result.intValue());
    }
}
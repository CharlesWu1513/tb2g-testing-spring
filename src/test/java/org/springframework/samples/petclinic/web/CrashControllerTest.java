package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CrashControllerTest {
    @InjectMocks
    CrashController controller;

    @Test
    void triggerException() {
        assertThrows(RuntimeException.class, ()->controller.triggerException());
    }
}
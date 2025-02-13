package org.springframework.samples.petclinic.sfg;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("component-scan")
@SpringJUnitConfig(classes = HearingInterpreterComponentScanTest.TestConfig.class)
public class HearingInterpreterComponentScanTest {
    @Profile("component-scan")
    @Configuration
    @ComponentScan("org.springframework.samples.petclinic.sfg")
    static class TestConfig{
    }
    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIHeard(){
        String word = hearingInterpreter.whatIHeard();

        assertEquals("Laurel", word);
    }
}
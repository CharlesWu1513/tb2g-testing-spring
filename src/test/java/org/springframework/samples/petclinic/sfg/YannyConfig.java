package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("base-test")
@Component
public class YannyConfig {
    @Bean
    YannyWordProducer yannyWordProducer(){
        return new YannyWordProducer();
    }
}

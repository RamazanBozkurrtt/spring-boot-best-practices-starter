package org.project.bestpractice.Indicators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ExternalApiHealthIndicator implements HealthIndicator {


    //This is a scenario set up to check the status of other services.
    //It has no function in this study; it is merely used for this purpose.
    @Override
    public Health health() {
        //there should be "service checking method" instead of "true" condition
        if (true) {
            return Health.up().build();
        }
        return Health.down().withDetail("Error", "Servis cevap vermiyor").build();


    }
}

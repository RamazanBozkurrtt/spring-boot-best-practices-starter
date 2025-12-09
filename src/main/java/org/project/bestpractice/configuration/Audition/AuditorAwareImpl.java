package org.project.bestpractice.configuration.Audition;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        //The name will be taken from Spring security context when i add Spring Security to project
        return Optional.of("Ramazan (Admin)");
    }
}

package com.assignment.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class UserAuditing implements AuditorAware<String> {

	public Optional<String> getCurrentAuditor() {
		// If you are using spring-security, you may get this from SecurityContext.
		return Optional.of("System");
	}

}

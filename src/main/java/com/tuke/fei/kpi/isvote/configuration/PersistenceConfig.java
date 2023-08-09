package com.tuke.fei.kpi.isvote.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.common.service.SecurityService;
import com.tuke.fei.kpi.isvote.util.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

    private final SecurityService securityService;

    public PersistenceConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Bean(name = "auditorProvider")
    public AuditorAware<User> auditorProvider() {
        return new AuditorAwareImpl(securityService);
    }
}

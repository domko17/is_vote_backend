package com.tuke.fei.kpi.isvote.util;

import org.springframework.data.domain.AuditorAware;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.common.service.SecurityService;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<User> {

    private final SecurityService securityService;

    public AuditorAwareImpl(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public Optional<User> getCurrentAuditor() {
        return securityService.loggedUserReference();
    }

}

package co.edu.udea.ingweb.repairworkshop.config.security;

import org.springframework.security.core.context.SecurityContextHolder;


public final class AuditSecurityConfiguration {

    public static String getDataAuthenticatedUser(){
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }
}

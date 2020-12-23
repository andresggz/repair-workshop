package co.edu.udea.ingweb.repairworkshop.component.user.domain;

public enum Role {
    GERENTE_GENERAL, ASISTENTE_GERENCIA, GERENTE_FINANCIERO, ASISTENTE_FINANCIERO,
    ASISTENTE_RECURSOS_HUMANOS, SUPERVISOR_TALLER, TECNICO_TALLER, PROPIETARIO, AUTHENTICATED;

    public static final String PREFIX = "ROLE_";

    public static Role of(String withPrefix){
        return Role.valueOf(withPrefix.replace(Role.PREFIX, ""));
    }

    public String withPrefix(){
        return PREFIX + this.toString();
    }
}

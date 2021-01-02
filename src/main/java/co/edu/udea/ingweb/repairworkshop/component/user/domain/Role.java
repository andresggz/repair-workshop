package co.edu.udea.ingweb.repairworkshop.component.user.domain;

public enum Role {
    GG, AG, GF, AF, ARH, ST, TT, P, AUTHENTICATED;

    public static final String PREFIX = "ROLE_";

    public static Role of(String withPrefix){
        return Role.valueOf(withPrefix.replace(Role.PREFIX, ""));
    }

    public String withPrefix(){
        return PREFIX + this.toString();
    }
}

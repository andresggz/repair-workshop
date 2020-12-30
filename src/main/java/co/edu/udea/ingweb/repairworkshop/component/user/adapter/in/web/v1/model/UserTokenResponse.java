package co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserTokenResponse {

    private String token;

    public static UserTokenResponse of(String token){
        return new UserTokenResponse(token);
    }
}

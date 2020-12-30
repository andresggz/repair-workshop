package co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserQuerySearchCmd;
import lombok.*;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuerySearchRequest {

    private String names;

    private String email;

    public static UserQuerySearchCmd toModel(UserQuerySearchRequest queryCriteria){
        return UserQuerySearchCmd.builder()
                .names(queryCriteria.getNames())
                .email(queryCriteria.getEmail())
                .build();
    }
}

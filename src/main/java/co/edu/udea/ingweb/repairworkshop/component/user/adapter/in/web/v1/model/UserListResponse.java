package co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.Role;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserListResponse {

    private Long id;

    private String dni;

    private String names;

    private String lastNames;

    private String phoneNumber;

    private String email;

    private Role role;

    private LocalDateTime createdAt;

    private boolean active;

    private LocalDateTime updatedAt;

    public static UserListResponse fromModel(User user){
        return UserListResponse.builder()
                .id(user.getId())
                .names(user.getNames()).lastNames(user.getLastNames())
                .email(user.getEmail()).active(user.isActive())
                .dni(user.getDni()).phoneNumber(user.getPhoneNumber())
                .role(user.getRole()).createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt()).build();
    }
}

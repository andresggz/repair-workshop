package co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSaveRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 15)
    private String dni;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String names;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String lastNames;

    @Size(min = 6, max = 15)
    private String phoneNumber;

    private String profilePhoto;

    @Email
    @NotNull
    private String email;

    @NotNull
    @NotBlank
    private String password;

    private Role role;

    public static UserSaveCmd toModel(UserSaveRequest userToRegister){
        return UserSaveCmd.builder()
                .dni(userToRegister.getDni())
                .names(userToRegister.getNames())
                .lastNames(userToRegister.getLastNames())
                .email(userToRegister.getEmail())
                .password(userToRegister.getPassword())
                .phoneNumber(userToRegister.getPhoneNumber())
                .profilePhoto(userToRegister.getProfilePhoto())
                .role(userToRegister.getRole())
                .build();
    }
}

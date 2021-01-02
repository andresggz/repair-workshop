package co.edu.udea.ingweb.repairworkshop.component.user.application;

import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.GetUserQuery;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.UpdateUserStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.UpdateUserStatePort;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateUserStateService implements UpdateUserStateUseCase {

    private final GetUserQuery getUserQuery;

    private final UpdateUserStatePort updateUserStatePort;

    @Override
    public User update(@NotNull Long id, @NotNull UserSaveCmd userToUpdateCmd) {

        User userInDataBase = getUserQuery.findById(id);

        User userToUpdate = userInDataBase.toBuilder().dni(userToUpdateCmd.getDni())
                .names(userToUpdateCmd.getNames()).lastNames(userToUpdateCmd.getLastNames())
                .email(userToUpdateCmd.getEmail()).password(userToUpdateCmd.getPassword())
                .phoneNumber(userToUpdateCmd.getPhoneNumber()).profilePhoto(userToUpdateCmd.getProfilePhoto())
                .role(userToUpdateCmd.getRole()).build();

        User userUpdated = updateUserStatePort.update(userToUpdate);

        return userUpdated;
    }
}

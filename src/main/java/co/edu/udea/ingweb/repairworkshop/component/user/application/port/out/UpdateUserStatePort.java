package co.edu.udea.ingweb.repairworkshop.component.user.application.port.out;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;

import javax.validation.constraints.NotNull;

public interface UpdateUserStatePort {

    User update(@NotNull User userToUpdate);
}

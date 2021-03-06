package co.edu.udea.ingweb.repairworkshop.component.spare.application;

import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.RegisterSpareUseCase;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.out.RegisterSparePort;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Transactional
@Service
@RequiredArgsConstructor
class RegisterSpareService implements RegisterSpareUseCase {

    private final RegisterSparePort registerSparePort;

    @Override
    public Spare register(@NotNull SpareSaveCmd spareToRegisterCmd) {

        Spare spareToRegister = SpareSaveCmd.toModel(spareToRegisterCmd);

        Spare spareRegistered = registerSparePort.register(spareToRegister);

        return spareRegistered;
    }
}

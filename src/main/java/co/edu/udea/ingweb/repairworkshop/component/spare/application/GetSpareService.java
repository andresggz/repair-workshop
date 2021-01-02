package co.edu.udea.ingweb.repairworkshop.component.spare.application;

import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.GetSpareQuery;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.out.LoadSparePort;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetSpareService implements GetSpareQuery {

    private final LoadSparePort loadSparePort;

    @Override
    public Spare findById(@NotNull Long id) {

        Spare spareLoaded = loadSparePort.findById(id);

        return spareLoaded;
    }

    @Override
    public Page<Spare> findByParameters(@NotNull SpareQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Page<Spare> sparesLoaded = loadSparePort.findByParameters(queryCriteria, pageable);

        return sparesLoaded;
    }
}

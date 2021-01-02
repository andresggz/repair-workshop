package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.LoadRepairPort;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.RegisterRepairPort;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.UpdateRepairStatePort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
class RepairPersistenceAdapter implements RegisterRepairPort, LoadRepairPort, UpdateRepairStatePort {

    private static final String RESOURCE_NOT_FOUND = "Repair not found";

    private final SpringDataRepairRepository repairRepository;

    @Override
    public Repair register(@NotNull Repair repairToRegister) {

        final Repair repairToBeRegistered =
                repairToRegister.toBuilder().createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

        final Repair repairRegistered = repairRepository.save(repairToBeRegistered);

        return repairRegistered;
    }

    @Override
    public Repair update(@NotNull Repair repairToUpdate) {

        final Repair repairToBeUpdated =
                repairToUpdate.toBuilder().updatedAt(LocalDateTime.now()).build();

        final Repair repairUpdated = repairRepository.save(repairToBeUpdated);

        return repairUpdated;
    }

    @Override
    public Repair findById(@NotNull Long id) {

        Repair repairFound = repairRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        return repairFound;
    }

    @Override
    public Page<Repair> findByParameters(@NotNull RepairQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Specification<Repair> specification = buildCriteria(queryCriteria);

        Page<Repair> resourcesFound = repairRepository.findAll(specification, pageable);

        return resourcesFound;
    }

    private Specification<Repair> buildCriteria(@NotNull RepairQuerySearchCmd queryCriteria) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (nonNull(queryCriteria.getCreatedAfterThan())) {
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), queryCriteria.getCreatedAfterThan())));
            }

            if (nonNull(queryCriteria.getCreatedBeforeThan())) {
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), queryCriteria.getCreatedBeforeThan())));
            }



            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}

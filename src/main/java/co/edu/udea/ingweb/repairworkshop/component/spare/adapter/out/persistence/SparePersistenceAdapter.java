package co.edu.udea.ingweb.repairworkshop.component.spare.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.ResourceNotFoundException;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.out.LoadSparePort;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.out.RegisterSparePort;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.out.UpdateSpareStatePort;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
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
class SparePersistenceAdapter implements RegisterSparePort, LoadSparePort, UpdateSpareStatePort {

    private static final String RESOURCE_NOT_FOUND = "Spare not found";

    private final SpringDataSpareRepository spareRepository;

    @Override
    public Spare register(@NotNull Spare spareToRegister) {

        final Spare spareToBeRegistered =
                spareToRegister.toBuilder().createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .active(true).build();

        final Spare spareRegistered = spareRepository.save(spareToBeRegistered);

        return spareRegistered;
    }

    @Override
    public Spare update(@NotNull Spare spareToUpdate) {

        final Spare spareToBeUpdated =
                spareToUpdate.toBuilder().updatedAt(LocalDateTime.now()).build();

        final Spare spareUpdated = spareRepository.save(spareToBeUpdated);

        return spareUpdated;
    }

    @Override
    public Spare findById(@NotNull Long id) {

        Spare spareLoaded = spareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        return spareLoaded;
    }

    @Override
    public Page<Spare> findByParameters(@NotNull SpareQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Specification<Spare> specification = buildCriteria(queryCriteria);

        Page<Spare> resourcesFound = spareRepository.findAll(specification, pageable);

        return resourcesFound;
    }

    private Specification<Spare> buildCriteria(@NotNull SpareQuerySearchCmd queryCriteria) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (nonNull(queryCriteria.getName())) {
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getName() + "%")));
            }

            if (nonNull(queryCriteria.getDescription())) {
                predicates.add(criteriaBuilder
                        .and(criteriaBuilder.like(root.get("description"), "%" + queryCriteria.getDescription() + "%")));
            }

            if (nonNull(queryCriteria.getStockGreaterThan())) {
                predicates.add(criteriaBuilder
                        .and(criteriaBuilder.greaterThanOrEqualTo(root.get("stock"), queryCriteria.getStockGreaterThan())));
            }

            if (nonNull(queryCriteria.getStockLessThan())) {
                predicates.add(criteriaBuilder
                        .and(criteriaBuilder.lessThanOrEqualTo(root.get("stock"), queryCriteria.getStockLessThan())));
            }

            if (nonNull(queryCriteria.getUnitCostGreaterThan())) {
                predicates.add(criteriaBuilder
                        .and(criteriaBuilder.greaterThanOrEqualTo(root.get("unitCost"), queryCriteria.getUnitCostGreaterThan())));
            }

            if (nonNull(queryCriteria.getUnitCostLessThan())) {
                predicates.add(criteriaBuilder
                        .and(criteriaBuilder.lessThanOrEqualTo(root.get("unitCost"), queryCriteria.getUnitCostLessThan())));
            }

            if (nonNull(queryCriteria.getUnitPriceLessThan())) {
                predicates.add(criteriaBuilder
                        .and(criteriaBuilder.lessThanOrEqualTo(root.get("unitPrice"), queryCriteria.getUnitPriceLessThan())));
            }

            if (nonNull(queryCriteria.getUnitPriceGreaterThan())) {
                predicates.add(criteriaBuilder
                        .and(criteriaBuilder.greaterThanOrEqualTo(root.get("unitPrice"), queryCriteria.getUnitPriceGreaterThan())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}

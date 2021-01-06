package co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.ResourceNotFoundException;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.out.LoadVehiclePort;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.out.RegisterVehiclePort;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.out.UpdateVehicleStatePort;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
class VehiclePersistenceAdapter implements RegisterVehiclePort, LoadVehiclePort, UpdateVehicleStatePort {

    private static final String RESOURCE_NOT_FOUND = "Vehicle not found";

    private final SpringDataVehicleRepository vehicleRepository;

    @Override
    public Vehicle register(@NotNull Vehicle vehicleToRegister) {

        final Vehicle vehicleRegistered = vehicleRepository.save(vehicleToRegister);

        return vehicleRegistered;
    }

    @Override
    public Vehicle update(@NotNull Vehicle vehicleToUpdate) {

        final Vehicle vehicleUpdated = vehicleRepository.save(vehicleToUpdate);

        return vehicleUpdated;
    }

    @Override
    public Vehicle findById(@NotNull Long id) {

        Vehicle vehicleFound = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        return vehicleFound;
    }

    @Override
    public Page<Vehicle> findByParameters(@NotNull VehicleQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Specification<Vehicle> specification = buildCriteria(queryCriteria);

        Page<Vehicle> resourcesFound = vehicleRepository.findAll(specification, pageable);

        return resourcesFound;
    }

    private Specification<Vehicle> buildCriteria(@NotNull VehicleQuerySearchCmd queryCriteria) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (nonNull(queryCriteria.getMaker())) {
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("maker"), queryCriteria.getMaker())));
            }

            if (nonNull(queryCriteria.getModel())) {
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.like(root.get("model"),"%" + queryCriteria.getModel() + "%")));
            }

            if (nonNull(queryCriteria.getVehicleType())) {
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("vehicleType"), queryCriteria.getVehicleType())));
            }



            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}

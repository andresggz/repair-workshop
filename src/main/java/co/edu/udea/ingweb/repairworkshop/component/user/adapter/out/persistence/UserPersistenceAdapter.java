package co.edu.udea.ingweb.repairworkshop.component.user.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.ResourceNotFoundException;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.LoadUserPort;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.RegisterUserPort;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.UpdateUserStatePort;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
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
class UserPersistenceAdapter implements RegisterUserPort, LoadUserPort, UpdateUserStatePort {

    private static final String RESOURCE_NOT_FOUND = "User not found. userId = ";

    private final SpringDataUserRepository userRepository;

    @Override
    public User register(@NotNull User userToRegister) {

        final User userToBeRegistered =
                userToRegister.toBuilder().createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .active(true).build();

        final User userRegistered = userRepository.save(userToBeRegistered);

        return userRegistered;
    }

    @Override
    public User update(@NotNull User userToUpdate) {

        final User userToBeUpdated =
                userToUpdate.toBuilder().updatedAt(LocalDateTime.now()).build();

        final User userUpdated = userRepository.save(userToBeUpdated);

        return userUpdated;
    }

    @Override
    public User loadById(@NotNull Long id) {

        User userLoaded = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND + id));

        return userLoaded;
    }

    @Override
    public Page<User> loadByParameters(@NotNull UserQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Specification<User> specification = buildCriteria(queryCriteria);

        Page<User> resourcesFound = userRepository.findAll(specification, pageable);

        return resourcesFound;
    }

    private Specification<User> buildCriteria(@NotNull UserQuerySearchCmd queryCriteria) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (nonNull(queryCriteria.getNames())) {
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.like(root.get("names"), "%" + queryCriteria.getNames() + "%")));
            }

            if (nonNull(queryCriteria.getEmail())) {
                predicates.add(criteriaBuilder
                        .and(criteriaBuilder.equal(root.get("email"),
                                 queryCriteria.getEmail())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}

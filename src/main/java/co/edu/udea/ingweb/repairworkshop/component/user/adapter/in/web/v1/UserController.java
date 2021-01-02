package co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.shared.model.ResponsePagination;
import co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model.UserListResponse;
import co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model.UserQuerySearchRequest;
import co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model.UserSaveRequest;
import co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model.UserSaveResponse;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.GetUserQuery;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.RegisterUserUseCase;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.UpdateUserStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping(path = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    private final GetUserQuery getUserQuery;

    private final UpdateUserStateUseCase updateUserStateUseCase;

    @PreAuthorize("hasRole('GG')")
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @NotNull @Valid UserSaveRequest userToRegister){

        UserSaveCmd userToRegisterCmd = UserSaveRequest.toModel(userToRegister);

        User userRegistered =
                registerUserUseCase.register(userToRegisterCmd);

        URI location = fromUriString("/api/v1/users").path("/{id}")
                .buildAndExpand(userRegistered.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserSaveResponse> findById(@Valid @PathVariable("id") Long id){

        User userFound = getUserQuery.findById(id);

        return ResponseEntity.ok(UserSaveResponse.fromModel(userFound));
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping
    public ResponsePagination<UserListResponse> findByParameters(@Valid @NotNull UserQuerySearchRequest queryCriteria,
                                                                 @PageableDefault(page = 0, size = 12,
                                                                 direction = Sort.Direction.DESC, sort = "id")
                                                                 Pageable pageable){
        UserQuerySearchCmd queryCriteriaCmd = UserQuerySearchRequest.toModel(queryCriteria);

        Page<User> usersFound = getUserQuery.findByParameters(queryCriteriaCmd, pageable);
        List<UserListResponse> usersFoundList = usersFound.stream().map(UserListResponse::fromModel)
                .collect(Collectors.toList());

        return ResponsePagination.fromObject(usersFoundList, usersFound.getTotalElements(), usersFound.getNumber(),
                usersFoundList.size());
    }

    @PreAuthorize("hasRole('GG')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<UserSaveResponse> update(@RequestBody @NotNull @Valid UserSaveRequest userToUpdate,
                                                     @Valid @PathVariable("id") @NotNull Long id) {

        UserSaveCmd userToUpdateCmd = UserSaveRequest.toModel(userToUpdate);

        User userUpdated = updateUserStateUseCase.update(id, userToUpdateCmd);

        return ResponseEntity.ok(UserSaveResponse.fromModel(userUpdated));
    }
}

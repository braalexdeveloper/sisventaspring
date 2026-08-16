package sisventaGroup.sisventaArtifact.modules.roles;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import sisventaGroup.sisventaArtifact.modules.roles.dtos.RoleRequestDto;
import sisventaGroup.sisventaArtifact.modules.roles.dtos.RoleResponseDto;
import sisventaGroup.sisventaArtifact.shared.ResponseBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getRoles() {

        List<RoleResponseDto> roles = roleService.getRoles();

        return ResponseEntity.ok(
                new ResponseBuilder()
                        .msg("Roles obtenidos con éxito")
                        .add("roles", roles)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createRole(
            @Valid @RequestBody RoleRequestDto request) {

        RoleResponseDto createdRole =
                roleService.createRole(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ResponseBuilder()
                                .msg("Rol creado correctamente")
                                .add("role", createdRole)
                                .build()
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequestDto request) {

        RoleResponseDto updatedRole =
                roleService.updateRole(request, id);

        return ResponseEntity.ok(
                new ResponseBuilder()
                        .msg("Rol actualizado correctamente")
                        .add("role", updatedRole)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteRole(
            @PathVariable Long id) {

        String message = roleService.deleteRole(id);

        return ResponseEntity.ok(
                new ResponseBuilder()
                        .msg(message)
                        .build()
        );
    }
}

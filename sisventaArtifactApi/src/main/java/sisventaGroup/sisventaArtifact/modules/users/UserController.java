package sisventaGroup.sisventaArtifact.modules.users;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisventaGroup.sisventaArtifact.modules.users.dtos.UserRequestDto;
import sisventaGroup.sisventaArtifact.modules.users.dtos.UserResponseDto;
import sisventaGroup.sisventaArtifact.shared.ResponseBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public  UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getUsers(){
        List<UserResponseDto> users=userService.getUsers();
        return ResponseEntity.ok(new ResponseBuilder().msg("Usuarios obtenidos correctamente").add("users",users).build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(
           @Valid @RequestBody UserRequestDto request
    ) {

        UserResponseDto user = userService.createUser(request);

        return ResponseEntity.ok(
                new ResponseBuilder()
                        .msg("Usuario creado correctamente")
                        .add("user", user)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto request
    ) {

        UserResponseDto user = userService.updateUser(id, request);

        return ResponseEntity.ok(
                new ResponseBuilder()
                        .msg("Usuario actualizado correctamente")
                        .add("user", user)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @PathVariable Long id
    ) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                new ResponseBuilder()
                        .msg("Usuario eliminado correctamente")
                        .build()
        );
    }

}

package sisventaGroup.sisventaArtifact.modules.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisventaGroup.sisventaArtifact.modules.auth.dtos.LoginRequest;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request
    ) {

        String token=authService.login(request);

        return ResponseEntity.ok(token);
    }
}

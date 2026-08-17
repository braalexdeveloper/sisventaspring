package sisventaGroup.sisventaArtifact.modules.auth;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisventaGroup.sisventaArtifact.modules.auth.dtos.LoginRequest;
import sisventaGroup.sisventaArtifact.modules.auth.dtos.LoginResponse;
import sisventaGroup.sisventaArtifact.modules.auth.dtos.RegisterRequest;
import sisventaGroup.sisventaArtifact.modules.auth.dtos.RegisterResponse;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {

        LoginResponse response=authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest user){
        RegisterResponse response=authService.register(user);
        return ResponseEntity.ok(response);
    }
}

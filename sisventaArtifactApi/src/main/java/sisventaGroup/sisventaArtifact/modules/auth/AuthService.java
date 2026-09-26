package sisventaGroup.sisventaArtifact.modules.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sisventaGroup.sisventaArtifact.Errors.ResourceNotFoundException;
import sisventaGroup.sisventaArtifact.modules.auth.dtos.LoginRequest;
import sisventaGroup.sisventaArtifact.modules.auth.dtos.LoginResponse;
import sisventaGroup.sisventaArtifact.modules.auth.dtos.RegisterRequest;
import sisventaGroup.sisventaArtifact.modules.auth.dtos.RegisterResponse;
import sisventaGroup.sisventaArtifact.modules.auth.security.JwtService;
import sisventaGroup.sisventaArtifact.modules.roles.Role;
import sisventaGroup.sisventaArtifact.modules.roles.RoleRepository;
import sisventaGroup.sisventaArtifact.modules.users.User;
import sisventaGroup.sisventaArtifact.modules.users.UserRepository;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.roleRepository=roleRepository;
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        /*UserDetails userDetails=(UserDetails) authentication.getPrincipal();*/
        User user=(User) authentication.getPrincipal();
        String token=jwtService.generateToken(user);
        return new LoginResponse(
                token,
                user.getEmail(),
                user.getRole().getName()
        );
    }


    public RegisterResponse register(RegisterRequest request){
        Role role=roleRepository.findByName("User").orElseThrow(()->new ResourceNotFoundException("Rol no encontrado"));
        User user=new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        User userSaved=userRepository.save(user);
        String token=jwtService.generateToken(userSaved);

        return new RegisterResponse(userSaved.getEmail(),userSaved.getRole().getName(),token);
    }

    public RegisterResponse register(RegisterRequest request){
        Role role=roleRepository.findByName("User").orElseThrow(()->new ResourceNotFoundException("Rol no encontrado"));
        User user=new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        User userSaved=userRepository.save(user);
        String token=jwtService.generateToken(userSaved);

        return new RegisterResponse(userSaved.getEmail(),userSaved.getRole().getName(),token);
    }
}

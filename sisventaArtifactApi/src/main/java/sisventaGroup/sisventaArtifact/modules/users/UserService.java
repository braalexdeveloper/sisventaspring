package sisventaGroup.sisventaArtifact.modules.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sisventaGroup.sisventaArtifact.Errors.ResourceNotFoundException;
import sisventaGroup.sisventaArtifact.modules.roles.Role;
import sisventaGroup.sisventaArtifact.modules.roles.RoleRepository;
import sisventaGroup.sisventaArtifact.modules.users.dtos.UserRequestDto;
import sisventaGroup.sisventaArtifact.modules.users.dtos.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public List<UserResponseDto> getUsers(){
     return userRepository.findAll().stream().map(this::convertToUserResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto requestUser){
        Role roleFound=roleRepository.findById(requestUser.getRoleId()).orElseThrow(()->new ResourceNotFoundException("Rol no encontrado"));

        User userCreated=userRepository.save(convertTouser(new User(),requestUser,roleFound));
        return convertToUserResponseDto(userCreated);
    }

    @Transactional
    public UserResponseDto updateUser(Long id,UserRequestDto request){
        User userFound=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado"));

        Role roleFound=roleRepository.findById(request.getRoleId()).orElseThrow(()->new ResourceNotFoundException("Rol no encontrado"));

        User userUpdated=userRepository.save(convertTouser(userFound,request,roleFound));
        return convertToUserResponseDto(userUpdated);
    }

    public String deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("Usuario no encontrado!");
        }
        userRepository.deleteById(id);

        return "Usuario eliminado";

    }

    private User convertTouser(User user,UserRequestDto requestUser,Role role){

        user.setEmail(requestUser.getEmail());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        return user;
    }
    private UserResponseDto convertToUserResponseDto(User user){
        UserResponseDto response=new UserResponseDto();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRoleName(user.getRole().getName());
        return response;
    }
}

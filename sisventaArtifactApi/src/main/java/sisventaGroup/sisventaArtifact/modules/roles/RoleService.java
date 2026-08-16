package sisventaGroup.sisventaArtifact.modules.roles;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sisventaGroup.sisventaArtifact.Errors.ResourceNotFoundException;
import sisventaGroup.sisventaArtifact.modules.roles.dtos.RoleRequestDto;
import sisventaGroup.sisventaArtifact.modules.roles.dtos.RoleResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleResponseDto> getRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::convertToResponseRoleDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RoleResponseDto createRole(RoleRequestDto request) {

        Role role = new Role();
        role.setName(request.getName());

        Role roleSaved = roleRepository.save(role);

        return convertToResponseRoleDto(roleSaved);
    }

    @Transactional
    public RoleResponseDto updateRole(RoleRequestDto request, Long id) {

        Role roleFound = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Rol no encontrado!"
                        ));

        roleFound.setName(request.getName());

        Role roleUpdated = roleRepository.save(roleFound);

        return convertToResponseRoleDto(roleUpdated);
    }

    @Transactional
    public String deleteRole(Long id) {

        Role roleFound = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Rol no encontrado!"
                        ));

        roleRepository.delete(roleFound);

        return "Rol eliminado correctamente";
    }

    private RoleResponseDto convertToResponseRoleDto(Role role) {

        RoleResponseDto response = new RoleResponseDto();

        response.setId(role.getId());
        response.setName(role.getName());

        return response;
    }
}
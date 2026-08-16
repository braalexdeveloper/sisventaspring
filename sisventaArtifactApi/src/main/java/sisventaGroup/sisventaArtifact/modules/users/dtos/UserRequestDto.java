package sisventaGroup.sisventaArtifact.modules.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequestDto {

    @NotBlank(message = "El campo email es obligatorio")
    @Email(message = "El campo email debe tener un formato válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatorio")
    private String password;

    @NotNull(message = "El id del rol es obligatorio")
    private Long roleId;

    public UserRequestDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}

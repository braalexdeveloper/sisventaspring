package sisventaGroup.sisventaArtifact.modules.auth.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotBlank(message = "Email es requerido")
    private String email;

    @NotBlank(message = "Password requerido")
    private String password;

    public RegisterRequest() {
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
}

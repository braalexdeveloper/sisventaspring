package sisventaGroup.sisventaArtifact.modules.auth.dtos;

public record LoginResponse(
        String token,
        String email,
        String role
) {
}

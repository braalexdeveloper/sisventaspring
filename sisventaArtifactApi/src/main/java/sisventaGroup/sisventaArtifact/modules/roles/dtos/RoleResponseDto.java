package sisventaGroup.sisventaArtifact.modules.roles.dtos;

public class RoleResponseDto {

    private Long id;
    private String name;

    public RoleResponseDto() {
    }

    public RoleResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

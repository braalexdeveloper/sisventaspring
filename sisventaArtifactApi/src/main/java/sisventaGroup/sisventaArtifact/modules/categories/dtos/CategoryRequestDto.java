package sisventaGroup.sisventaArtifact.modules.categories.dtos;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

public class CategoryRequestDto {
    @NotBlank(message = "El nombre es requerido")
    @Size(min=2,max=50)
    private String name;

    @Size(min=2,max=50)
    private String description;

    public CategoryRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

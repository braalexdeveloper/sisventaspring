package sisventaGroup.sisventaArtifact.modules.products.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class RequestProductDto {
    @NotBlank(message = "El nombre es requerido!")
    private String name;

    private String description;

    @NotNull(message = "El precio es obligatorio!")
    @DecimalMin(value = "0.0",inclusive = false,message = "El precio debe ser mayor a 0")
    private BigDecimal price;

    @NotNull(message = "Stock es obligatorio!")
    @Min(value=0,message = "El stock debe ser un número mayor o igual a 0")
    private Integer stock;

    private MultipartFile imageFile;

    @NotNull(message = "El id de categoria es obligatirio!")
    private Long category_id;

    public RequestProductDto() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
}

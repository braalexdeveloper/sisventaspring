package sisventaGroup.sisventaArtifact.modules.categories;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import sisventaGroup.sisventaArtifact.modules.products.Product;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio!")
    private String name;

    private String description;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Product> products=new HashSet<>();

    public  CategoryEntity(){

    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getName(){
        return name;
    }

    public void  setName(String name){
        this.name=name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Product> getProducts(){
        return products;
    }

    public void setProducts(Set<Product> products){
        this.products=products;
    }



}

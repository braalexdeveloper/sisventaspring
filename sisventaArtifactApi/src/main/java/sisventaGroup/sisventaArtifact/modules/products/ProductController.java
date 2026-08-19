package sisventaGroup.sisventaArtifact.modules.products;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisventaGroup.sisventaArtifact.modules.products.dtos.ProductFilterRequest;
import sisventaGroup.sisventaArtifact.modules.products.dtos.RequestProductDto;
import sisventaGroup.sisventaArtifact.modules.products.dtos.ResponseProductDto;
import sisventaGroup.sisventaArtifact.shared.ResponseBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy, ProductFilterRequest filter){

     Page<ResponseProductDto> products=productService.getProducts(page,size,sortBy,filter);

     return ResponseEntity.ok(new ResponseBuilder().msg("Productos obtenidos con éxito").add("products",products.getContent()).add("page",products.getNumber()).add("size",products.getSize()).add("totalElements", products.getTotalElements())
             .add("totalPages", products.getTotalPages()).build());
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createProduct(@Valid @ModelAttribute RequestProductDto product){
        ResponseProductDto createdProduct=productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseBuilder().msg("Producto creado correctamente!").add("product",createdProduct).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable("id") Long id,@Valid @ModelAttribute RequestProductDto
            productRequest){

        ResponseProductDto updatedProduct = productService.updateProduct(productRequest,id);
        return ResponseEntity.ok(new ResponseBuilder().msg("Producto actualizado correctamente!").add("product",updatedProduct).build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable("id") Long id){
        String message=productService.deleteProduct(id);
        return ResponseEntity.ok(new ResponseBuilder().msg(message).build());
    }


}

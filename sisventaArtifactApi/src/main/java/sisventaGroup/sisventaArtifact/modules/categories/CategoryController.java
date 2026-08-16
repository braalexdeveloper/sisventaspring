package sisventaGroup.sisventaArtifact.modules.categories;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import sisventaGroup.sisventaArtifact.modules.categories.dtos.CategoryRequestDto;
import sisventaGroup.sisventaArtifact.modules.categories.dtos.ResponseCategoryDto;
import sisventaGroup.sisventaArtifact.shared.ResponseBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDto>> getCategories(){

       return ResponseEntity.ok(categoryService.getCategories());
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> create(@Valid @RequestBody CategoryRequestDto category){

        ResponseCategoryDto newCategory=categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseBuilder().msg("Categoria creado correctamente!").add("category",newCategory).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable("id") Long id,@Valid @RequestBody CategoryRequestDto category){

        ResponseCategoryDto categoryUpdate=categoryService.updateCategory(category,id);
      return ResponseEntity.ok(new ResponseBuilder().msg("Categoria actualizada correctamente").add("category",categoryUpdate).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable("id") Long id){
       String message=categoryService.deleteCategory(id);
       return ResponseEntity.ok(new ResponseBuilder().msg(message).build());
    }


}

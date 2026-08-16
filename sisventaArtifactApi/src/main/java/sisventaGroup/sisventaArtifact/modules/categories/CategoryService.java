package sisventaGroup.sisventaArtifact.modules.categories;

import org.springframework.stereotype.Service;
import sisventaGroup.sisventaArtifact.Errors.ResourceNotFoundException;
import sisventaGroup.sisventaArtifact.modules.categories.dtos.CategoryRequestDto;
import sisventaGroup.sisventaArtifact.modules.categories.dtos.ResponseCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    public List<ResponseCategoryDto> getCategories(){

        return this.categoryRepository.findAll().stream()
                .map(this::convertToCategoryResponse)
                .collect(Collectors.toList());

    }

    public ResponseCategoryDto createCategory(CategoryRequestDto category) {

        CategoryEntity categoryEntity =
                convertToCategoryEntity(new CategoryEntity(), category);

        CategoryEntity savedCategory =
                categoryRepository.save(categoryEntity);

        return convertToCategoryResponse(savedCategory);
    }

    public ResponseCategoryDto updateCategory(CategoryRequestDto category,Long id){
        CategoryEntity categoryUpdated=categoryRepository.findById(id).map(categoryFound->{

            return categoryRepository.save(convertToCategoryEntity(categoryFound,category));

        }).orElseThrow(()->new ResourceNotFoundException("Categoria no encontrado!"));

        return convertToCategoryResponse(categoryUpdated);
    }


    public String deleteCategory(Long id){
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Categoria no encontrada!");
        }
        categoryRepository.deleteById(id);
        return "Categoria eliminada!";
    }

    private CategoryEntity convertToCategoryEntity(CategoryEntity category,CategoryRequestDto request){
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return  category;
    }

    private ResponseCategoryDto convertToCategoryResponse(CategoryEntity categoryEntity){
        ResponseCategoryDto categoryResponse=new ResponseCategoryDto();
        categoryResponse.setId(categoryEntity.getId());
        categoryResponse.setName(categoryEntity.getName());
        categoryResponse.setDescription(categoryEntity.getDescription());
        return categoryResponse;
    }
}

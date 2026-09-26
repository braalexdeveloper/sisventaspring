package sisventaGroup.sisventaArtifact.modules.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import sisventaGroup.sisventaArtifact.Errors.ResourceNotFoundException;
import sisventaGroup.sisventaArtifact.modules.categories.CategoryEntity;
import sisventaGroup.sisventaArtifact.modules.categories.CategoryRepository;
import sisventaGroup.sisventaArtifact.modules.products.dtos.ProductFilterRequest;
import sisventaGroup.sisventaArtifact.modules.products.dtos.RequestProductDto;
import sisventaGroup.sisventaArtifact.modules.products.dtos.ResponseProductDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private static final String UPLOAD_DIR = "uploads/products/";

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ResponseProductDto> getProducts(int page, int size, String sortBy, ProductFilterRequest filter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Product> productsPage;

        String name = filter.getName();
        String categoryName = filter.getCategoryName();

        if (name != null && !name.isBlank() && categoryName != null && !categoryName.isBlank()) {
            productsPage = productRepository.findByNameContainingIgnoreCaseAndCategory_NameContainingIgnoreCase(name,
                    categoryName, pageable);
        } else if (name != null && !name.isBlank()) {
            productsPage = productRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (categoryName != null && !categoryName.isBlank()) {

            productsPage = productRepository.findByCategory_NameContainingIgnoreCase(
                    categoryName,
                    pageable);

        } else {

            productsPage = productRepository.findAll(pageable);
        }

        return productsPage.map(this::convertToResponseProductDto);
    }

    public ResponseProductDto createProduct(RequestProductDto requestProduct) {
        CategoryEntity categoryFound = categoryRepository.findById(requestProduct.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada!"));

        Product newProduct = convertToProduct(new Product(), requestProduct, categoryFound);

        Product productSave = productRepository.save(newProduct);
        return convertToResponseProductDto(productSave);
    }

    public ResponseProductDto updateProduct(RequestProductDto requestProduct, Long id) {
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado!"));

        CategoryEntity categoryFound = categoryRepository.findById(requestProduct.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada!"));

        Product productUpdated = productRepository.save(convertToProduct(productFound, requestProduct, categoryFound));
        return convertToResponseProductDto(productUpdated);
    }

    public String deleteProduct(Long id) {
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado!"));
        if (productFound.getImage() != null) {
            deleteImage(productFound.getImage());
        }
        productRepository.deleteById(id);
        return "Producto eliminado correctamente";
    }

    private Product convertToProduct(Product product, RequestProductDto request, CategoryEntity categoryFound) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {

            if (product.getImage() != null) {
                deleteImage(product.getImage());
            }

            product.setImage(saveImage(request.getImageFile()));
        }

        product.setCategory(categoryFound);

        return product;
    }

    private ResponseProductDto convertToResponseProductDto(Product product) {
        ResponseProductDto response = new ResponseProductDto();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setImage(product.getImage());
        response.setCategoryName(product.getCategory().getName());
        return response;
    }

    private String saveImage(MultipartFile imageFile) {
        try {
            String imageName = System.currentTimeMillis()
                    + "_"
                    + imageFile.getOriginalFilename();

            Path uploadPath = Paths.get(UPLOAD_DIR);

            Files.createDirectories(uploadPath);

            Path imagePath = uploadPath.resolve(imageName);

            Files.write(imagePath, imageFile.getBytes());

            return imageName;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    private void deleteImage(String imagePath) {
        try {
            Path path = Paths.get(UPLOAD_DIR + imagePath);

            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Imagen eliminada: " + path.toString());
            } else {
                System.out.println("La imagen no existe : " + path.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar imagen", e);
        }
    }
}

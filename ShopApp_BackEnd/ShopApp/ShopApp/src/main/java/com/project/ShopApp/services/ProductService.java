package com.project.ShopApp.services;
import com.project.ShopApp.Response.ProductResponse;
import com.project.ShopApp.dtos.ProductDTO;
import com.project.ShopApp.dtos.ProductImageDTO;
import com.project.ShopApp.models.Category;
import com.project.ShopApp.models.Product;
import com.project.ShopApp.models.ProductImage;
import com.project.ShopApp.repositories.CategoryRepository;
import com.project.ShopApp.repositories.ProductImageRepository;
import com.project.ShopApp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found with id: " + productDTO.getCategoryId()));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .category(category)
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) {
        Product existingProduct = getProductById(productId);
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .image_url(productImageDTO.getImage_url())
                .build();
        //KIểm tra và không cho thêm ảnh nếu sản phẩm có hơn 5 ảnh
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= 5){
            throw new RuntimeException("Product's image size exceeds 5!");
        }

        return productImageRepository.save(newProductImage);
    }


    @Override
    public Product getProductById(long id) {
//        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(product ->{
            ProductResponse productResponse = ProductResponse.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .thumbnail(product.getThumbnail())
                    .categoryId(product.getCategory().getId())
                    .build();
            productResponse.setCreatedAt(product.getCreatedAt());
            productResponse.setUpdatedAt(product.getCreatedAt());
            return productResponse;
        });


    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO){
        Product existingProduct = getProductById(id);
        if(existingProduct != null){

            Category newCategory = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found with id: " + productDTO.getCategoryId()));

            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            existingProduct.setCategory(newCategory);

            return productRepository.save(existingProduct);
        }else{
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @Override
    public void deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }


}

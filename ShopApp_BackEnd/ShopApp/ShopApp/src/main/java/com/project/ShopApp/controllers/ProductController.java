package com.project.ShopApp.controllers;

import com.github.javafaker.Faker;
import com.project.ShopApp.response.ProductListResponse;
import com.project.ShopApp.response.ProductResponse;
import com.project.ShopApp.dtos.ProductDTO;
import com.project.ShopApp.dtos.ProductImageDTO;
import com.project.ShopApp.models.Product;
import com.project.ShopApp.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;



    @PostMapping(value = "")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result
    ){
        try{
            //Get all error messages and return break
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDTO);
            //Return response
            return ResponseEntity.ok(newProduct);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping(value = "uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ){
        try{
//Get productExisting
            //Check if file is null, to prevent exception

            if(files.size() > 5){
                return ResponseEntity.badRequest().body("You just can upload less than or equal 5 images!");
            }
            if(files == null){
                files = new ArrayList<MultipartFile>();
            }
            List<ProductImageDTO> productImages = new ArrayList<ProductImageDTO>();
            for (MultipartFile file : files) {
                //Check image file if null
                if(file == null){
                    return ResponseEntity.badRequest().body("File is null!");
                }

                //Check image if image size = 0
                if(file.getSize() == 0){
                    return ResponseEntity.badRequest().body("What was what!??");
                }

                //Check image if too large
                if(file.getSize() > 10 * 1024 * 1024){
                    return ResponseEntity.badRequest().body("File is too large!");
                }

                //Check if wrong extension
                if (!isImageFile(file)) {
                    return ResponseEntity.badRequest().body("File must be an image!");
                }
                //Execute store the file
                String fileName = this.storeFile(file);

                //Save images of product to DB
                ProductImageDTO productImageDTO = ProductImageDTO.builder()
                        .image_url(fileName)
                        .build();
                productService.createProductImage(productId, productImageDTO);
                productImages.add(productImageDTO);
            }
            return ResponseEntity.ok(productImages);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private boolean isImageFile(MultipartFile file) {
        // Check content type (more reliable)
        if (file.getContentType() != null && file.getContentType().startsWith("image/")) {
            return true;
        }

        // Check file extension (less reliable)
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.asList("jpg", "jpeg", "png", "gif").contains(extension);
    }
    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //Add UUID before fileName to make this file unique, not duplicate with another file
        String uniqueFileName = UUID.randomUUID() + "_" + filename;
        // The dir path to save file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        //Check, create the folder "uploads" if it doesn't exist
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //Get the file path in the dir
        java.nio.file.Path destinatioin = Paths.get(uploadDir.toString(), uniqueFileName);
        //Copy the file to the folder uploads
        Files.copy(file.getInputStream(), destinatioin, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }


    //GET Method to get - ALL Products
    @GetMapping("") //http://localhost:8088/api/v1/products?page=1&limit=10
    public ResponseEntity<?> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("createdAt").descending());

        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        int totalPages = productPage.getTotalPages();


        List<ProductResponse> products = productPage.getContent();
        int total_products = products.size();

        return ResponseEntity.ok(ProductListResponse.builder()
                        .products(products)
                        .total_pages(totalPages)
                        .total_products(total_products)
                .build());
    }

    //GET METHOD - To get a product with id
    @GetMapping("/{id}") //http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<?> getProductById(@PathVariable Long id)
    {
        try {
            ProductResponse existingProduct = ProductResponse.fromProduct(productService.getProductById(id));
            return ResponseEntity.ok(existingProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Update a product
    @PutMapping("/{id}")
    public  ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO
    ){
        try {
            Product updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Delete a product
    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("This is Deleted Product: id = " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/generateFakeProducts")
    public ResponseEntity<?> generateFakeProducts(){
        Faker faker = new Faker();
        for(int i = 0; i < 100000; i++){

            String productName = faker.commerce().productName();



            //Check data
            if(productService.existsByName(productName)){
                continue;
            }


            //Create
            ProductDTO productDTO  = ProductDTO.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(10,10000000))
                    .thumbnail("")
                    .description(faker.lorem().sentence())
                    .categoryId((long)faker.number().numberBetween(2,3))
                    .build();

            //Save
            try{
                productService.createProduct(productDTO);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }

        return ResponseEntity.ok("Fake Product Generator");
    }


}

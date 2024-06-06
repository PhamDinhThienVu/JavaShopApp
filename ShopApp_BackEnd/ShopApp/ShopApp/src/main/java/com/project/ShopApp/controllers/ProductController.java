package com.project.ShopApp.controllers;

import ch.qos.logback.core.util.StringUtil;
import com.project.ShopApp.dtos.CategoryDTO;
import com.project.ShopApp.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    //GET Method to get - ALL Products
    @GetMapping("") //http://localhost:8088/api/v1/products?page=1&limit=10
    public ResponseEntity<String> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        return ResponseEntity.ok("Get products here!");
    }

    //GET METHOD - To get a product with id
    @GetMapping("/{id}") //http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<String> getProductById(@PathVariable Long id)
    {
        return ResponseEntity.ok("Get products " + id);
    }





    @PostMapping(value = "",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @Valid
            @ModelAttribute
            ProductDTO productDTO,
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

            //Get files from objects
            List<MultipartFile> files = productDTO.getFiles();
            //Check if file is null, to prevent exception
            if(files == null){
                files = new ArrayList<MultipartFile>();
            }

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
            }

            //Save object to DB

            //Return response
            return ResponseEntity.ok().body("Create a new product successfully!");

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
    public String storeFile(MultipartFile file) throws IOException {
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






    //Update a product
    @PutMapping("/{id}")
    public  ResponseEntity<String> updateProduct(@PathVariable Long id){
        return ResponseEntity.ok("This is Update Product: id = " + id);
    }

    //Delete a product
    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok("This is delete product: id = " + id);
    }
}

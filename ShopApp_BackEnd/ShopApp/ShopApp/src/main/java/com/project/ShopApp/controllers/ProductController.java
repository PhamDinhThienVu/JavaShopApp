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
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    //Get all product
    @GetMapping("") //http://localhost:8088/api/v1/products?page=1&limit=10
    public ResponseEntity<String> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        return ResponseEntity.ok("Get products here!");
    }

    //Get a product
    @GetMapping("/{id}") //http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<String> getProductById(@PathVariable Long id)
    {
        return ResponseEntity.ok("Get products " + id);
    }



    //Insert a product
//    @PostMapping("")
//    public  ResponseEntity<?> insertProduct(
//            @Valid
//            @RequestBody
//            ProductDTO productDTO,
//            BindingResult result){
//        try{
//            if(result.hasErrors()){
//                List<String> errorMessages = result.getFieldErrors()
//                        .stream()
//                        .map(FieldError::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.badRequest().body(errorMessages);
//            }
//            return ResponseEntity.ok("This is insertCategory" + productDTO.toString());
//        }catch(Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }



    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<?> insertProduct(
            @Valid @RequestBody
            ProductDTO productDTO,
            //@RequestPart("file") MultipartFile file,
            BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            MultipartFile file = productDTO.getFile();
            if(file != null){
                //Kiểm tra kích thước và định dạng
                if(file.getSize() > 10 * 1024 * 1024){
//            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File is too large");
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large");
                }
                String contentType = file.getContentType();
                //Kiểm tra định dạng file
//                if(contentType == null || !contentType.startsWith("image/")){
//
//                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE
//                    ).body("File must be an image!");
//                }
                String  filename = this.storeFile(file);

            }
            return ResponseEntity.ok("This is insertProduct" + productDTO.toString());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    //Néu tham số truyền vào là một đối tượng => Dât Tràner Object = Request Object
    private String storeFile(MultipartFile file) throws IOException{
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;

        java.nio.file.Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }

        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
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

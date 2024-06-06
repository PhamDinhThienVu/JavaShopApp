package com.project.ShopApp.controllers;

import com.project.ShopApp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
//@Validated
public class CategoryController {
    //Get all
    @GetMapping("") //http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        return ResponseEntity.ok("Chao ban haha, page = " + page + " limit = " + limit);
    }

    //Néu tham số truyền vào là một đối tượng => Dât Tràner Object = Request Object
    @PostMapping("")
    public  ResponseEntity<?> insertCategory(
            @Valid
            @RequestBody
            CategoryDTO categoryDTO,
            BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("This is insertCategory" + categoryDTO.toString());
    }

    @PutMapping("/{id}")
    public  ResponseEntity<String> updateCategory(@PathVariable Long id){
        return ResponseEntity.ok("This is insertCategory: id = " + id);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok("This is delete category: id = " + id);
    }
}
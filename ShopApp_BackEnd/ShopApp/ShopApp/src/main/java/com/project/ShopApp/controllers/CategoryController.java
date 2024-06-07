package com.project.ShopApp.controllers;

import com.project.ShopApp.dtos.CategoryDTO;
import com.project.ShopApp.models.Category;
import com.project.ShopApp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated
@RequiredArgsConstructor
public class CategoryController {

    private  final CategoryService categoryService;

    //Néu tham số truyền vào là một đối tượng => Dât Tràner Object = Request Object
    @PostMapping("")
    public  ResponseEntity<?> createCategory(
            @Valid
            @RequestBody
            CategoryDTO categoryDTO,
            BindingResult result){

        //Get all error messages and return break
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        //Creaet product to db
        categoryService.createCategory(categoryDTO);

        return ResponseEntity.ok("Insert category successfully! Info:" + categoryDTO.toString());
    }




    //Get all
    @GetMapping("") //http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }



    @PutMapping("/{id}")
    public  ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO
    ){
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("UpdateCategory successful" + "_" + id + "_" + categoryDTO.toString());
    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete category successfully!: id = " + id);
    }
}

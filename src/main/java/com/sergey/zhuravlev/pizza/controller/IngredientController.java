package com.sergey.zhuravlev.pizza.controller;

import com.sergey.zhuravlev.pizza.dto.IngredientRequestDto;
import com.sergey.zhuravlev.pizza.dto.pageable.PageDto;
import com.sergey.zhuravlev.pizza.entity.Ingredient;
import com.sergey.zhuravlev.pizza.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping(path = "/ingredients")
    public PageDto<Ingredient> list(@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return new PageDto<>(ingredientService.list(PageRequest.of(page, size)));
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long id) {
        return ResponseEntity.ok().body(ingredientService.getIngredient(id));
    }

    @PostMapping(value = "/ingredients")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody @Valid IngredientRequestDto ingredientRequestDto) throws URISyntaxException {
        Ingredient ingredient = ingredientService.createIngredient(
                ingredientRequestDto.getTitle(),
                ingredientRequestDto.getShelfLife());
        return ResponseEntity.created(new URI("/api/ingredients/" + ingredient.getId())).body(ingredient);
    }

    @PutMapping(value = "/ingredients/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id,
                                                       @RequestBody @Valid IngredientRequestDto ingredientRequestDto) {
        Ingredient ingredient = ingredientService.updateIngredient(
                id,
                ingredientRequestDto.getTitle(),
                ingredientRequestDto.getShelfLife());

        return ResponseEntity.ok().body(ingredient);
    }

    @DeleteMapping(value = "/ingredients/{id}")
    public ResponseEntity deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }

}

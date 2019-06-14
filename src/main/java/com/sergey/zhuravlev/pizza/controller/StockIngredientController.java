package com.sergey.zhuravlev.pizza.controller;

import com.sergey.zhuravlev.pizza.dto.StockIngredientRequestDto;
import com.sergey.zhuravlev.pizza.dto.pageable.PageDto;
import com.sergey.zhuravlev.pizza.entity.StockIngredient;
import com.sergey.zhuravlev.pizza.service.StockIngredientService;
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
public class StockIngredientController {

    private final StockIngredientService stockIngredientService;

    @GetMapping(path = "/stock")
    public PageDto<StockIngredient> list(@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return new PageDto<>(stockIngredientService.list(PageRequest.of(page, size)));
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<StockIngredient> getStockIngredient(@PathVariable Long id) {
        return ResponseEntity.ok().body(stockIngredientService.getStockIngredient(id));
    }

    @PostMapping(value = "/stock")
    public ResponseEntity<StockIngredient> createStockIngredient(@RequestBody @Valid StockIngredientRequestDto stockIngredientRequestDto)
            throws URISyntaxException {
        StockIngredient stockIngredient = stockIngredientService.createStockIngredient(
                stockIngredientRequestDto.getIngredientId(),
                stockIngredientRequestDto.getDeliveryDate(),
                stockIngredientRequestDto.getStockType(),
                stockIngredientRequestDto.getCount(),
                stockIngredientRequestDto.getWeight());
        return ResponseEntity.created(new URI("/api/stock/" + stockIngredient.getId())).body(stockIngredient);
    }

    @PutMapping(value = "/stock/{id}")
    public ResponseEntity<StockIngredient> updateStockIngredient(@PathVariable Long id,
                                                            @RequestBody @Valid StockIngredientRequestDto stockIngredientRequestDto) {
        StockIngredient stockIngredient = stockIngredientService.updateStockIngredient(
                id,
                stockIngredientRequestDto.getIngredientId(),
                stockIngredientRequestDto.getDeliveryDate(),
                stockIngredientRequestDto.getStockType(),
                stockIngredientRequestDto.getCount(),
                stockIngredientRequestDto.getWeight());
        return ResponseEntity.ok().body(stockIngredient);
    }

    @DeleteMapping(value = "/stock/{id}")
    public ResponseEntity deleteStockIngredient(@PathVariable Long id) {
        stockIngredientService.deleteStockIngredient(id);
        return ResponseEntity.noContent().build();
    }

}

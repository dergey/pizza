package com.sergey.zhuravlev.pizza.controller;

import com.sergey.zhuravlev.pizza.dto.supplier.SupplierCreateRequestDto;
import com.sergey.zhuravlev.pizza.dto.supplier.SupplierUpdateRequestDto;
import com.sergey.zhuravlev.pizza.dto.pageable.PageDto;
import com.sergey.zhuravlev.pizza.entity.Supplier;
import com.sergey.zhuravlev.pizza.entity.Supply;
import com.sergey.zhuravlev.pizza.service.SupplierService;
import com.sergey.zhuravlev.pizza.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final SupplyService supplyService;

    @GetMapping(path = "/suppliers")
    public PageDto<Supplier> list(@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return new PageDto<>(supplierService.list(PageRequest.of(page, size)));
    }

    @GetMapping("/suppliers/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable Long id) {
        return ResponseEntity.ok().body(supplierService.getSupplier(id));
    }

    @PostMapping(value = "/suppliers")
    public ResponseEntity<Supplier> createSupplier(@RequestBody @Valid SupplierCreateRequestDto supplierRequestDto)
            throws URISyntaxException {
        Supplier supplier = supplierService.createSupplier(supplierRequestDto.getTitle(), supplierRequestDto.getAddress());
        Collection<Supply> supplies = supplyService.createSupplies(supplier, supplierRequestDto.getSupplies());
        supplierService.updateSupply(supplier.getId(), supplies);
        return ResponseEntity.created(new URI("/api/stock/" + supplier.getId())).body(supplier);
    }

    @PutMapping(value = "/suppliers/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id,
                                                   @RequestBody @Valid SupplierUpdateRequestDto supplierCreateRequestDto) {
        Supplier supplier = supplierService.getSupplier(id);
        Collection<Supply> supplies = supplyService.updateSupply(supplier, supplierCreateRequestDto.getSupplies());
        supplier = supplierService.updateSupplier(
                id,
                supplierCreateRequestDto.getTitle(),
                supplierCreateRequestDto.getAddress(),
                supplies
        );
        return ResponseEntity.ok().body(supplier);
    }

    @DeleteMapping(value = "/suppliers/{id}")
    public ResponseEntity deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/supply/{id}")
    public ResponseEntity deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
        return ResponseEntity.noContent().build();
    }

}

package com.sergey.zhuravlev.pizza.controller;

import com.sergey.zhuravlev.pizza.dto.CustomerRequestDto;
import com.sergey.zhuravlev.pizza.dto.pageable.PageDto;
import com.sergey.zhuravlev.pizza.entity.Customer;
import com.sergey.zhuravlev.pizza.entity.Ingredient;
import com.sergey.zhuravlev.pizza.service.CustomerService;
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
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(path = "/customers")
    public PageDto<Customer> list(@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return new PageDto<>(customerService.list(PageRequest.of(page, size)));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getIngredient(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.getCustomer(id));
    }

    @PostMapping(value = "/customers")
    public ResponseEntity<Customer> createIngredient(@RequestBody @Valid CustomerRequestDto customerRequestDto) throws URISyntaxException {
        Customer customer = customerService.createCustomer(
                customerRequestDto.getFirstName(),
                customerRequestDto.getLastName(),
                customerRequestDto.getAddress());
        return ResponseEntity.created(new URI("/api/customers/" + customer.getId())).body(customer);
    }

    @PutMapping(value = "/customers/{id}")
    public ResponseEntity<Customer> updateIngredient(@PathVariable Long id,
                                                       @RequestBody @Valid CustomerRequestDto customerRequestDto) {
        Customer customer = customerService.updateCustomer(
                id,
                customerRequestDto.getFirstName(),
                customerRequestDto.getLastName(),
                customerRequestDto.getAddress());

        return ResponseEntity.ok().body(customer);
    }

    @DeleteMapping(value = "/customers/{id}")
    public ResponseEntity deleteIngredient(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}

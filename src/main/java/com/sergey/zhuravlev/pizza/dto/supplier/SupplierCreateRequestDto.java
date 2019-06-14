package com.sergey.zhuravlev.pizza.dto.supplier;

import com.sergey.zhuravlev.pizza.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCreateRequestDto {

    private String title;

    private Address address;

    private Collection<SupplyRequestDto> supplies;

}
